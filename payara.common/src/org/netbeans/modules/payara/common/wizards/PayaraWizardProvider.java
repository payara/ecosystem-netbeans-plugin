/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.common.wizards;

import org.netbeans.modules.payara.common.ServerDetails;
import org.netbeans.spi.server.ServerWizardProvider;
import org.openide.WizardDescriptor.InstantiatingIterator;

/**
 *
 * @author Peter Williams
 * @author vince kraemer
 */
public class PayaraWizardProvider implements ServerWizardProvider {

    public static PayaraWizardProvider createEe6() {
        return new PayaraWizardProvider(
                org.openide.util.NbBundle.getMessage(PayaraWizardProvider.class,
                "STR_V3_FAMILY_NAME", new Object[]{}) // NOI18N
                );
    }
    
    private final String displayName;

    private PayaraWizardProvider(
            String displayName
            ) {
        this.displayName = displayName;
    }

    // ------------------------------------------------------------------------
    // ServerWizardProvider interface implementation
    // ------------------------------------------------------------------------
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Creates an iterator for a wizard to instantiate server objects.
     * <p/>
     * @return Server wizard iterator initialized with supported Payara
     * server versions.
     */
    @Override
    public InstantiatingIterator getInstantiatingIterator() {
        return ServerDetails.getInstantiatingIterator();
    }

}
