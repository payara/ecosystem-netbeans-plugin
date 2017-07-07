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
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.tooling.data;


/**
 * Payara server entity interface.
 * <p/>
 * Payara Server entity interface allows to use foreign entity classes.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 */
public interface PayaraServer {

    ////////////////////////////////////////////////////////////////////////////
    // Interface Methods                                                      //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Get Payara server name.
     * <p/>
     * @return The name.
     */
    public String getName();

    /**
     * Get Payara server host.
     * <p/>
     * @return The host.
     */
    public String getHost();

    /**
     * Get Payara server port.
     * <p/>
     * @return The port.
     */
    public int getPort();

   /**
     * Get Payara server administration port.
     * <p/>
     * @return The administration port.
     */
    public int getAdminPort();

    /**
     * Get Payara server administration user name.
     * <p/>
     * @return The adminUser.
     */
    public String getAdminUser();

    /**
     * Get Payara server administration user password.
     * <p/>
     * @return The adminPassword.
     */
    public String getAdminPassword();

    /**
     * Get Payara server domains folder.
     * <p/>
     * @return Domains folder.
     */
    public String getDomainsFolder();

    /**
     * Get Payara server domain name.
     * <p/>
     * @return Server domain name.
     */
    public String getDomainName();

    /**
     * Get Payara server URL.
     * <p/>
     * @return Server URL.
     */
    public String getUrl();

    /**
     * Get Payara server home which is <code>payara</code> subdirectory
     * under installation root.
     * <p/>
     * @return Server installation root.
     */
    public String getServerHome();

    /**
     * Get Payara server installation directory.
     * <p/>
     * @return Server server installation directory.
     */
    public String getServerRoot();

    /** Get Payara server version.
     * <p/>
     * @return The version
     */
    public PayaraVersion getVersion();

    /**
     * Get Payara server administration interface type.
     * <p/>
     * @return Payara server administration interface type.
     */
    public PayaraAdminInterface getAdminInterface();

    /**
     * Get information if this Payara server instance is local or remote.
     * <p/>
     * Local Payara server instance has domains folder attribute set while
     * remote does not.
     * <p/>
     * @return Value of <code>true</code> when this Payara server instance
     *         is remote or <code>false</code> otherwise.
     */
    public boolean isRemote();

}
