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
package org.netbeans.modules.fish.payara.micro.project.ui;

import static org.netbeans.modules.fish.payara.micro.Constants.ARCHETYPE_ARTIFACT_ID;
import static org.netbeans.modules.fish.payara.micro.Constants.ARCHETYPE_GROUP_ID;
import static org.netbeans.modules.fish.payara.micro.Constants.ARCHETYPE_VERSION;
import org.netbeans.modules.fish.payara.micro.project.VersionRepository;
import static org.netbeans.modules.fish.payara.micro.project.ui.Bundle.template_PayaraMicroApp;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.templates.TemplateRegistration;
import static org.netbeans.modules.fish.payara.micro.Constants.PROJECT_TYPE;
import static org.netbeans.modules.fish.payara.micro.Constants.PROP_ARTIFACT_ID;
import static org.netbeans.modules.fish.payara.micro.Constants.PROP_AUTO_BIND_HTTP;
import static org.netbeans.modules.fish.payara.micro.Constants.PROP_GROUP_ID;
import static org.netbeans.modules.fish.payara.micro.Constants.PROP_JAVA_EE_VERSION;
import static org.netbeans.modules.fish.payara.micro.Constants.PROP_PACKAGE;
import static org.netbeans.modules.fish.payara.micro.Constants.PROP_PAYARA_MICRO_VERSION;
import static org.netbeans.modules.fish.payara.micro.Constants.PROP_VERSION;
import org.netbeans.modules.maven.api.archetype.Archetype;
import org.netbeans.modules.maven.api.archetype.ArchetypeWizards;
import org.netbeans.modules.maven.api.archetype.ProjectInfo;
import org.netbeans.modules.maven.j2ee.utils.MavenProjectSupport;
import org.netbeans.validation.api.ui.ValidationGroup;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
@TemplateRegistration(
        folder = ArchetypeWizards.TEMPLATE_FOLDER,
        position = 200,
        displayName = "#template.PayaraMicroApp",
        iconBase = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro.png",
        description = "../resources/PayaraMicroProjectDescription.html"
)
@Messages("template.PayaraMicroApp=Payara Micro Application")
public final class MicroProjectWizardIterator extends BaseWizardIterator {

    @Override
    public Set<FileObject> instantiate() throws IOException {
        ProjectInfo projectInfo = new ProjectInfo(
                (String) descriptor.getProperty(PROP_GROUP_ID),
                (String) descriptor.getProperty(PROP_ARTIFACT_ID),
                (String) descriptor.getProperty(PROP_VERSION),
                (String) descriptor.getProperty(PROP_PACKAGE)
        );
        String payaraMicroVersion = (String) descriptor.getProperty(PROP_PAYARA_MICRO_VERSION);
        Archetype archetype = createMojoArchetype();

        Map<String, String> properties = new HashMap<>();
        properties.put(PROP_PAYARA_MICRO_VERSION, payaraMicroVersion);
        properties.put(PROP_JAVA_EE_VERSION, VersionRepository.getInstance().getJavaEEVersion(payaraMicroVersion));
        properties.put(PROP_AUTO_BIND_HTTP, (String) descriptor.getProperty(PROP_AUTO_BIND_HTTP));

        ArchetypeWizards.logUsage(archetype.getGroupId(), archetype.getArtifactId(), archetype.getVersion());

        File rootFile = FileUtil.normalizeFile((File) descriptor.getProperty("projdir")); // NOI18N
        ArchetypeWizards.createFromArchetype(rootFile, projectInfo, archetype, properties, true);

        Set<FileObject> projects = ArchetypeWizards.openProjects(rootFile, rootFile);
        for (FileObject projectFile : projects) {
            Project project = ProjectManager.getDefault().findProject(projectFile);
            if (project == null) {
                continue;
            }
            MavenProjectSupport.changeServer(project, true);
        }

        return projects;
    }

    private Archetype createMojoArchetype() {
        Archetype archetype = new Archetype();
        archetype.setGroupId(ARCHETYPE_GROUP_ID);
        archetype.setArtifactId(ARCHETYPE_ARTIFACT_ID);
        archetype.setVersion(ARCHETYPE_VERSION);
        return archetype;
    }

    @Override
    public void initialize(WizardDescriptor wiz) {
        super.initialize(wiz);
        wiz.putProperty("NewProjectWizard_Title", template_PayaraMicroApp());
    }

    @Override
    protected WizardDescriptor.Panel[] createPanels(ValidationGroup vg) {
        return new WizardDescriptor.Panel[]{
            ArchetypeWizards.basicWizardPanel(vg, false, null),
            new PayaraMicroDescriptor(PROJECT_TYPE)
        };
    }
}
