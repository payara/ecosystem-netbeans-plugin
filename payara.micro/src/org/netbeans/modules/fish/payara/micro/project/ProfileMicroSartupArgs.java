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
import static org.netbeans.modules.fish.payara.micro.Constants.PROFILE_SINGLE_ACTION;
import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.api.execute.ExecutionContext;
import org.netbeans.modules.maven.api.execute.LateBoundPrerequisitesChecker;
import org.netbeans.modules.maven.api.execute.RunConfig;
import org.netbeans.modules.maven.execute.BeanRunConfig;
import org.netbeans.modules.maven.runjar.RunJarStartupArgs;
import static org.netbeans.spi.project.ActionProvider.COMMAND_PROFILE;
import static org.netbeans.spi.project.ActionProvider.COMMAND_PROFILE_TEST_SINGLE;
import org.netbeans.spi.project.ProjectServiceProvider;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
@ProjectServiceProvider(
        service=LateBoundPrerequisitesChecker.class, 
        projectType=MAVEN_WAR_PROJECT_TYPE
)
public class ProfileMicroSartupArgs extends RunJarStartupArgs {
    
        private static final Set<String> PROFILE_ACTIONS = new HashSet<>(asList(new String[]{
        COMMAND_PROFILE, 
        PROFILE_SINGLE_ACTION,
        COMMAND_PROFILE_TEST_SINGLE
    }));
    
    @Override 
    public boolean checkRunConfig(RunConfig config, ExecutionContext con) {
        boolean status = true;
        String action = config.getActionName();
        if (!PROFILE_ACTIONS.contains(action)) {
            return true;
        }
        interceptActionName(config, action, COMMAND_PROFILE);
        
        Project project = config.getProject();
        MicroApplication microApplication = project.getLookup().lookup(MicroApplication.class);
        if (microApplication != null) {
            status = super.checkRunConfig(config, con);
        }
        
        interceptActionName(config, action, action);
        return status;
    }
    
    private void interceptActionName(RunConfig config, String orignalAction, String newAction){
        if (PROFILE_SINGLE_ACTION.equals(orignalAction) && config instanceof BeanRunConfig) {
            BeanRunConfig beanRunConfig = (BeanRunConfig) config;
            beanRunConfig.setActionName(newAction);
        }
    }
}
