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

import static org.netbeans.modules.fish.payara.micro.Constants.COMMAND_EXPLODE;
import static org.netbeans.modules.fish.payara.micro.Constants.COMPILE_EXPLODE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.DEBUG_SINGLE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.EXPLODE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.MAVEN_WAR_PROJECT_TYPE;
import static org.netbeans.modules.fish.payara.micro.Constants.PROFILE_SINGLE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.RELOAD_FILE;
import static org.netbeans.modules.fish.payara.micro.Constants.RUN_SINGLE_ACTION;
import org.netbeans.modules.fish.payara.micro.action.ReloadAction;
import java.io.File;
import java.io.IOException;
import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.api.execute.ExecutionContext;
import org.netbeans.modules.maven.api.execute.ExecutionResultChecker;
import org.netbeans.modules.maven.api.execute.PrerequisitesChecker;
import org.netbeans.modules.maven.api.execute.RunConfig;
import static org.netbeans.spi.project.ActionProvider.COMMAND_BUILD;
import static org.netbeans.spi.project.ActionProvider.COMMAND_CLEAN;
import static org.netbeans.spi.project.ActionProvider.COMMAND_DEBUG;
import static org.netbeans.spi.project.ActionProvider.COMMAND_PROFILE;
import static org.netbeans.spi.project.ActionProvider.COMMAND_REBUILD;
import static org.netbeans.spi.project.ActionProvider.COMMAND_RUN;
import org.netbeans.spi.project.ProjectServiceProvider;
import org.openide.util.Exceptions;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
@ProjectServiceProvider(
        service = {
            ExecutionResultChecker.class,
            PrerequisitesChecker.class
        }, 
        projectType = MAVEN_WAR_PROJECT_TYPE
)
public class ExecutionChecker implements ExecutionResultChecker, PrerequisitesChecker {
    
    private static final String COMMAND_BUILD_WITH_DEPENDENCIES = "build-with-dependencies";
    
    private static final Set<String> BUILD_ACTIONS = new HashSet<>(asList(new String[]{
        COMMAND_CLEAN, 
        COMMAND_BUILD, 
        COMMAND_REBUILD,
        COMMAND_BUILD_WITH_DEPENDENCIES,
        COMPILE_EXPLODE_ACTION, 
        EXPLODE_ACTION
    }));
    
    private static final Set<String> RUN_ACTIONS = new HashSet<>(asList(new String[]{
        COMMAND_RUN, 
        COMMAND_DEBUG, 
        COMMAND_PROFILE, 
        RUN_SINGLE_ACTION, 
        DEBUG_SINGLE_ACTION,
        PROFILE_SINGLE_ACTION
    }));
    
    
    @Override
    public boolean checkRunConfig(RunConfig config) {
        Project project = config.getProject();
        MicroApplication microApplication = MicroApplication.getInstance(project);
        if (microApplication != null) {
            if (BUILD_ACTIONS.contains(config.getActionName())) {
                microApplication.setBuilding(true, config.getActionName());
            }else if (RUN_ACTIONS.contains(config.getActionName())) {
                microApplication.setRunning(true, config.getActionName());
            }
        }
        return true;
    }

    @Override
    public void executionResult(RunConfig config, ExecutionContext res, int resultCode) {
        Project project = config.getProject();
        MicroApplication microApplication = MicroApplication.getInstance(project);
        if (microApplication != null) {
            if (BUILD_ACTIONS.contains(config.getActionName())) {
                if(config.getActionName().contains(COMMAND_BUILD) 
                        || config.getActionName().contains(COMMAND_EXPLODE) ){
                    reloadApplication(microApplication);
                }
                microApplication.setBuilding(false);
            } else if (RUN_ACTIONS.contains(config.getActionName())) {
                microApplication.setRunning(false);
            }
        }
    }
    
    public static void reloadApplication(MicroApplication application) {
        if (!application.isRunning()) {
            return;
        }
        String buildPath = application.getMavenProject().getBuild().getDirectory()
                + File.separator
                + application.getMavenProject().getBuild().getFinalName();
        ReloadAction.reloadApplication(buildPath);
    }
    

}
