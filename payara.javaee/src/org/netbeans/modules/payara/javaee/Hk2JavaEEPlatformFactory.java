/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2015 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.payara.javaee;

import javax.enterprise.deploy.spi.DeploymentManager;
import org.netbeans.api.j2ee.core.Profile;
import org.netbeans.modules.payara.tooling.data.PayaraJavaEEConfig;
import org.netbeans.modules.payara.tooling.data.PayaraJavaSEConfig;
import org.netbeans.modules.payara.tooling.data.PayaraServer;
import org.netbeans.modules.payara.tooling.data.PayaraVersion;
import org.netbeans.modules.payara.tooling.server.config.ConfigBuilder;
import org.netbeans.modules.payara.tooling.server.config.ConfigBuilderProvider;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.plugins.spi.J2eePlatformFactory;
import org.netbeans.modules.j2ee.deployment.plugins.spi.J2eePlatformImpl;
import org.openide.util.NbBundle;


/**
 * Payara JavaEE platform factory.
 * <p/>
 * Creates Payara JavaEE platform instances for individual Payara server
 * instances from deployment manager.
 * <p/>
 * Works as a singleton instance in regular use-cases. Unfortunately
 * <code>layer.xml</code> does not allow to work with singletons so we allow
 * it to create more instances.
 * <p/>
 * @author Tomas Kraus, Vince Kraemer
 */
public class Hk2JavaEEPlatformFactory extends J2eePlatformFactory {

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** GlassFish V3 JavaEE platform lookup key. */
    private static final String V3_LOOKUP_KEY
            = "J2EE/DeploymentPlugins/pfv3ee6/Lookup";

    /** Payara V4 JavaEE platform lookup key.
     *  <p/>We will keep V3 value now because no one knows what will get broken
     *  when changing it. */
    private static final String V4_LOOKUP_KEY = V3_LOOKUP_KEY;

    /** Payara JavaEE platform factory singleton object. */
    private static volatile Hk2JavaEEPlatformFactory instance;

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Return existing singleton instance of this class or create a new one
     * when no instance exists.
     * <p>
     * @return {@see Hk2JavaEEPlatformFactory} singleton instance.
     */
    public static Hk2JavaEEPlatformFactory getFactory() {
        if (instance != null) {
            return instance;
        }
        synchronized(Hk2JavaEEPlatformFactory.class) {
            if (instance == null) {
                instance = new Hk2JavaEEPlatformFactory();
            }
        }
        return instance;
    }

    /**
     * Get Payara JavaEE platform name from bundle properties for given
     * Payara server version.
     * <p/>
     * @param version Payara server version used to pick up display name.
     * @return Payara JavaEE platform name related to given server version.
     */
    private static String getDisplayName(final PayaraVersion version) {
        final int ord = version.ordinal();
        if (ord >= PayaraVersion.PF_4_1_144.ordinal()) {
            return NbBundle.getMessage(
                    Hk2JavaEEPlatformFactory.class, "MSG_V4ServerPlatform");
        } else {
            return NbBundle.getMessage(
                    Hk2JavaEEPlatformFactory.class, "MSG_V3ServerPlatform");
        }
    }

    /**
     * Get Payara JavaEE library name from bundle properties for given
     * Payara server version.
     * <p/>
     * @param version Payara server version used to pick up display name.
     * @return Payara JavaEE library name related to given server version.
     */
    private static String getLibraryName(final PayaraVersion version) {
        final int ord = version.ordinal();
        if (ord >= PayaraVersion.PF_4_1_144.ordinal()) {
            return NbBundle.getMessage(
                    Hk2JavaEEPlatformFactory.class, "LBL_V4ServerLibraries");
        } else {
            return NbBundle.getMessage(
                    Hk2JavaEEPlatformFactory.class, "LBL_V3ServerLibraries");
        }
    }

    /**
     * Get Payara JavaEE platform lookup key for given Payara
     * server version.
     * <p/>
     * @param version Payara server version used to pick up lookup key.
     * @return Lookup key for given Payara server version.
     */
    private static String getLookupKey(final PayaraVersion version) {
        final int ord = version.ordinal();
        if (ord >= PayaraVersion.PF_4_1_144.ordinal()) {
            return V4_LOOKUP_KEY;
        } else {
            return V3_LOOKUP_KEY;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // J2eePlatformFactory methods                                            //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Return {@see J2eePlatformImpl} for the given {@see DeploymentManager}.
     * <p/>
     * @param dm {@see DeploymentManager} object for which JavaEE platform
     *           environment object is created.
     */
    @Override
    public J2eePlatformImpl getJ2eePlatformImpl(final DeploymentManager dm) {
        if (dm instanceof Hk2DeploymentManager) {
            final PayaraServer server = ((Hk2DeploymentManager)dm)
                    .getCommonServerSupport().getInstance();
            final PayaraVersion version = server.getVersion();
            final ConfigBuilder cb = ConfigBuilderProvider.getBuilder(server);
            final PayaraJavaSEConfig javaSEConfig = cb.getJavaSEConfig(version);
            final PayaraJavaEEConfig javaEEConfig = cb.getJavaEEConfig(version);
            final String[] platforms = Hk2JavaEEPlatformImpl.nbJavaSEProfiles(
                    javaSEConfig.getPlatforms());
            final Profile[] profiles = Hk2JavaEEPlatformImpl
                    .nbJavaEEProfiles(javaEEConfig.getProfiles());
            final J2eeModule.Type[] types = Hk2JavaEEPlatformImpl
                    .nbModuleTypes(javaEEConfig.getModuleTypes());
            return new Hk2JavaEEPlatformImpl((Hk2DeploymentManager)dm,
                    platforms, profiles, types, getDisplayName(version),
                    getLibraryName(version), getLookupKey(version));
        }
        throw new IllegalArgumentException(
                "Deployment manager instance is not instance  of Hk2DeploymentManager");
    }

}
