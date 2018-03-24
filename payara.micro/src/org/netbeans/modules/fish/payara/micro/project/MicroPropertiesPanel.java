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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.netbeans.api.project.Project;
import static org.netbeans.api.project.ProjectUtils.getPreferences;
import static org.netbeans.modules.fish.payara.micro.Constants.VERSION;
import org.netbeans.modules.maven.api.customizer.ModelHandle2;
import org.netbeans.modules.maven.j2ee.ui.customizer.ApplyChangesCustomizer;
import org.netbeans.modules.maven.j2ee.ui.customizer.ComboBoxUpdater;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
public class MicroPropertiesPanel extends JPanel implements ApplyChangesCustomizer {

    private static final MicroVersion DEFAULT_VERSION = new MicroVersion("", "", "defined in pom.xml");
    
    private final Preferences pref;
    
    private final ComboBoxUpdater microVersionComboBoxUpdater;
    
    public MicroPropertiesPanel(ModelHandle2 handle, Project project) {
        pref = getPreferences(project, MicroApplication.class, true);
        initComponents();
        String microVersionText = pref.get(VERSION, "");
        Optional<MicroVersion> microVersionOptional = VersionRepository.toMicroVersion(microVersionText);
        if(microVersionOptional.isPresent()){
            microVersionCombobox.setSelectedItem(microVersionOptional.get());
        } else {
            microVersionCombobox.setSelectedItem(DEFAULT_VERSION);
        }
        
        microVersionComboBoxUpdater= ComboBoxUpdater.create(microVersionCombobox, microVersionLabel, "", (Object microVersionRaw) -> {
              MicroVersion microVersion = (MicroVersion)microVersionRaw;
              pref.put(VERSION, microVersion.getVersion());
        });
    }
    
    private MicroVersion[] getMicroVersion() {
        List<MicroVersion> microVersions = new ArrayList<>();
        microVersions.add(DEFAULT_VERSION);
        microVersions.addAll(VersionRepository.getInstance().getMicroVersion());
        return microVersions.toArray(new MicroVersion[]{});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        microVersionLabel = new javax.swing.JLabel();
        microVersionCombobox = new javax.swing.JComboBox();

        org.openide.awt.Mnemonics.setLocalizedText(microVersionLabel, org.openide.util.NbBundle.getMessage(MicroPropertiesPanel.class, "MicroPropertiesPanel.microVersionLabel.text")); // NOI18N

        microVersionCombobox.setModel(new DefaultComboBoxModel(getMicroVersion()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(microVersionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(microVersionCombobox, 0, 272, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(microVersionCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(microVersionLabel))
                .addContainerGap(117, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox microVersionCombobox;
    private javax.swing.JLabel microVersionLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void applyChanges() {
        microVersionComboBoxUpdater.storeValue();
    }

    @Override
    public void applyChangesInAWT() {
    }

}
