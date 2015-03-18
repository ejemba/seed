/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.core.internal;

import org.reflections.vfs.Vfs;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * VFS file implementation for JNDI directory scanning.
 *
 * @author adrien.lauer@mpsa.com
 */
class JndiInputFile implements Vfs.File {
    private final String path;
    private final Object jndiResource;

    JndiInputFile(Object jndiResource, String path) {
        this.jndiResource = jndiResource;
        this.path = path;
    }

    @Override
    public String getName() {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    @Override
    public String getRelativePath() {
        return path;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        try {
            Method method = jndiResource.getClass().getMethod("streamContent");
            return (InputStream) method.invoke(jndiResource);
        } catch (Exception e) {
            throw new IOException("Unable to open JNDI file " + getName(), e);
        }
    }
}
