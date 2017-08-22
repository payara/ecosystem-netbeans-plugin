/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010-2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.payara.common.parser.TreeParser;
import org.netbeans.modules.payara.common.wizards.ServerWizardIterator;
import org.netbeans.modules.payara.tooling.data.PayaraVersion;
import org.netbeans.modules.payara.tooling.utils.ServerUtils;
import org.openide.WizardDescriptor;
import org.openide.util.NbBundle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * @author vkraemer
 */
public enum ServerDetails {
    
    /**
     * details for an instance of Payara Server 4.1.144
     */
    PAYARA_SERVER_4_1_144(NbBundle.getMessage(ServerDetails.class,"STR_41144_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        41144,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.144/payara.zip", // NOI18N
        null 
    ),

    /**
     * details for an instance of Payara Server 4.1.151
     */
    PAYARA_SERVER_4_1_151(NbBundle.getMessage(ServerDetails.class,"STR_41151_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        41151,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.151/payara-4.1.151.zip", // NOI18N
        null 
    ),
        
    /**
     * details for an instance of Payara Server 4.1.152
     */
    PAYARA_SERVER_4_1_152(NbBundle.getMessage(ServerDetails.class,"STR_41152_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        41152,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.152/payara-4.1.152.zip", // NOI18N
        null 
    ),
    
    /**
     * details for an instance of Payara Server 4.1.153
     */
    PAYARA_SERVER_4_1_153(NbBundle.getMessage(ServerDetails.class,"STR_41153_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        41153,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.153/payara-4.1.153.zip", // NOI18N
        null 
    ),

    /**
     * details for an instance of Payara Server 4.1.1.154
     */
    PAYARA_SERVER_4_1_1_154(NbBundle.getMessage(ServerDetails.class,"STR_411154_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        411154,
        "hhttps://github.com/payara/Payara/releases/download/payara-server-4.1.1.154/payara-4.1.1.154.zip", // NOI18N
        null 
    ),

    /**
     * details for an instance of Payara Server 4.1.1.161
     */
    PAYARA_SERVER_4_1_1_161(NbBundle.getMessage(ServerDetails.class,"STR_411161_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        411161,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.1.161/payara-4.1.1.161.zip", // NOI18N
        null 
    ),

    /**
     * details for an instance of Payara Server 4.1.1.162
     */
    PAYARA_SERVER_4_1_1_162(NbBundle.getMessage(ServerDetails.class,"STR_411162_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        411162,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.1.162/payara-4.1.1.162.zip", // NOI18N
        null 
    ),

    /**
     * details for an instance of Payara Server 4.1.1.163
     */
    PAYARA_SERVER_4_1_1_163(NbBundle.getMessage(ServerDetails.class,"STR_411163_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        411163,
        "https://github.com/payara/Payara/releases/download/4.1.1.163/payara-4.1.1.163.zip", // NOI18N
        null 
    ),
    
    /**
     * details for an instance of Payara Server 4.1.1.164
     */
    PAYARA_SERVER_4_1_1_164(NbBundle.getMessage(ServerDetails.class,"STR_411164_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        411164,
        "http://bit.ly/2eG8vfN", // NOI18N
        null 
    ),
    
    /**
     * details for an instance of Payara Server 4.1.1.171
     */
    PAYARA_SERVER_4_1_1_171(NbBundle.getMessage(ServerDetails.class,"STR_411171_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        411171,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.1.171/payara-4.1.1.171.zip", // NOI18N
        null 
    ),

    /**
     * details for an instance of Payara Server 4.1.2.172
     */
    PAYARA_SERVER_4_1_2_172(NbBundle.getMessage(ServerDetails.class,"STR_412172_SERVER_NAME", new Object[]{}), // NOI18N
        "deployer:pfv3ee6wc", // NOI18N
        412172,
        "https://github.com/payara/Payara/releases/download/payara-server-4.1.2.172/payara-4.1.2.172.zip", // NOI18N
        null 
    );


    /**
     * Creates an iterator for a wizard to instantiate server objects.
     * <p/>
     * @return Server wizard iterator initialized with supported Payara
     * server versions.
     */
    public static WizardDescriptor.InstantiatingIterator
            getInstantiatingIterator() {
        return new ServerWizardIterator(
                new ServerDetails[]{
                    PAYARA_SERVER_4_1_2_172,
                    PAYARA_SERVER_4_1_1_171,
                    PAYARA_SERVER_4_1_1_164,
                    PAYARA_SERVER_4_1_1_163,
                    PAYARA_SERVER_4_1_1_162,
                    PAYARA_SERVER_4_1_1_161,
                    PAYARA_SERVER_4_1_1_154,
                    PAYARA_SERVER_4_1_153,
                    PAYARA_SERVER_4_1_152,
                    PAYARA_SERVER_4_1_151,
                    PAYARA_SERVER_4_1_144},
                new ServerDetails[]{
                    PAYARA_SERVER_4_1_2_172,
                    PAYARA_SERVER_4_1_1_171,
                    PAYARA_SERVER_4_1_1_164,
                    PAYARA_SERVER_4_1_1_163,
                    PAYARA_SERVER_4_1_1_162,
                    PAYARA_SERVER_4_1_1_161}
        );
        
    }

    /**
     * Determine the version of the Payara Server installed in a directory
     * @param payaraDir the directory that holds a Payara installation
     * @return -1 if the directory is not a Payara server install
     */
    public static int getVersionFromInstallDirectory(File payaraDir)  {
        if (payaraDir == null) {
            return -1;
        }

        PayaraVersion version
                = ServerUtils.getServerVersion(payaraDir.getAbsolutePath());
        ServerDetails sd = null;
        if (version != null) {
            switch (version) {
                case PF_4_1_144:
                    return PAYARA_SERVER_4_1_144.getVersion();
                case PF_4_1_151:
                    return PAYARA_SERVER_4_1_151.getVersion();
                case PF_4_1_153:
                    return PAYARA_SERVER_4_1_153.getVersion();
                case PF_4_1_1_154:
                    return PAYARA_SERVER_4_1_1_154.getVersion();
                case PF_4_1_1_161:
                    return PAYARA_SERVER_4_1_1_161.getVersion();
                case PF_4_1_1_162:
                    return PAYARA_SERVER_4_1_1_162.getVersion();
                case PF_4_1_1_163:
                    return PAYARA_SERVER_4_1_1_163.getVersion();
                case PF_4_1_1_171:
                    return PAYARA_SERVER_4_1_1_171.getVersion();
                case PF_4_1_1_172:
                    return PAYARA_SERVER_4_1_2_172.getVersion();
                default:
                    return -1;
            }
        }
        return  null==sd?-1:sd.getVersion();
    }

    


    /**
     * Determine the version of the Payara Server that wrote the domain.xml file
     * 
     * @param domainXml the file to analyze
     * @return -1 if domainXml is null, unreadable or not a directory
     * @throws IllegalStateException if domainXml cannot be parsed
     */
    @Deprecated
    public static int getVersionFromDomainXml(File domainXml) throws IllegalStateException {
        if (null == domainXml || !domainXml.isFile() || !domainXml.canRead()) {
            return -1;
        }
        return hasDefaultConfig(domainXml) ? PAYARA_SERVER_4_1_2_172.getVersion() :
            PAYARA_SERVER_4_1_1_171.getVersion();
    }

    private static boolean hasDefaultConfig(File domainXml) throws IllegalStateException {
        DomainParser dp = new DomainParser();
        List<TreeParser.Path> paths = new ArrayList<TreeParser.Path>();
        paths.add(new TreeParser.Path("/domain/configs/config",dp)); // NOI18N
        TreeParser.readXml(domainXml, paths);
        return dp.hasDefaultConfig();
    }
    
    private String displayName;
    private String uriFragment;
    private String indirectUrl;
    private String directUrl;
    private int versionInt;
    

    ServerDetails(String displayName, String uriFragment, int versionInt,
            String directUrl, String indirectUrl) {
            this.displayName = displayName;
            this.uriFragment = uriFragment;
            this.indirectUrl = indirectUrl;
            this.directUrl = directUrl;
            this.versionInt = versionInt;
    }
    
    @Override 
    public String toString() {
        return displayName;
    }

    public String getUriFragment() {
        return uriFragment;
    }

    public int getVersion() {
        return versionInt;
    }

    /**
     * Determine if the glassfishDir holds a valid install of this release of
     * Payara Server.
     * @param payaraDir
     * @return true if the glassfishDir holds this particular server version.
     */
    public boolean isInstalledInDirectory(File payaraDir) {
        return getVersionFromInstallDirectory(payaraDir) == this.getVersion();
    }

    static class DomainParser extends TreeParser.NodeReader {

        private boolean hasDefaultConfig = false;
        private boolean hasDefaultConfig() {
            return hasDefaultConfig;
        }

        @Override
        public void readAttributes(String qname, Attributes attributes) throws SAXException {
            String name = attributes.getValue("name"); // NOI18N
            if ("default-config".equals(name)) { // NOI18N
                hasDefaultConfig = true;
            }
        }

    }

    public String getDirectUrl() {
        return directUrl;
    }

    public String getIndirectUrl() {
        return indirectUrl;
    }

}
