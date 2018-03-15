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
package org.netbeans.modules.fish.payara.micro.project;

import static org.netbeans.modules.fish.payara.micro.Constants.MAVEN_WAR_PROJECT_TYPE;
import static org.netbeans.modules.fish.payara.micro.Constants.PROJECT_ICON;
import static org.netbeans.modules.fish.payara.micro.Constants.RELOAD_ICON;
import static org.netbeans.modules.fish.payara.micro.Constants.RESTART_ICON;
import javax.swing.Icon;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.j2ee.ui.EEIcons.WarIcon;
import org.netbeans.modules.maven.spi.nodes.SpecialIcon;
import org.netbeans.spi.project.ProjectServiceProvider;
import static org.openide.util.ImageUtilities.loadImageIcon;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
@ProjectServiceProvider(
        service = SpecialIcon.class,
        projectType = MAVEN_WAR_PROJECT_TYPE
)
public class MicroIcon extends WarIcon {

    private MicroApplication microApplication;

    public void setProject(Project project) {
        this.microApplication = project.getLookup().lookup(MicroApplication.class);
    }

    @Override
    public Icon getIcon() {
        String icon = PROJECT_ICON;
        if (microApplication == null) {
            return super.getIcon();
        } else if (microApplication.isLoading()) {
            icon = RELOAD_ICON;
        } else if (microApplication.isBuilding() && microApplication.getBuildActionType() != null) {
            icon = microApplication.getBuildActionType().getIcon();
        } else if (microApplication.isRunning()) {
            if (microApplication.getRunningInstanceCount() > 1) {
                icon = RESTART_ICON;
            } else if (microApplication.getRunActionType() != null) {
                icon = microApplication.getRunActionType().getIcon();
            }
        }       
        return loadImageIcon(icon, true);
    }

}
