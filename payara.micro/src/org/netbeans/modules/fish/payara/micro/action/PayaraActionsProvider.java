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

import static org.netbeans.modules.fish.payara.micro.Constants.DEBUG_SINGLE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.MAVEN_WAR_PROJECT_TYPE;
import static org.netbeans.modules.fish.payara.micro.Constants.PROFILE_SINGLE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.RUN_SINGLE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.WAR_PACKAGING;
import org.netbeans.modules.fish.payara.micro.project.MicroApplication;
import java.io.InputStream;
import java.util.Set;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.netbeans.modules.maven.api.execute.RunConfig;
import org.netbeans.modules.maven.execute.model.NetbeansActionMapping;
import org.netbeans.modules.maven.spi.actions.AbstractMavenActionsProvider;
import org.netbeans.modules.maven.spi.actions.MavenActionsProvider;
import static org.netbeans.spi.project.ActionProvider.COMMAND_DEBUG;
import static org.netbeans.spi.project.ActionProvider.COMMAND_PROFILE;
import static org.netbeans.spi.project.ActionProvider.COMMAND_RUN;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.openide.util.Lookup;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
@ProjectServiceProvider(
        service = MavenActionsProvider.class,
        projectType = MAVEN_WAR_PROJECT_TYPE
)
public class PayaraActionsProvider implements MavenActionsProvider {// extends AbstractMavenActionsProvider {


    @StaticResource
    private static final String ACTION_MAPPINGS = "org/netbeans/modules/fish/payara/micro/action/resources/action-mapping.xml";
        
    AbstractMavenActionsProvider abstractMavenActionsProvider = new AbstractMavenActionsProvider() {
        @Override
        protected InputStream getActionDefinitionStream() {
            return PayaraActionsProvider.class.getClassLoader()
                    .getResourceAsStream(ACTION_MAPPINGS);
        }

        @Override
        public boolean isActionEnable(String action, Project project, Lookup lookup) {
            NbMavenProject nbMavenProject = project.getLookup().lookup(NbMavenProject.class);
            MicroApplication microApplication = project.getLookup().lookup(MicroApplication.class);
            final String packagingType = nbMavenProject.getPackagingType();
            if (!WAR_PACKAGING.equals(packagingType)) {
                return false;
            }
            switch (action) {
                case COMMAND_RUN:
                case COMMAND_DEBUG:
                case COMMAND_PROFILE:
                case RUN_SINGLE_ACTION:
                case DEBUG_SINGLE_ACTION:
                case PROFILE_SINGLE_ACTION:
                    break;
                default:
                    return false;
            }
            return microApplication != null;
        }

    };

    @Override
    public RunConfig createConfigForDefaultAction(String actionName, Project project, Lookup lookup) {
        return abstractMavenActionsProvider.createConfigForDefaultAction(actionName, project, lookup);
    }

    @Override
    public NetbeansActionMapping getMappingForAction(String actionName, Project project) {
        return abstractMavenActionsProvider.getMappingForAction(actionName, project);
    }

    @Override
    public boolean isActionEnable(String action, Project project, Lookup lookup) {
        return abstractMavenActionsProvider.isActionEnable(action, project, lookup);
    }

    @Override
    public Set<String> getSupportedDefaultActions() {
        return abstractMavenActionsProvider.getSupportedDefaultActions();
    }

}
