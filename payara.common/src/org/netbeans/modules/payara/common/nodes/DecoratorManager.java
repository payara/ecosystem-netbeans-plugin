/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * 
 * Contributor(s):
 * 
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.payara.common.nodes;

import java.util.HashMap;
import org.netbeans.modules.payara.common.utils.Util;
import org.netbeans.modules.payara.spi.Decorator;
import org.netbeans.modules.payara.spi.DecoratorFactory;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Peter Williams
 */
public final class DecoratorManager {

    private static volatile java.util.Map<String, Decorator> decoratorMap;
    
    private DecoratorManager() {
    }
    
    private static synchronized void initDecorators() {
        if(decoratorMap != null) {
            return;
        }
        
        // Find all decorator support, categorize by type.
        decoratorMap = new HashMap<String, Decorator>();
        for (DecoratorFactory decoratorFactory : 
                Lookups.forPath(Util.PF_LOOKUP_PATH).lookupAll(DecoratorFactory.class)) {
            java.util.Map<String, Decorator> map = decoratorFactory.getAllDecorators();
            decoratorMap.putAll(map);
        }
    }

    
    public static Decorator findDecorator(String type, Decorator defaultDecorator,boolean enabled) {
        if(decoratorMap == null) {
            initDecorators();
        }

        if (!enabled) {
            type = Decorator.DISABLED+type;
        }
        Decorator d = decoratorMap.get(type);
        return d != null ? d : defaultDecorator;
    }
    
}
