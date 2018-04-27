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

import org.apache.maven.project.MavenProject;
import static org.netbeans.modules.fish.payara.micro.Constants.MAVEN_WAR_PROJECT_TYPE;
import org.netbeans.api.project.Project;
import static org.netbeans.modules.fish.payara.micro.Constants.PAYARA_MICRO_MAVEN_PLUGIN;
import org.netbeans.modules.j2ee.deployment.devmodules.spi.J2eeModuleProvider;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.netbeans.modules.maven.spi.nodes.SpecialIcon;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.netbeans.spi.project.ui.ProjectOpenedHook;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
@ProjectServiceProvider(
        service = ProjectOpenedHook.class,
        projectType = MAVEN_WAR_PROJECT_TYPE
)
public class ProjectHookImpl extends ProjectOpenedHook {

    private final Project project;

    public ProjectHookImpl(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
        initMicroProject(isPayaraMicroProject(project));
    }

    public void initMicroProject(boolean microProject) {
        MicroApplicationProvider microApplicationProvider = project.getLookup().lookup(MicroApplicationProvider.class);
        if (microProject) {
            microApplicationProvider.setMicroApplication(new MicroApplication(project));
            addDeployOnSaveManager(project);
            updateMicroIcon();
        } else {
            microApplicationProvider.setMicroApplication(null);
        }
    }

    @Override
    public void projectClosed() {
        removeDeployOnSaveManager(project);
    }

    public static boolean isPayaraMicroProject(Project project) {
        NbMavenProject nbMavenProject = project.getLookup().lookup(NbMavenProject.class);
        MavenProject mavenProject = (MavenProject) nbMavenProject.getMavenProject();
        return mavenProject.getPluginArtifactMap()
                .get(PAYARA_MICRO_MAVEN_PLUGIN) != null;
    }

    private void updateMicroIcon() {

        SpecialIcon specialIcon = project.getLookup().lookup(SpecialIcon.class);
        MicroIcon microIcon;
        if (specialIcon instanceof MicroIcon) {
            microIcon = (MicroIcon) specialIcon;
        } else {
            return;
        }
        microIcon.setProject(project);
    }

    private void addDeployOnSaveManager(Project project) {
        J2eeModuleProvider moduleProvider = project.getLookup().lookup(J2eeModuleProvider.class);
        if (moduleProvider != null) {
            DeployOnSaveManager.getDefault().startListening(project, moduleProvider);
        }
    }

    private void removeDeployOnSaveManager(Project project) {
        J2eeModuleProvider moduleProvider = project.getLookup().lookup(J2eeModuleProvider.class);
        if (moduleProvider != null && MicroApplication.getInstance(project) != null) {
            DeployOnSaveManager.getDefault().stopListening(project, moduleProvider);
        }
    }
}
