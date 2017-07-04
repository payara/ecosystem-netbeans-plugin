/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012-2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.payara.javaee;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.netbeans.modules.payara.tooling.data.PayaraLibrary;
import org.netbeans.modules.payara.tooling.data.PayaraServer;
import org.netbeans.modules.payara.tooling.server.config.ConfigBuilder;
import org.netbeans.modules.payara.tooling.server.config.ConfigBuilderProvider;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.api.project.libraries.LibraryManager;
import static org.netbeans.modules.payara.javaee.ide.Hk2PluginProperties.fileToUrl;
import org.netbeans.modules.j2ee.deployment.common.api.J2eeLibraryTypeProvider;
import org.netbeans.spi.project.libraries.LibraryImplementation;
import org.openide.ErrorManager;
import org.openide.filesystems.FileUtil;
import org.openide.modules.InstalledFileLocator;

/**
 * Payara bundled libraries provider.
 * <p/>
 * Builds <code>Library</code> instance containing Jersey library from Payara
 * modules. Actually only GlassFish v3 and v4 are supported.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 */
public class Hk2LibraryProvider /*implements JaxRsStackSupportImplementation*/ {

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Library provider type. */
    private static final String PROVIDER_TYPE = "j2se";

    /** Java EE library name suffix to be added after server instance name.
     *  Java EE library name must be unique so combination of instance name
     *  and some common suffix is used. */
    private static final String JAVAEE_NAME_SUFFIX = " Java EE";

    /** Java EE library name suffix to be added after server instance name.
     *  Jersey library name must be unique so combination of instance name
     *  and some common suffix is used. */
    private static final String JERSEY_NAME_SUFFIX = " Jersey";

    /** JAX-RS library name suffix to be added after server instance name.
     *  JAX-RS library name must be unique so combination of instance name
     *  and some common suffix is used. */
    private static final String JAXRS_NAME_SUFFIX = " JAX-RS";

    /** Java EE library name pattern to search for it in
     *  <code>PayaraLibrary</code> list. */
    private Pattern JAVAEE_PATTERN = Pattern.compile("[jJ]ava {0,1}[eE]{2}");

    /** Jersey library name pattern to search for it in
     *  <code>PayaraLibrary</code> list. */
    private Pattern JERSEY_PATTERN = Pattern.compile("[jJ]ersey.*");

    /** JAX-RS library name pattern to search for it in
     *  <code>PayaraLibrary</code> list. */
    private Pattern JAXRS_PATTERN
            = Pattern.compile("[jJ][aA][xX][ -]{0,1}[rR][sS]");

    /** Code base for file locator. */
    static final String JAVAEE_DOC_CODE_BASE
            = "org.netbeans.modules.j2ee.platform";

    /** Internal {@see PayaraServer} to {@see Hk2LibraryProvider}
     *  mapping. */
    private static final Map <PayaraServer, Hk2LibraryProvider> providers
            = new HashMap();

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns {@see Hk2LibraryProvider} class instance for specific server
     * instance.
     * <p/>
     * Provider instances for individual {@see PayaraServer} instances
     * are shared.
     * <p/>
     * @param server {@see PayaraServer} instance for which provider
     *               is returned.
     * @return {@see Hk2LibraryProvider} class instance for given server
     *         instance.
     */
    public static Hk2LibraryProvider getProvider(PayaraServer server) {
        Hk2LibraryProvider provider;
        synchronized(providers) {
            if ((provider = providers.get(server)) == null)
                providers.put(
                        server, provider = new Hk2LibraryProvider(server));
        }
        return provider;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Instance attributes                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /** Library builder associated with current platform.
      * This attribute should be accessed only using {@see #getBuilder()} even
      * internally. */
    private volatile ConfigBuilder builder;

    /** Payara server home directory. */
    private final String serverHome;

    /** Payara server name. */
    private final String serverName;

    /** Payara server instance. */
    private final PayaraServer server;

    /** Java EE library name associated with current Payara server context.
     *  This is lazy initialized internal cache. Do not access this attribute
     *  outside {@see #getJavaEEName()} method! */
    private volatile String javaEEName = null;

    /** Jersey library name associated with current Payara server context.
     *  This is lazy initialized internal cache. Do not access this attribute
     *  outside {@see #getJerseyName()} method! */
    private volatile String jerseyName = null;

    /** Jersey JAX-RS name associated with current Payara server context.
     *  This is lazy initialized internal cache. Do not access this attribute
     *  outside {@see #getJaxRsName()} method! */
    private volatile String jaxRsName = null;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates an instance of Jersey library provider.
     * <p/>
     * @param server Payara server entity.
     */
    private Hk2LibraryProvider(PayaraServer server) {
        if (server == null) {
            throw new IllegalArgumentException(
                    "Payara server entity shall not be null.");
        }
        serverHome = server.getServerHome();
        serverName = server.getName();
        this.server = server;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods                                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Get Java EE library name for this server context.
     * <p/>
     * This library name shall be registered in default {@see LibraryManager}
     * and is unique for Jersey modules of given Payara server instance.
     * Library name is cached after first usage.
     * <p/>
     * @return Java EE library name for this server context.
     */
    public String getJavaEEName() {
        if (javaEEName != null) {
            return javaEEName;
        }
        synchronized (this) {
            StringBuilder sb = new StringBuilder(
                    serverName.length() + JAVAEE_NAME_SUFFIX.length());
            sb.append(serverName);
            sb.append(JAVAEE_NAME_SUFFIX);
            javaEEName = sb.toString();
        }
        return javaEEName;
    }

    /**
     * Get Jersey library name for this server context.
     * <p/>
     * This library name shall be registered in default {@see LibraryManager}
     * and is unique for Jersey modules of given Payara server instance.
     * Library name is cached after first usage.
     * <p/>
     * @return Jersey library name for this server context.
     */
    public String getJerseyName() {
        if (jerseyName != null) {
            return jerseyName;
        }
        synchronized (this) {
            StringBuilder sb = new StringBuilder(
                    serverName.length() + JERSEY_NAME_SUFFIX.length());
            sb.append(serverName);
            sb.append(JERSEY_NAME_SUFFIX);
            jerseyName = sb.toString();
        }
        return jerseyName;
    }

    /**
     * Get JAX-RS library name for this server context.
     * <p/>
     * This library name shall be registered in default {@see LibraryManager}
     * and is unique for Jersey modules of given Payara server instance.
     * Library name is cached after first usage.
     * <p/>
     * @return JAX-RS library name for this server context.
     */
    public String getJaxRsName() {
        if (jaxRsName != null) {
            return jaxRsName;
        }
        synchronized (this) {
            StringBuilder sb = new StringBuilder(
                    serverName.length() + JAXRS_NAME_SUFFIX.length());
            sb.append(serverName);
            sb.append(JAXRS_NAME_SUFFIX);
            jaxRsName = sb.toString();
        }
        return jaxRsName;
    }

    /**
     * Return Jersey libraries available in Payara.
     * <p/>
     * @return Jersey libraries available in Payara.
     */
    public Library getJerseyLibrary() {
        return getLibrary(JERSEY_PATTERN, getJerseyName());
    }

    /**
     * Set {@see LibraryImplementation} content for Jersey libraries
     * available in Payara.
     * <p/>
     * @param lib         Target {@see LibraryImplementation}.
     * @param libraryName Library name in returned Library instance.
     */
    public void setJerseyImplementation(
            LibraryImplementation lib, String libraryName) {
        setLibraryImplementationContent(lib, JERSEY_PATTERN, libraryName);
    }

    /**
     * Get {@see List} of class path {@see URL}s for Jersey libraries.
     * <p/>
     * @return {@see List} of class path {@see URL}s for Jersey libraries.
     */
    public List<URL> getJerseyClassPathURLs() {
        return getLibraryClassPathURLs(JERSEY_PATTERN);
    }

    /**
     * Return JAX-RS libraries available in Payara.
     * <p/>
     * @return JAX-RS libraries available in Payara.
     */
    public Library getJaxRsLibrary() {
        return getLibrary(JAXRS_PATTERN, getJaxRsName());
    }

   /**
     * Set {@see LibraryImplementation} content for JAX-RS libraries
     * available in Payara.
     * <p/>
     * @param lib         Target {@see LibraryImplementation}.
     * @param libraryName Library name in returned Library instance.
     */
    public void setJaxRsLibraryImplementation(
            LibraryImplementation lib, String libraryName) {
        setLibraryImplementationContent(lib, JAXRS_PATTERN, libraryName);
    }

    /**
     * Get {@see List} of class path {@see URL}s for JAX-RS libraries.
     * <p/>
     * @return {@see List} of class path {@see URL}s for JAX-RS libraries.
     */
    public List<URL> getJaxRsClassPathURLs() {
        return getLibraryClassPathURLs(JAXRS_PATTERN);
    }

    /**
     * Return Java EE libraries available in Payara.
     * <p/>
     * @return Java EE libraries available in Payara\.
     */
    public Library getJavaEELibrary() {
        return getLibrary(JAVAEE_PATTERN, getJavaEEName());
    }

     /**
     * Set {@see LibraryImplementation} content for Java EE libraries
     * available in Payara.
     * <p/>
     * @param lib         Target {@see LibraryImplementation}.
     * @param libraryName Library name in returned Library instance.
     */
    public void setJavaEELibraryImplementation(
            LibraryImplementation lib, String libraryName) {
        setLibraryImplementationContent(lib, JAVAEE_PATTERN, libraryName);
    }

    /**
     * Get {@see List} of class path {@see URL}s for Java EE libraries.
     * <p/>
     * @return {@see List} of class path {@see URL}s for Java EE libraries.
     */
    public List<URL> getJavaEEClassPathURLs() {
        return getLibraryClassPathURLs(JAVAEE_PATTERN);
    }

    /**
     * Return libraries available in Payara.
     * <p/>
     * @param namePattern Library name pattern to search for it in
     *                    <code>PayaraLibrary</code> list.
     * @param libraryName Library name in returned Library instance.
     * @return Requested Payara library.
     */
    private Library getLibrary(Pattern namePattern, String libraryName) {
        Library lib = LibraryManager.getDefault().getLibrary(libraryName);
        if (lib != null) {
            return lib;
        }
        ConfigBuilder cb = ConfigBuilderProvider.getBuilder(server);
        List<PayaraLibrary> gfLibs = cb.getLibraries(server.getVersion());
        for (PayaraLibrary gfLib : gfLibs) {
            if (namePattern.matcher(gfLib.getLibraryID()).matches()) {
                Map<String,List<URL>> contents
                        = new HashMap<String, List<URL>>(1);
                Map<String, String> properties = new HashMap<String, String>(2);
                contents.put("classpath", translateArchiveUrls(gfLib.getClasspath()));
                contents.put("javadoc", translateArchiveUrls(gfLib.getJavadocs()));
                properties.put("maven-dependencies", gfLib.getMavenDeps());
                properties.put("maven-repositories", "default");
                try {
                    return LibraryManager.getDefault().createLibrary(
                            PROVIDER_TYPE,
                            libraryName,
                            null,
                            null,
                            contents,
                            properties);
                } catch (IOException ioe) {
                    Logger.getLogger("payara-javaee").log(Level.WARNING,
                            "Could not create Jersey library for "
                            + serverName + ": ", ioe);
                }
            }
        }
        return null;
    }

    /**
     * Set {@see LibraryImplementation} content for given library name.
     * <p/>
     * @param lib         Target {@see LibraryImplementation}.
     * @param namePattern Library name pattern to search for it in
     *                    <code>PayaraLibrary</code> list.
     * @param libraryName Library name in returned Library instance.
     */
    private void setLibraryImplementationContent(LibraryImplementation lib,
            Pattern namePattern, String libraryName) {
        ConfigBuilder cb = ConfigBuilderProvider.getBuilder(server);
        List<PayaraLibrary> gfLibs = cb.getLibraries(server.getVersion());
        for (PayaraLibrary gfLib : gfLibs) {
            if (namePattern.matcher(gfLib.getLibraryID()).matches()) {
                List<String> javadocLookups = gfLib.getJavadocLookups();
                lib.setName(libraryName);
                // Build class path
                List<URL> cp = new ArrayList<URL>();
                for (URL url : gfLib.getClasspath()) {
                    if (FileUtil.isArchiveFile(url)) {
                        cp.add(FileUtil.getArchiveRoot(url));
                    } else {
                        cp.add(url);
                    }
                }
                // Build java docs
                List<URL> javadoc = new ArrayList<URL>();
                if (javadocLookups != null) {
                    for (String lookup : javadocLookups) {
                        try {
                            File j2eeDoc = InstalledFileLocator
                                    .getDefault().locate(lookup,
                                    JAVAEE_DOC_CODE_BASE, false);
                            if (j2eeDoc != null) {
                                javadoc.add(fileToUrl(j2eeDoc));
                            }
                        } catch (MalformedURLException e) {
                            ErrorManager.getDefault()
                                    .notify(ErrorManager.INFORMATIONAL, e);
                        }
                    }
                }
                lib.setContent(J2eeLibraryTypeProvider.VOLUME_TYPE_CLASSPATH,
                        cp);
                lib.setContent(J2eeLibraryTypeProvider.VOLUME_TYPE_JAVADOC,
                        javadoc);
            }
        }
    }

    /**
     * Get list of class path {@see URL}s for given library name.
     * <p/>
     * @param namePattern Library name pattern to search for it in
     *                    <code>PayaraLibrary</code> list.
     * @param libraryName Library name in returned Library instance.
     */
    private List<URL> getLibraryClassPathURLs(Pattern namePattern) {
        ConfigBuilder cb = ConfigBuilderProvider.getBuilder(server);
        List<PayaraLibrary> gfLibs = cb.getLibraries(server.getVersion());
        for (PayaraLibrary gfLib : gfLibs) {
            if (namePattern.matcher(gfLib.getLibraryID()).matches()) {
                return gfLib.getClasspath();
            }
        }
        return Collections.<URL>emptyList();
    }

    private List<URL> translateArchiveUrls(List<URL> urls) {
        List<URL> result = new ArrayList<>(urls.size());
        for (URL u : urls) {
            if (FileUtil.isArchiveFile(u)) {
                result.add(FileUtil.getArchiveRoot(u));
            } else {
                result.add(u);
            }
        }
        return result;
    }
}
