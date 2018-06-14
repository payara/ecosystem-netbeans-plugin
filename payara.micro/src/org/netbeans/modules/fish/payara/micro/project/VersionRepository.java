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

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
public class VersionRepository {

    private static VersionRepository versionRepository;
    private static final List<MicroVersion> MICRO_VERSIONS = new ArrayList<>();

    private VersionRepository() {
        MICRO_VERSIONS.add(new MicroVersion("5.182", "8.0"));
        MICRO_VERSIONS.add(new MicroVersion("5.181", "8.0"));
        MICRO_VERSIONS.add(new MicroVersion("4.1.2.181", "7.0"));
        MICRO_VERSIONS.add(new MicroVersion("4.1.2.174", "7.0"));
    }

    public static VersionRepository getInstance() {
        if (versionRepository == null) {
            versionRepository = new VersionRepository();
        }
        return versionRepository;
    }

    public List<MicroVersion> getMicroVersion() {
        return MICRO_VERSIONS;
    }
    
    public static Optional<MicroVersion> toMicroVersion(String microVersion) {
        return MICRO_VERSIONS
                .stream()
                .filter(micro -> micro.getVersion().equals(microVersion))
                .findAny();
    }

    public String getJavaEEVersion(String microVersion) {
        return MICRO_VERSIONS.stream()
                .filter(micro -> micro.getVersion().equals(microVersion))
                .map(MicroVersion::getJavaeeVersion)
                .findAny().get();
    }

}
