/*
 *
 * Copyright (c) 2018 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.netbeans.modules.fish.payara.micro.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
@ActionID(
        id = ReloadActionDelegator.ID,
        category = ReloadActionDelegator.CATEGORY
)
@ActionRegistration(
        displayName = "#CTL_ReloadAppAction",
        iconBase = "org/netbeans/modules/fish/payara/micro/action/resources/reload.png",
        lazy = true
)
@ActionReferences({
    @ActionReference(path = "Menu/BuildProject", position = 55)
    ,
    @ActionReference(path = "Toolbars/Build", position = 325)
    ,
    @ActionReference(path = "Projects/org-netbeans-modules-maven/Actions", position = 1000)
    ,
    @ActionReference(path = "Shortcuts", name = "DS-A")
})
@Messages("CTL_ReloadAppAction=Reload")
public class ReloadActionDelegator extends AbstractAction /*implements ContextAwareAction, LookupListener*/ {

    static final String CATEGORY = "Build";

    static final String ID = "org.netbeans.modules.fish.payara.micro.action.reload";

//    private Lookup.Result<Object> lkpInfo;        
//    

    public ReloadActionDelegator() {
//        setEnabled(action.isActionEnabled());
//        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
    }

//    void init(){
//        if (lkpInfo != null) {
//            lkpInfo = context.lookupResult(Object.class);
//            lkpInfo.addLookupListener(this);
//            resultChanged(null);
//        }
//    }
//
//    @Override
//    public boolean isEnabled() {
//        init();
//        return super.isEnabled();
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Lookup context = Utilities.actionsGlobalContext();
        Project project = context.lookup(Project.class);
        new ReloadAction(project).actionPerformed();
    }

//    @Override
//    public Action createContextAwareInstance(Lookup context) {
//        return new ReloadActionDelegator(context);
//    }
//    @Override
//    public void resultChanged(LookupEvent ev) {
//        super.setEnabled(action.isActionEnabled());
//    }
}
