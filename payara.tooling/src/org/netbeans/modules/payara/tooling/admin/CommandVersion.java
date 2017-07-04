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
package org.netbeans.modules.payara.tooling.admin;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.netbeans.modules.payara.tooling.PayaraIdeException;
import org.netbeans.modules.payara.tooling.data.PayaraVersion;
import org.netbeans.modules.payara.tooling.logging.Logger;
import org.netbeans.modules.payara.tooling.utils.ServerUtils;
import org.netbeans.modules.payara.tooling.data.PayaraServer;

/**
 * Payara Server Version Command Entity.
 * <p/>
 * Holds data for command. Objects of this class are created by API user.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 */
@RunnerHttpClass
@RunnerRestClass
public class CommandVersion extends Command {

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Logger instance for this class. */
    private static final Logger LOGGER = new Logger(CommandVersion.class);

    /** Command string for version command. */
    private static final String COMMAND = "version";

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Retrieve version from server.
     * <p/>
     * @param server Payara server entity.
     * @return Payara command result containing version string returned
     *         by server.
     * @throws PayaraIdeException When error occurred during administration
     *         command execution.
     */
    public static ResultString getVersion(final PayaraServer server)
            throws PayaraIdeException {
        final String METHOD = "getVersion";
        Future<ResultString> future =
                ServerAdmin.<ResultString>exec(server, new CommandVersion());
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException
                | CancellationException ee) {
            throw new CommandException(LOGGER.excMsg(METHOD, "exception"),
                    ee.getLocalizedMessage());
        }
    }

    /**
     * Retrieve version from server.
     * <p/>
     * @param server Payara server entity.
     * @return Payara command result containing {@link PayaraVersion}
     *         object retrieved from server or <code>null</code> if no
     *         version was returned.
     * @throws PayaraIdeException When error occurred during administration
     *         command execution.
     */
    public static PayaraVersion getPayaraVersion(
            final PayaraServer server) {
        ResultString result;
        try {
            result = getVersion(server);
        } catch (CommandException ce) {
            return null;
        }
        String value = result != null
                ? ServerUtils.getVersionString(result.getValue()) : null;
        if (value != null) {
            return PayaraVersion.toValue(value);
        } else {
            return null;
        }
    }

    /**
     * Verifies if domain directory returned by version command result matches
     * domain directory of provided Payara server entity.
     * <p/>
     * @param result Version command result.
     * @param server Payara server entity.
     * @return For local server value of <code>true</code> means that server
     *         major and minor version value matches values returned by version
     *         command and value of <code>false</code> that they differs.
     */
    public static boolean verifyResult(
            final ResultString result, final PayaraServer server) {
        boolean verifyResult = false;
        String value = ServerUtils.getVersionString(result.getValue());
        if (value != null) {
            PayaraVersion valueVersion = PayaraVersion.toValue(value);
            PayaraVersion serverVersion = server.getVersion();
            if (valueVersion != null && serverVersion != null) {
                verifyResult = serverVersion.equals(valueVersion);
            }
        }
        return verifyResult;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs an instance of Payara server version command entity.
     */
    public CommandVersion() {
        super(COMMAND);
    }

}
