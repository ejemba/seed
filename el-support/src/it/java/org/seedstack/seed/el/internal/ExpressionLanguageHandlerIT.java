/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.el.internal;

import org.seedstack.seed.core.api.SeedException;
import org.seedstack.seed.el.internal.fixtureFailing.Foo;
import org.seedstack.seed.el.internal.fixtures.SomeRepresentation;
import org.seedstack.seed.el.internal.fixtureFailing.Foo;
import org.seedstack.seed.el.internal.fixtures.SomeRepresentation;
import org.seedstack.seed.it.SeedITRunner;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 01/07/2014
 */
@RunWith(SeedITRunner.class)
public class ExpressionLanguageHandlerIT {

    public static String message = "";
    public static Long count = 0L;

    @Inject
    private SomeRepresentation representation;

    @Inject
    private Foo representation2;

    @Test
    public void el_was_coerced_in_long() {
        representation.setMessage("hello");
        Assertions.assertThat(count).isEqualTo(4);
    }

    @Test(expected = SeedException.class)
    public void error_when_annotation_value_not_available() {
        representation2.pok();
    }

    @Test(expected = SeedException.class)
    public void failing_el_evaluation_throw_seed_exception() {
        representation.failingMethod();
    }

}
