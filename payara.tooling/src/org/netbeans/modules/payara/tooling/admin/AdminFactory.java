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
// Portions Copyright [2017-2018] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.tooling.admin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.netbeans.modules.payara.tooling.data.PayaraAdminInterface;
import org.netbeans.modules.payara.tooling.data.PayaraVersion;
import org.netbeans.modules.payara.tooling.logging.Logger;
import org.netbeans.modules.payara.tooling.data.PayaraServer;

/**
 * Payara Abstract Server Command Factory.
 * <p/>
 * Selects correct Payara server administration functionality depending
 * on given Payara server entity object.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 * @author Gaurav Gupta
 */
public abstract class AdminFactory {

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Logger instance for this class. */
    private static final Logger LOGGER = new Logger(AdminFactory.class);

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates specific <code>AdminFactory</code> child class instance
     * to build Payara server administration command runner and data objects
     * based on provided Payara server version.
     * <p>
     * @param version Payara server version.
     * @return Child factory class instance to work with given Payara server.
     */
    static AdminFactory getInstance(final PayaraVersion version)
            throws CommandException {
        switch (version) {
            // Use HTTP interface for older than 3.

            // Use REST interface for Payara 4.
            case PF_4_1_144:
            case PF_4_1_151:
            case PF_4_1_153:
            case PF_4_1_1_154:
            case PF_4_1_1_161:
            case PF_4_1_1_162:
            case PF_4_1_1_163:
            case PF_4_1_1_171:
            case PF_4_1_2_172:
            case PF_4_1_2_173:
            case PF_4_1_2_174:
            case PF_4_1_2_181:
            case PF_5_181:
            case PF_5_182:
            case PF_5_183:
            case PF_5_184:
                return AdminFactoryRest.getInstance();
            // Anything else is not unknown.
            default:
                throw new CommandException(CommandException.UNKNOWN_VERSION);
        }
    }

    /**
     * Creates specific <code>AdminFactory</code> child class instance
     * to build Payara server administration command runner and data objects
     * based on provided Payara server administration interface type.
     * <p/>
     * @param adminInterface Payara server administration interface type.
     * @return Child factory class instance to work with given Payara server.
     */
    public static AdminFactory getInstance(
            final PayaraAdminInterface adminInterface) throws CommandException {
        switch (adminInterface) {
            case REST: return AdminFactoryRest.getInstance();
            case HTTP: return AdminFactoryHttp.getInstance();
            // Anything else is unknown.
            default:
                throw new CommandException(
                        CommandException.UNKNOWN_ADMIN_INTERFACE);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Abstract methods                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Build runner for command interface execution and connect it with
     * provided <code>Command</code> instance.
     * <p/>
     * @param srv Target Payara server.
     * @param cmd Payara server administration command entity.
     * @return Payara server administration command execution object.
     */
    public abstract Runner getRunner(
            final PayaraServer srv, final Command cmd);

    ////////////////////////////////////////////////////////////////////////////
    // Methods                                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs an instance of selected <code>Runner</code> child class.
     * <p/>
     * @param srv Target Payara server.
     * @param cmd Payara server administration command entity.
     * @param runnerClass Class of newly instantiated <code>runner</code>
     * @return Payara server administration command execution object.
     * @throws <code>CommandException</code> if construction of new instance
     *         fails.
     */
    Runner newRunner(final PayaraServer srv, final Command cmd,
            final Class runnerClass) throws CommandException {
        final String METHOD = "newRunner";
        Constructor<Runner> con = null;
        Runner runner = null;
        try {
            con = runnerClass.getConstructor(PayaraServer.class, Command.class);
        } catch (NoSuchMethodException | SecurityException nsme) {
            throw new CommandException(CommandException.RUNNER_INIT, nsme);
        }
        if (con == null) {
            return runner;
        }
        try {
            runner = con.newInstance(srv, cmd);
        } catch (InstantiationException | IllegalAccessException ie) {
            throw new CommandException(CommandException.RUNNER_INIT, ie);
        } catch (InvocationTargetException ite) {
            LOGGER.log(Level.WARNING, "exceptionMsg", ite.getMessage());
            Throwable t = ite.getCause();
            if (t != null) {
                LOGGER.log(Level.WARNING, "cause", t.getMessage());
            }
            throw new CommandException(CommandException.RUNNER_INIT, ite);
        }
        return runner;
    }

}
