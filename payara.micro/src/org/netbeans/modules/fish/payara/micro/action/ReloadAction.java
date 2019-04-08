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

import static org.netbeans.modules.fish.payara.micro.Constants.COMPILE_EXPLODE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.EXPLODE_ACTION;
import static org.netbeans.modules.fish.payara.micro.Constants.RELOAD_FILE;
import org.netbeans.modules.fish.payara.micro.project.MicroApplication;
import java.io.File;
import java.io.IOException;
import org.apache.maven.project.MavenProject;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.netbeans.modules.maven.api.execute.RunUtils;
import static org.netbeans.modules.maven.api.execute.RunUtils.isCompileOnSaveEnabled;
import org.netbeans.modules.maven.execute.ActionToGoalUtils;
import org.netbeans.modules.maven.execute.ModelRunConfig;
import org.netbeans.modules.maven.execute.model.NetbeansActionMapping;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import static org.openide.util.NbBundle.getMessage;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
public class ReloadAction {

    private static final RequestProcessor RP = new RequestProcessor(ReloadActionDelegator.class.getName());

    private final Project project;

    private final MavenProject mavenProject;


    public ReloadAction(Project project) {
        this.project = project;
        NbMavenProject nbMavenProject = project.getLookup().lookup(NbMavenProject.class);
        mavenProject = nbMavenProject.getMavenProject();
    }

    public void actionPerformed() {
        MicroApplication microApplication = MicroApplication.getInstance(project);
        if (microApplication == null) {
            StatusDisplayer.getDefault().setStatusText(
                    getMessage(ReloadAction.class, "ERR_Payara_Micro_Plugin_Not_Found", mavenProject.getArtifactId()));
        } else if (!isCompileOnSaveEnabled(microApplication.getProject())) {
            StatusDisplayer.getDefault().setStatusText(
                    getMessage(ReloadAction.class, "ERR_Compile_On_Save_Not_Enabled", mavenProject.getArtifactId()));
        } else {
            runMavenCommands();
        }
    }

    public boolean isActionEnabled() {
        return MicroApplication.getInstance(project) != null;
    }

    public void runMavenCommands() {
        RP.post(() -> {
            String action = RunUtils.isCompileOnSaveEnabled(project)? EXPLODE_ACTION : COMPILE_EXPLODE_ACTION;
            NetbeansActionMapping mapping = ActionToGoalUtils.getDefaultMapping(action, project);
            ModelRunConfig rc = new ModelRunConfig(project, mapping, mapping.getActionName(), null, Lookup.EMPTY, false);
            rc.setTaskDisplayName(getMessage(ReloadAction.class, "TXT_Reload", mavenProject.getArtifactId()));
            rc.getGoals().addAll(PayaraActionsProvider.getGoals(action));
            RunUtils.run(rc);
        });
    }

    public static void reloadApplication(String buildPath) {
        File check = new File(buildPath, RELOAD_FILE);
        if (check.exists()) {
            check.setLastModified(System.currentTimeMillis());
        } else {
            try {
                check.createNewFile();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}
