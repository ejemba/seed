/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.shell.commands;

import org.seedstack.seed.core.spi.command.CommandDefinition;
import org.seedstack.seed.core.spi.command.Command;

import java.util.Collection;
import java.util.Map;

/**
 * This command counts the number of elements on input object.
 *
 * @author adrien.lauer@mpsa.com
 */
@CommandDefinition(scope = "", name = "count", description = "Counts the number of elements on input object (string, collection or map)")
public class CountCommand implements Command {
    @Override
    public Object execute(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return ((String) object).length();
        } else if (object instanceof Collection) {
            return ((Collection) object).size();
        } else if (object instanceof Map) {
            return ((Map) object).size();
        } else {
            throw new IllegalArgumentException("cannot count on " + object.getClass().getCanonicalName());
        }
    }
}
