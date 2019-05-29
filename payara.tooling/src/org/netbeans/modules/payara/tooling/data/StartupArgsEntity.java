/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
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
 */
// Portions Copyright [2017-2019] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.tooling.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.payara.tooling.server.JDK;
import org.netbeans.modules.payara.tooling.server.JDK.Version;
import org.openide.util.Exceptions;

/**
 * Payara Server Entity.
 * <p/>
 * Local Payara Server entity instance which is used when not defined in IDE.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 */
public class StartupArgsEntity implements StartupArgs {

    ////////////////////////////////////////////////////////////////////////////
    // Instance attributes                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /** Command line arguments passed to bootstrap jar. */
    private List<String>payaraArgs;

    /** Command line arguments passed to JVM. */
    private List<String> javaArgs;

    /** Environment variables set before JVM execution. */
    private Map<String, String> environmentVars;

    /** Installation home of Java SDK used to run Payara. */
    private String javaHome;

    /** Version of Java SDK used to run Payara. */
    private Version javaVersion;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs empty class instance. No default values are set.
     */
    public StartupArgsEntity() {
    }

    /**
     * Constructs class instance with all values supplied.
     * <p/>
     * @param payaraArgs   Command line arguments passed to bootstrap jar.
     * @param javaArgs        Command line arguments passed to JVM.
     * @param environmentVars Environment variables set before JVM execution.
     * @param javaHome        Installation home of Java SDK used to
     *                        run Payara.
     */
    public StartupArgsEntity(List<String>payaraArgs, List<String> javaArgs,
            Map<String, String> environmentVars, String javaHome) {
        this.payaraArgs = payaraArgs;
        this.javaArgs = javaArgs;
        this.environmentVars = environmentVars;
        this.javaHome = javaHome;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getters and Setters                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Get command line arguments passed to bootstrap jar.
     * <p/>
     * @return Command line arguments passed to bootstrap jar.
     */
    @Override
    public List<String> getPayaraArgs() {
        return payaraArgs;
    }

    /**
     * Set command line arguments passed to bootstrap jar.
     * <p/>
     * @param payaraArgs Command line arguments passed to bootstrap jar.
     */
    public void setPayaraArgs(List<String> payaraArgs) {
        this.payaraArgs = payaraArgs;
    }

    /**
     * Get command line arguments passed to JVM.
     * <p/>
     * @return Command line arguments passed to JVM.
     */
    @Override
    public List<String> getJavaArgs() {
        return javaArgs;
    }

    /**
     * Set command line arguments passed to JVM.
     * <p/>
     * @param javaArgs Command line arguments passed to JVM.
     */
    public void getJavaArgs(List<String> javaArgs) {
        this.javaArgs = javaArgs;
    }

    /**
     * Get environment variables set before JVM execution.
     * <p/>
     * @return Environment variables set before JVM execution.
     */
    @Override
    public Map<String, String> getEnvironmentVars() {
        return environmentVars;
    }

    /**
     * Set environment variables set before JVM execution.
     * <p/>
     * @param environmentVars Environment variables set before JVM execution.
     */
    public void setEnvironmentVars(Map<String, String> environmentVars) {
        this.environmentVars = environmentVars;
    }

    /**
     * Get installation home of Java SDK used to run Payara.
     * <p/>
     * @return Installation home of Java SDK used to run Payara.
     */
    @Override
    public String getJavaHome() {
        return javaHome;
    }
    
    /**
     * Set installation home of Java SDK used to run Payara.
     * <p/>
     * @param javaHome Installation home of Java SDK used to run Payara.
     */
    public void getJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    /**
     * Get version of Java SDK used to run Payara.
     * <p/>
     * @return version of Java SDK used to run Payara.
     */
    @Override
    public Version getJavaVersion() {
        if(javaVersion == null && javaHome != null) {
            try (BufferedReader bufferedReader
                    = new BufferedReader(new FileReader(new File(javaHome, "release")));) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("JAVA_VERSION")) {
                        javaVersion = JDK.getVersion(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
                        break;
                    }
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return javaVersion;
    }

}
