/*
 *
 * Copyright (c) 2018 Payara Foundation and/or its affiliates. All rights reserved.
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
package org.netbeans.modules.fish.payara.micro;

import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.netbeans.spi.project.ActionProvider;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
public class Constants {

    public static final String WAR_PACKAGING = "war";

    public static final String MAVEN_WAR_PROJECT_TYPE = "org-netbeans-modules-maven/" + NbMavenProject.TYPE_WAR;

    public static final String PAYARA_MICRO_MAVEN_PLUGIN = "fish.payara.maven.plugins:payara-micro-maven-plugin";

    public static final String RELOAD_FILE = ".reload";

    public static final String COMMAND_EXPLODE = "explode";
    public static final String COMPILE_EXPLODE_ACTION = "micro-complie-explode";
    public static final String EXPLODE_ACTION = "micro-explode";

    public static final String RUN_SINGLE_ACTION = ActionProvider.COMMAND_RUN_SINGLE + ".deploy";
    public static final String DEBUG_SINGLE_ACTION = ActionProvider.COMMAND_DEBUG_SINGLE + ".deploy";
    public static final String PROFILE_SINGLE_ACTION = ActionProvider.COMMAND_PROFILE_SINGLE + ".deploy";

    public static final String ARCHETYPE_GROUP_ID = "fish.payara.maven.archetypes";
    public static final String ARCHETYPE_ARTIFACT_ID = "payara-micro-maven-archetype";
    public static final String ARCHETYPE_VERSION = "1.0-SNAPSHOT";
    public static final String ARCHETYPE_REPOSITORY = "https://oss.sonatype.org/content/repositories/snapshots";

    
    @StaticResource
    public static final String PROJECT_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro.png";

    @StaticResource
    public static final String CLEAN_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-clean.png";

    @StaticResource
    public static final String BUILD_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-build.png";

    @StaticResource
    public static final String REBUILD_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-clean-build.png";

    @StaticResource
    public static final String START_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-start.png";

    @StaticResource
    public static final String RESTART_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-restart.png";

    @StaticResource
    public static final String RELOAD_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-reload.png";

    @StaticResource
    public static final String DEBUG_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-debug.png";
    
    @StaticResource
    public static final String PROFILE_ICON = "org/netbeans/modules/fish/payara/micro/project/resources/payara-micro-profile.png";

}
