/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
// Portions Copyright [2018] [Payara Foundation and/or its affiliates]
package org.netbeans.modules.fish.payara.micro.project.ui;

import java.awt.Component;
import java.util.NoSuchElementException;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.validation.adapters.WizardDescriptorAdapter;
import org.netbeans.validation.api.ui.ValidationGroup;
import org.openide.WizardDescriptor;
import static org.openide.util.NbBundle.getMessage;

/**
 * Base abstract class for all types of Maven enterprise projects.
 * Encapsulates some Wizard related stuffs and few methods common for every project type
 *
 * @author Martin Janicek
 */
public abstract class BaseWizardIterator implements WizardDescriptor.BackgroundInstantiatingIterator {

    protected WizardDescriptor descriptor;
    private int index;
    private WizardDescriptor.Panel[] panels;

    protected abstract WizardDescriptor.Panel[] createPanels(ValidationGroup validationGroup);

    @Override
    public void initialize(WizardDescriptor descriptor) {
        this.descriptor = descriptor;
        this.index = 0;
        panels = createPanels(ValidationGroup.create(new WizardDescriptorAdapter(descriptor)));
        updateSteps();
    }

    @Override
    public void uninitialize(WizardDescriptor wiz) {
        this.descriptor.putProperty("projdir", null); //NOI18N
        this.descriptor.putProperty("name", null); //NOI18N
        this.descriptor = null;
        panels = null;
    }

    @Override
    public String name() {
        return getMessage(BaseWizardIterator.class, "LBL_NameFormat", index + 1, panels.length);
    }

    @Override
    public boolean hasNext() {
        return index < panels.length - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    @Override
    public WizardDescriptor.Panel current() {
        return panels[index];
    }

    @Override
    public final void addChangeListener(ChangeListener l) {
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
    }

    private void updateSteps() {
        // Make sure list of steps is accurate.
        String[] steps = new String[panels.length];
        String[] basicOnes = new String[]{
            getMessage(BaseWizardIterator.class, "LBL_MavenProjectSettings"),
            getMessage(BaseWizardIterator.class, "LBL_PayaraMicroSettings")
        };
        System.arraycopy(basicOnes, 0, steps, 0, basicOnes.length);
        for (int i = 0; i < panels.length; i++) {
            Component c = panels[i].getComponent();
            if (i >= basicOnes.length || steps[i] == null) {
                // Default step name to component name of panel.
                // Mainly useful for getting the name of the target
                // chooser to appear in the list of steps.
                steps[i] = c.getName();
            }
            if (c instanceof JComponent) {
                // assume Swing components
                JComponent jc = (JComponent) c;
                // Step #.
                jc.putClientProperty("WizardPanel_contentSelectedIndex", Integer.valueOf(i)); //NOI18N
                // Step name (actually the whole list for reference).
                jc.putClientProperty("WizardPanel_contentData", steps); //NOI18N
            }
        }
    }

}