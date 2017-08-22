/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.common.nodes;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.payara.common.CommonServerSupport;
import org.netbeans.modules.payara.spi.AppDesc;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;


/**
 * 
 * @author Ludovic Champenois
 * @author Peter Williams
 */
public class Hk2ApplicationsChildren extends Children.Keys<Object> implements Refreshable {
    
    private Lookup lookup;
    private final static Node WAIT_NODE = Hk2ItemNode.createWaitNode();
    
    Hk2ApplicationsChildren(Lookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public void updateKeys(){
        setKeys(new Object[] { WAIT_NODE });
        
        RequestProcessor t = new RequestProcessor("app-child-updater");
        t.post(new Runnable() {
            Vector<Object> keys = new Vector<Object>();
            
            @Override
            public void run() {
                CommonServerSupport commonSupport = lookup.lookup(
                        CommonServerSupport.class);
                if(commonSupport != null) {
                    try {
                        java.util.Map<String, List<AppDesc>> appMap
                                = commonSupport.getApplications(null);
                        for(Entry<String, List<AppDesc>> entry: appMap.entrySet()) {
                            List<AppDesc> apps = entry.getValue();
                            for(AppDesc app: apps) {
                                keys.add(new Hk2ApplicationNode(lookup, app, DecoratorManager.findDecorator(entry.getKey(), Hk2ItemNode.J2EE_APPLICATION, app.getEnabled())));
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger("payara").log(Level.INFO, ex.getLocalizedMessage(), ex);
                    }
                    
                    setKeys(keys);
                }
            }
        }, 0);
    }
    
    @Override
    protected void addNotify() {
        updateKeys();
    }
    
    @Override
    protected void removeNotify() {
        setKeys((Set<? extends Object>) java.util.Collections.EMPTY_SET);
    }
    
    @Override
    protected org.openide.nodes.Node[] createNodes(Object key) {
        if (key instanceof Hk2ItemNode){
            return new Node [] { (Hk2ItemNode) key };
        }
        
        if (key instanceof String && key.equals(WAIT_NODE)){
            return new Node [] { WAIT_NODE };
        }
        
        return null;
    }
}
