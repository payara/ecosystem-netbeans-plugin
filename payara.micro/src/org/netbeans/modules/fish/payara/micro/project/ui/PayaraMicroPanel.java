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

import org.netbeans.modules.fish.payara.micro.project.VersionRepository;
import static org.netbeans.modules.fish.payara.micro.project.ui.Bundle.LBL_PayaraMicroSettings;
import static org.netbeans.modules.fish.payara.micro.project.ui.PayaraMicroWizardIterator.PROP_AUTO_BIND_HTTP;
import static org.netbeans.modules.fish.payara.micro.project.ui.PayaraMicroWizardIterator.PROP_PAYARA_MICRO_VERSION;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.netbeans.modules.fish.payara.micro.project.MicroVersion;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.openide.WizardDescriptor;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
class PayaraMicroPanel extends JPanel {

    PayaraMicroPanel(J2eeModule.Type projectType) {
        initComponents();
        getAccessibleContext().setAccessibleDescription(getName());
    }

    @Override
    public String getName() {
        return LBL_PayaraMicroSettings();
    }

    void readSettings(WizardDescriptor descriptor) {
        String microVersionText = (String) descriptor.getProperty(PROP_PAYARA_MICRO_VERSION);
        if (microVersionText != null) {
            VersionRepository.toMicroVersion(microVersionText)
                    .ifPresent(microVersion -> microVersionCombobox.setSelectedItem(microVersion));
        }
        
        String autoBindHTTP = (String)descriptor.getProperty(PROP_AUTO_BIND_HTTP);
        if(autoBindHTTP == null){
            autoBindHTTP = Boolean.TRUE.toString();
        }
        autoBindHttpCheckBox.setSelected(Boolean.valueOf(autoBindHTTP));
    }

    void storeSettings(WizardDescriptor descriptor) {
        descriptor.putProperty(PROP_PAYARA_MICRO_VERSION, ((MicroVersion)microVersionCombobox.getSelectedItem()).getVersion());
        descriptor.putProperty(PROP_AUTO_BIND_HTTP, String.valueOf(autoBindHttpCheckBox.isSelected()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        microVersionCombobox = new javax.swing.JComboBox();
        microVersionLabel = new javax.swing.JLabel();
        autoBindHttpLabel = new javax.swing.JLabel();
        autoBindHttpCheckBox = new javax.swing.JCheckBox();

        microVersionCombobox.setModel(new DefaultComboBoxModel(VersionRepository.getInstance().getMicroVersion().toArray()));

        org.openide.awt.Mnemonics.setLocalizedText(microVersionLabel, org.openide.util.NbBundle.getMessage(PayaraMicroPanel.class, "PayaraMicroPanel.microVersionLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(autoBindHttpLabel, org.openide.util.NbBundle.getMessage(PayaraMicroPanel.class, "PayaraMicroPanel.autoBindHttpLabel.text")); // NOI18N
        autoBindHttpLabel.setToolTipText(org.openide.util.NbBundle.getMessage(PayaraMicroPanel.class, "TLTP_AUTO_BIND_HTTP")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(autoBindHttpCheckBox, org.openide.util.NbBundle.getMessage(PayaraMicroPanel.class, "PayaraMicroPanel.autoBindHttpCheckBox.text")); // NOI18N
        autoBindHttpCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(PayaraMicroPanel.class, "TLTP_AUTO_BIND_HTTP")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(microVersionLabel)
                    .addComponent(autoBindHttpLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(autoBindHttpCheckBox)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(microVersionCombobox, 0, 230, Short.MAX_VALUE)
                        .addGap(69, 69, 69))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(microVersionLabel)
                    .addComponent(microVersionCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(autoBindHttpCheckBox)
                    .addComponent(autoBindHttpLabel))
                .addContainerGap(255, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoBindHttpCheckBox;
    private javax.swing.JLabel autoBindHttpLabel;
    private javax.swing.JComboBox microVersionCombobox;
    private javax.swing.JLabel microVersionLabel;
    // End of variables declaration//GEN-END:variables

}
