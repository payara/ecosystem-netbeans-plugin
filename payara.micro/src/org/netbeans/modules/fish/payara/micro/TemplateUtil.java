/**
 * Copyright 2013-2018 the original author or authors from the Jeddict project (https://jeddict.github.io/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
// Portions Copyright [2018] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.fish.payara.micro;

import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;

/**
 *
 * @author Gaurav Gupta <gaurav.gupta@payara.fish>
 */
public class TemplateUtil {

    private static volatile Object currentLoader;
    
    private static Lookup.Result<ClassLoader> loaderQuery = null;
    
    private static boolean noLoaderWarned = false;
    
    private static final Logger LOG = Logger.getLogger(TemplateUtil.class.getName());
    
    private static final String ENCODING_PROPERTY_NAME = "encoding"; //NOI18N
    
    private static ScriptEngineManager manager;
    
    private static ClassLoader getLoader() {
        Object is = currentLoader;
        if (is instanceof ClassLoader) {
            return (ClassLoader) is;
        }

        currentLoader = Thread.currentThread();

        if (loaderQuery == null) {
            loaderQuery = Lookup.getDefault().lookupResult(ClassLoader.class);
            loaderQuery.addLookupListener((LookupEvent ev) -> {
                LOG.fine("Loader cleared"); // NOI18N
                currentLoader = null;
            });
        }

        Iterator it = loaderQuery.allInstances().iterator();
        if (it.hasNext()) {
            ClassLoader toReturn = (ClassLoader) it.next();
            if (currentLoader == Thread.currentThread()) {
                currentLoader = toReturn;
            }
            LOG.log(Level.FINE, "Loader computed: {0}", currentLoader); // NOI18N
            return toReturn;
        } else {
            if (!noLoaderWarned) {
                noLoaderWarned = true;
                LOG.log(Level.WARNING, "No ClassLoader instance found in {0}", Lookup.getDefault() // NOI18N
                );
            }
            return null;
        }
    }

    public static URL getResourceURL(String resource) {
        if (resource.startsWith("/")) { // NOI18N
            resource = resource.substring(1);
        }
        return getLoader().getResource(resource);
    }

    public static InputStream loadResource(String resource) {
        InputStream inputStream = null;
        try {
            inputStream = getResourceURL(resource).openStream();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return inputStream;
    }

    public static String expandTemplate(Reader reader, Map<String, Object> values) {
        StringWriter writer = new StringWriter();
        ScriptEngine eng = getScriptEngine();
        Bindings bind = eng.getContext().getBindings(ScriptContext.ENGINE_SCOPE);
        if (values != null) {
            bind.putAll(values);
        }
        bind.put(ENCODING_PROPERTY_NAME, Charset.defaultCharset().name());
        eng.getContext().setWriter(writer);
        try {
            eng.eval(reader);
        } catch (ScriptException ex) {
            Exceptions.printStackTrace(ex);
        }

        return writer.toString();
    }
    
    
    /**
     * Used core method for getting {@code ScriptEngine} from {@code
     * org.netbeans.modules.templates.ScriptingCreateFromTemplateHandler}.
     */
    private static ScriptEngine getScriptEngine() {
        if (manager == null) {
            synchronized (TemplateUtil.class) {
                if (manager == null) {
                    ClassLoader loader = Lookup.getDefault().lookup(ClassLoader.class);
                    try {
                        loader.loadClass(PrefixResolver.class.getName());
                    } catch (ClassNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    manager = new ScriptEngineManager(loader != null ? loader : Thread.currentThread().getContextClassLoader());
                }
            }
        }
        return manager.getEngineByName("freemarker");
    }
}
