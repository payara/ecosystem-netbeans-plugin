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

package org.netbeans.modules.payara.tooling.server.state;

import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.payara.tooling.PayaraStatus;
import org.netbeans.modules.payara.tooling.data.DataException;
import org.netbeans.modules.payara.tooling.logging.Logger;

/**
 * Server status check internal state.
 * <p/>
 * Internally there are more states to be recognized when server is partially up
 * but not fully responding. This helps to use just subset of checks in some
 * states and also to use different checks synchronization strategy.
 * <p/>
 * @author Tomas Kraus
 */
public enum StatusJobState {
    ////////////////////////////////////////////////////////////////////////////
    // Enum values                                                            //
    ////////////////////////////////////////////////////////////////////////////

    /** Server status checks are turned off. */
    NO_CHECK,

    /** Server status is unknown. */
    UNKNOWN,

    /** Server status yet unknown but administrator port is alive. */
    UNKNOWN_PORT,

    /** Server is offline (not running, not responding and administrator port
     *  is not alive). */
    OFFLINE,

    /** Server is offline (not responding but running and administrator port
     *  is alive). */
    OFFLINE_PORT,

    /** Server start or restart was requested but server is still not
     *  responding and administrator port is not alive. */
    STARTUP,

    /** Server start or restart was requested but server is still not
     *  bully responding but administrator port is alive. */
    STARTUP_PORT,

    /** Server is running and responding. */
    ONLINE,

    /** Server shutdown was requested but server is still running, responding
     *  and administrator port is alive. */
    SHUTDOWN,
    
    /** Server shutdown was requested but server is still running, administrator
     *  port is alive but server is not responding. . */
    SHUTDOWN_PORT;

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Logger instance for this class. */
    private static final Logger LOGGER = new Logger(StatusJobState.class);

    /** Payara version enumeration length. */
    public static final int length = StatusJobState.values().length;

    /**  A <code>String</code> representation of NO_CHECK value. */
    private static final String NO_CHECK_STR = "NO_CHECK";

    /**  A <code>String</code> representation of UNKNOWN value. */
    private static final String UNKNOWN_STR = "UNKNOWN";

    /**  A <code>String</code> representation of UNKNOWN_PORT value. */
    private static final String UNKNOWN_PORT_STR = "UNKNOWN_PORT";

    /**  A <code>String</code> representation of OFFLINE value. */
    private static final String OFFLINE_STR = "OFFLINE";

    /**  A <code>String</code> representation of OFFLINE_PORT value. */
    private static final String OFFLINE_PORT_STR = "OFFLINE_PORT";

    /**  A <code>String</code> representation of STARTUP value. */
    private static final String STARTUP_STR = "STARTUP";

    /**  A <code>String</code> representation of STARTUP_PORT value. */
    private static final String STARTUP_PORT_STR = "STARTUP_PORT";

    /**  A <code>String</code> representation of ONLINE value. */
    private static final String ONLINE_STR = "ONLINE";

    /**  A <code>String</code> representation of SHUTDOWN value. */
    private static final String SHUTDOWN_STR = "SHUTDOWN";

    /**  A <code>String</code> representation of SHUTDOWN_PORT value. */
    private static final String SHUTDOWN_PORT_STR = "SHUTDOWN_PORT";

    /** Stored <code>String</code> values for backward <code>String</code>
     *  conversion. */
    private static final Map<String, StatusJobState> stringValuesMap
            = new HashMap<>(length);
    static {
        for (StatusJobState state : StatusJobState.values()) {
            stringValuesMap.put(state.toString().toUpperCase(), state);
        }
    }

    /** Server status check internal state to public Payara server status
     *  translation table. */
    private static final PayaraStatus toPayaraStatus[] = {
        PayaraStatus.UNKNOWN,  // NO_CHECK
        PayaraStatus.UNKNOWN,  // UNKNOWN
        PayaraStatus.UNKNOWN,  // UNKNOWN_PORT
        PayaraStatus.OFFLINE,  // OFFLINE
        PayaraStatus.OFFLINE,  // OFFLINE_PORT
        PayaraStatus.STARTUP,  // STARTUP
        PayaraStatus.STARTUP,  // STARTUP_PORT
        PayaraStatus.ONLINE,   // ONLINE
        PayaraStatus.SHUTDOWN, // SHUTDOWN
        PayaraStatus.SHUTDOWN  // SHUTDOWN_PORT
    };

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////
   
    /**
     * Returns a <code>PayaraStatus</code> with a value represented by the
     * specified <code>String</code>. The <code>PayaraStatus</code> returned
     * represents existing value only if specified <code>String</code>
     * matches any <code>String</code> returned by <code>toString</code> method.
     * Otherwise <code>null</code> value is returned.
     * <p>
     * @param name Value containing <code>PayaraStatus</code> 
     *             <code>toString</code> representation.
     * @return <code>PayaraStatus</code> value represented
     *         by <code>String</code> or <code>null</code> if value
     *         was not recognized.
     */
    public static StatusJobState toValue(final String name) {
        if (name != null) {
            return (stringValuesMap.get(name.toUpperCase()));
        } else {
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods                                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Convert <code>StatusJobState</code> value to <code>String</code>.
     * <p/>
     * @return A <code>String</code> representation of the value of this object.
     */
    @Override
    public String toString() {
        final String METHOD = "toString";
        switch (this) {
            case NO_CHECK:      return NO_CHECK_STR;
            case UNKNOWN:       return UNKNOWN_STR;
            case UNKNOWN_PORT:  return UNKNOWN_PORT_STR;
            case OFFLINE:       return OFFLINE_STR;
            case OFFLINE_PORT:  return OFFLINE_PORT_STR;
            case STARTUP:       return STARTUP_STR;
            case STARTUP_PORT:  return STARTUP_PORT_STR;
            case ONLINE:        return ONLINE_STR;
            case SHUTDOWN:      return SHUTDOWN_STR;
            case SHUTDOWN_PORT: return SHUTDOWN_PORT_STR;
            // This is unrecheable. Being here means this class does not handle
            // all possible values correctly.
            default: throw new DataException(
                        LOGGER.excMsg(METHOD, "invalidVersion"));
        }
    }
    
    /**
     * Convert <code>StatusJobState</code> value to {@link PayaraStatus}.
     * <p/>
     * @return A {@link PayaraStatus} representation of the value
     *         of this object.
     */
    PayaraStatus toPayaraStatus() {
        return toPayaraStatus[this.ordinal()];
    }

}
