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

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

import javax.naming.Context;
import java.util.Map;

/**
 * Guice module that binds the configured JNDI context.
 *
 * @author adrien.lauer@mpsa.com
 */
class JndiModule extends AbstractModule {
    private Map<String, Context> jndiContextsToBeBound;
    private Context defaultContext;

    JndiModule(Context defaultContext, Map<String, Context> jndiContextsToBeBound) {
        this.jndiContextsToBeBound = jndiContextsToBeBound;
        this.defaultContext = defaultContext;
    }

    @Override
    protected void configure() {
        requestStaticInjection(JndiContext.class);

        // Bind default context
        Key defaultContextKey = Key.get(Context.class, Names.named("defaultContext"));
        bind(defaultContextKey).toInstance(defaultContext);
        bind(Context.class).to(defaultContextKey);

        // Bind additional contexts
        for (Map.Entry<String, Context> jndiContextToBeBound : jndiContextsToBeBound.entrySet()) {
            Key<Context> key = Key.get(Context.class, Names.named(jndiContextToBeBound.getKey()));
            bind(key).toInstance(jndiContextToBeBound.getValue());
        }

        bindListener(Matchers.any(), new ResourceTypeListener(this.defaultContext, this.jndiContextsToBeBound));
    }
}
