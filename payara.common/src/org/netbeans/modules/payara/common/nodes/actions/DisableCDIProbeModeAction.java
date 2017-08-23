/*
 *
 * Copyright (c) 2017 Payara Foundation and/or its affiliates. All rights reserved.
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

package org.netbeans.modules.payara.common.nodes.actions;

import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.payara.tooling.admin.ResultString;
import org.netbeans.modules.payara.spi.ServerUtilities;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.actions.NodeAction;

/**
 * @author Gaurav Gupta
 */
public class DisableCDIProbeModeAction extends NodeAction {

    @Override
    protected void performAction(Node[] nodes) {
        if((nodes == null) || (nodes.length < 1)) {
            return;
        }

        RequestProcessor enabler = new RequestProcessor("pf-disable-cdi-probe-mode");
        
        for(Node node : nodes) {
            DisableCDIProbeModeCookie uCookie = node.getCookie(DisableCDIProbeModeCookie.class);

            if(uCookie != null) {
                final Future<ResultString> result = uCookie.disableCDIProbeMode();
                final Node pNode = node.getParentNode().getParentNode();
                final Node fnode = node;

                enabler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result.get(ServerUtilities.ACTION_TIMEOUT, ServerUtilities.ACTION_TIMEOUT_UNIT);
                        } catch(TimeoutException ex) {
                            Logger.getLogger("payara").log(Level.WARNING, "Disable CDI probe mode action timed out for {0}", fnode.getDisplayName());
                        } catch (InterruptedException ie) {
                            // we can ignore this
                        }catch(Exception ex) {
                            Logger.getLogger("payara").log(Level.INFO, ex.getLocalizedMessage(), ex);
                        }

                    }
                });
            }
        }
    }

    @Override
    protected boolean enable(Node[] nodes) {
        for(Node node : nodes) {
            DisableCDIProbeModeCookie cookie = node.getCookie(DisableCDIProbeModeCookie.class);
            if(cookie == null || cookie.isRunning()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(DisableCDIProbeModeAction.class, "LBL_DisableCDIProbeModeAction");
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    public org.openide.util.HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
}
