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

package org.netbeans.modules.payara.tooling.data.cloud;

/**
 * Payara Cloud User Account Entity Interface.
 * <p/>
 * Payara Cloud User Account entity instance which is used when not defined
 * externally in IDE.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 */
public class PayaraAccountEntity implements PayaraAccount {

    ////////////////////////////////////////////////////////////////////////////
    // Instance attributes                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /** Payara cloud user account name (display name in IDE).
     *  Used as key attribute. */
    protected String name;

    /** Payara cloud server URL. Used as key attribute. */
    private String url;

    /** Payara cloud account name. */
    protected String account;

    /** Payara cloud account user name. */
    protected String userName;

    /** Payara cloud account user password. */
    protected String userPassword;

    /** Payara cloud entity reference. */
    protected PayaraCloud cloudEntity;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs empty class instance. No default values are set.
     */
    public PayaraAccountEntity() {
    }

    /**
     * Constructs class instance with ALL values set.
     * <p/>
     * @param name         Payara cloud account name to set.
     * @param url          Payara cloud server URL.
     * @param account      Payara cloud host to set.
     * @param userName     Payara cloud account user name to set.
     * @param userPassword Payara cloud account user password to set.
     * @param cloudEntity  Payara cloud entity reference to set.
     */
    public PayaraAccountEntity(String name, String account, String userName,
            String userPassword, String url, PayaraCloud cloudEntity) {
        this.name = name;
        this.url = url;
        this.account = account;
        this.userName = userName;
        this.userPassword = userPassword;
        this.cloudEntity = cloudEntity;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getters and Setters                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Get Payara cloud user account display name.
     * <p/>
     * Key attribute.
     * <p/>
     * This is display name given to the cloud user account.
     * <p/>
     * @return Payara cloud user account display name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set Payara cloud user account display name.
     * <p/>
     * Key attribute.
     * <p/>
     * This is display name given to the cloud user account.
     * <p/>
     * @param name Payara cloud user account display name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Payara cloud URL.
     * <p/>
     * Key attribute.
     * <p/>
     * @return Cloud URL.
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Set Payara cloud URL.
     * <p/>
     * Key attribute.
     * <p/>
     * @param url Cloud URL to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get Payara cloud account name.
     * <p/>
     * @return Payara cloud account name.
     */
    @Override
    public String getAcount() {
        return account;
    }

    /**
     * Set Payara cloud account name.
     * <p/>
     * @param account Payara cloud account name to set.
     */
    public void setAcount(String account) {
        this.account = account;
    }

    /**
     * Get Payara cloud user name under account.
     * <p/>
     * @return Payara cloud user name under account.
     */
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * Set Payara cloud user name under account.
     * <p/>
     * @param userName Payara cloud user name under account to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get Payara cloud user password under account.
     * <p/>
     * @return Payara cloud user password under account.
     */
    @Override
    public String getUserPassword() {
        return userPassword;
    }


    /**
     * Set Payara cloud user password under account.
     * <p/>
     * @param userPassword Payara cloud user password under account to set.
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Get Payara cloud entity reference.
     * <p/>
     * @return Payara cloud entity reference.
     */
    @Override
    public PayaraCloud getCloudEntity() {
        return cloudEntity;
    }

    /**
     * Set Payara cloud entity reference.
     * <p/>
     * @param cloudEntity Payara cloud entity reference to set.
     */
    public void setCloudEntity(PayaraCloud cloudEntity) {
        this.cloudEntity = cloudEntity;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods                                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * String representation of this Payara cloud entity.
     * <p/>
     * @return String representation of this Payara cloud entity.
     */
    @Override
    public String toString() {
        return name;
    }

}

