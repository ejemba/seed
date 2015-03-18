/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.core.internal.jndi;

import org.seedstack.seed.core.api.SeedException;
import io.nuun.kernel.api.Plugin;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.core.AbstractPlugin;
import org.apache.commons.configuration.Configuration;
import org.seedstack.seed.core.internal.CorePlugin;
import org.seedstack.seed.core.internal.application.ApplicationPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Plugin that retrieve configured JNDI contexts.
 *
 * @author adrien.lauer@mpsa.com
 */
public class JndiPlugin extends AbstractPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(JndiPlugin.class);

    private final Map<String, Context> additionalJndiContexts = new HashMap<String, Context>();
    private Context defaultJndiContext;

    @Override
    public String name() {
        return "seed-core-jndi-plugin";
    }

    @Override
    public InitState init(InitContext initContext) {
        ApplicationPlugin applicationPlugin = (ApplicationPlugin) initContext.pluginsRequired().iterator().next();
        Configuration configuration = applicationPlugin.getApplication().getConfiguration().subset(CorePlugin.CORE_PLUGIN_PREFIX);

        // Default JNDI context
        try {
            this.defaultJndiContext = new InitialContext();
            LOGGER.info("Default JNDI context has been configured");
        } catch (NamingException e) {
            throw SeedException.wrap(e, JndiErrorCode.UNABLE_TO_CONFIGURE_DEFAULT_JNDI_CONTEXT);
        }

        // Additional JNDI contexts
        String[] jndiContextNames = configuration.getStringArray("additional-jndi-contexts");
        if (jndiContextNames != null) {
            for (String jndiContextName : jndiContextNames) {
                String propertiesResource = configuration.getString("additional-jndi-context." + jndiContextName);
                Properties properties = new Properties();
                InputStream propertiesResourceStream = this.getClass().getResourceAsStream(propertiesResource);

                if (propertiesResourceStream != null) {
                    try {
                        properties.load(propertiesResourceStream);
                        this.additionalJndiContexts.put(jndiContextName, new InitialContext(properties));
                        LOGGER.info("JNDI context " + jndiContextName + " has been configured from " + propertiesResource);
                    } catch (Exception e) {
                        throw SeedException.wrap(e, JndiErrorCode.UNABLE_TO_CONFIGURE_ADDITIONAL_JNDI_CONTEXT).put("context", jndiContextName);
                    }

                    try {
                        propertiesResourceStream.close();
                    } catch (IOException e) {
                        LOGGER.warn("Unable to close JNDI properties resource " + propertiesResource, e);
                    }
                } else {
                    throw SeedException.createNew(JndiErrorCode.MISSING_JNDI_PROPERTIES).put("context", jndiContextName).put("property", "org.seedstack.seed.core.additional-jndi-context." + jndiContextName + " property");
                }
            }
        }
        return InitState.INITIALIZED;
    }

    @Override
    public Collection<Class<? extends Plugin>> requiredPlugins() {
        Collection<Class<? extends Plugin>> plugins = new ArrayList<Class<? extends Plugin>>();
        plugins.add(ApplicationPlugin.class);
        return plugins;
    }

    @Override
    public Object nativeUnitModule() {
        return new JndiModule(this.defaultJndiContext, this.additionalJndiContexts);
    }

    /**
     * Retrieve all configured JNDI contexts.
     *
     * @return the map of all configured JNDI contexts.
     */
    public Map<String, Context> getJndiContexts() {
        Map<String, Context> jndiContexts = new HashMap<String, Context>();
        jndiContexts.putAll(additionalJndiContexts);
        jndiContexts.put("default", defaultJndiContext);
        return jndiContexts;
    }
}
