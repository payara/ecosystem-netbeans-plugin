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
package org.netbeans.modules.fish.payara.micro.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINER;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.openide.windows.GlobalActionContextImpl;
import org.openide.explorer.ExplorerManager;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.ContextGlobalProvider;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.Lookup.Template;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * This class proxies the original ContextGlobalProvider and ensures the current
 * project remains in the GlobalContext regardless of the TopComponent
 * selection. The class also ensures that when a child node is selected within
 * the in Projects tab, the parent Project will be in the lookup.
 * <p>
 * To use this class you must have an implementation dependency on
 * org.openide.windows module.
 * <p>
 * Taken from http://wiki.netbeans.org/DevFaqAddGlobalContext
 *
 * @see ContextGlobalProvider
 * @see GlobalActionContextImpl
 * @author Bruce Schubert
 */
@ServiceProvider(service = ContextGlobalProvider.class, supersedes = "org.netbeans.modules.openide.windows.GlobalActionContextImpl")
public class GlobalActionContextProxy implements ContextGlobalProvider {

    /**
     * Additional content for our proxy lookup
     */
    private final InstanceContent content;
    /**
     * The primary lookup managed by the platform
     */
    private final Lookup globalContextLookup;
    /**
     * The project lookup managed by resultChanged
     */
    private Lookup projectLookup;
    /**
     * The actual proxyLookup returned by this class
     */
    private Lookup proxyLookup;
    /**
     * A lookup result that we listen to for Projects
     */
    private final Result<Project> resultProjects;
    /**
     * Listener for changes resultProjects
     */
    private final LookupListener resultListener = new LookupListenerImpl();
    /**
     * Listener for changes on the TopComponent registry
     */
    private final PropertyChangeListener registryListener = new RegistryPropertyChangeListener();
    /**
     * The last project selected
     */
    private Project lastProject;
    /**
     * Critical section lock
     */
    private final Object lock = new Object();
    private static final Logger LOGGER = Logger.getLogger(GlobalActionContextProxy.class.getName());
    public static final String PROJECT_LOGICAL_TAB_ID = "projectTabLogical_tc";
    public static final String PROJECT_FILE_TAB_ID = "projectTab_tc";

    public GlobalActionContextProxy() {
        this.content = new InstanceContent();
        this.globalContextLookup = new GlobalActionContextImpl().createGlobalContext();
        // Monitor the activation of the Projects Tab TopComponent
        TopComponent.getRegistry().addPropertyChangeListener(this.registryListener);
        // Monitor the existance of a Project in the principle lookup
        this.resultProjects = globalContextLookup.lookupResult(Project.class);
        this.resultProjects.addLookupListener(this.resultListener);
    }

    /**
     * Returns a ProxyLookup that adds the current Project instance to the
     * global selection returned by Utilities.actionsGlobalContext().
     *
     * @return a ProxyLookup that includes the original global context lookup.
     */
    @Override
    public Lookup createGlobalContext() {
        if (proxyLookup == null) {
            projectLookup = new AbstractLookup(content);
            proxyLookup = new ProxyLookup(globalContextLookup, projectLookup);
        }
        return proxyLookup;
    }

    /**
     * This class populates the proxy lookup with the currently selected project
     * found in the Projects tab.
     */
    private class RegistryPropertyChangeListener implements PropertyChangeListener {

        private TopComponent projectsTab = null;

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getPropertyName().equals(TopComponent.Registry.PROP_ACTIVATED_NODES)
                    || event.getPropertyName().equals(TopComponent.Registry.PROP_ACTIVATED)) {
                // Get a reference to the Projects window
                if (projectsTab == null) {
                    projectsTab = WindowManager.getDefault().findTopComponent(PROJECT_LOGICAL_TAB_ID);
                    if (projectsTab == null) {
                        LOGGER.log(SEVERE, "propertyChange: cannot find the Projects logical window ({0})", PROJECT_LOGICAL_TAB_ID);
                        return;
                    }
                }
                // Look for the current project in the Projects window when activated and handle
                // special case at startup when lastProject hasn't been initialized.
                Node[] nodes = null;
                TopComponent activated = TopComponent.getRegistry().getActivated();
                if (activated != null && activated.equals(projectsTab)) {
                    LOGGER.finer("propertyChange: processing activated nodes");
                    nodes = projectsTab.getActivatedNodes();
                } else if (lastProject == null) {
                    LOGGER.finer("propertyChange: processing selected nodes");
                    ExplorerManager em = ((ExplorerManager.Provider) projectsTab).getExplorerManager();
                    nodes = em.getSelectedNodes();
                }
                // Find and use the first project that owns a node
                if (nodes != null) {
                    for (Node node : nodes) {
                        Project project = findProjectThatOwnsNode(node);
                        if (project != null) {
                            synchronized (lock) {
                                // Remember this project for when the Project Tab goes out of focus
                                lastProject = project;
                                // Add this project to the proxy if it's not in the global lookup
                                if (!resultProjects.allInstances().contains(lastProject)) {
                                    LOGGER.log(FINER, "propertyChange: Found project [{0}] that owns current node.",
                                            ProjectUtils.getInformation(lastProject).getDisplayName());
                                    updateProjectLookup(lastProject);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * This class listens for changes in the Project results, and ensures a
     * Project remains in the Utilities.actionsGlobalContext() if a project is
     * open.
     */
    private class LookupListenerImpl implements LookupListener {

        @Override
        public void resultChanged(LookupEvent event) {
            synchronized (lock) {
                // First, handle projects in the principle lookup
                if (resultProjects.allInstances().size() > 0) {
                    // Clear the proxy, and remember this project.
                    // Note: not handling multiple selection of projects.
                    clearProjectLookup();
                    lastProject = resultProjects.allInstances().iterator().next();
                    LOGGER.log(FINER, "resultChanged: Found project [{0}] in the normal lookup.",
                            ProjectUtils.getInformation(lastProject).getDisplayName());
                } else if (OpenProjects.getDefault().getOpenProjects().length == 0) {
                    clearProjectLookup();
                    lastProject = null;
                } else {
                    if (lastProject == null) {
                        // Find the project that owns the current Node
                        Node currrentNode = globalContextLookup.lookup(Node.class);
                        Project project = findProjectThatOwnsNode(currrentNode);
                        if (project != null) {
                            lastProject = project;
                            LOGGER.log(FINER, "resultChanged: Found project [{0}] that owns current node.",
                                    ProjectUtils.getInformation(lastProject).getDisplayName());
                        }
                    }
                    // Add the last used project to our internal lookup
                    if (lastProject != null) {
                        updateProjectLookup(lastProject);
                    }
                }
            }
        }
    }

    /**
     * Unconditionally clears the project lookup.
     */
    private void clearProjectLookup() {
        if (projectLookup != null) {
            Collection<? extends Project> projects = projectLookup.lookupAll(Project.class);
            for (Project project : projects) {
                content.remove(project);
            }
        }
    }

    /**
     * Replaces the project lookup content.
     *
     * @param project to place in the project lookup.
     */
    private void updateProjectLookup(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null.");
        }
        // Add the project if an instance of it is not already in the lookup
        Template<Project> template = new Template<>(Project.class, null, project);
        if (projectLookup != null && projectLookup.lookupItem(template) == null) {
            clearProjectLookup();
            content.add(project);
            LOGGER.log(FINE, "updateProjectLookup: added [{0}] to the proxy lookup.",
                    ProjectUtils.getInformation(lastProject).getDisplayName());
        }
    }
    
    /**
     * Recursively searches the node hierarchy for the project that owns a node.
     *
     * @param node a node to test for a Project in its or its ancestor's lookup.
     * @return the Project that owns the node, or null if not found
     */
    private static Project findProjectThatOwnsNode(Node node) {
        if (node != null) {
            Project project = node.getLookup().lookup(Project.class);
            if (project == null) {
                DataObject dataObject = node.getLookup().lookup(DataObject.class);
                if (dataObject != null) {
                    project = FileOwnerQuery.getOwner(dataObject.getPrimaryFile());
                }
            }
            return (project == null) ? findProjectThatOwnsNode(node.getParentNode()) : project;
        } else {
            return null;
        }
    }
}
