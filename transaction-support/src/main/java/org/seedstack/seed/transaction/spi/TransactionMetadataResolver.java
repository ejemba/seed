/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.transaction.spi;

import org.aopalliance.intercept.MethodInvocation;

/**
 * This interface must be implemented by SEED transaction metadata resolvers. These are used by a transaction manager
 * to determine the current transaction behavior.
 *
 * @author adrien.lauer@mpsa.com
 */
public interface TransactionMetadataResolver {

    /**
     * This method is called for a resolver to resolve transaction metadata. It is given the {@link MethodInvocation}
     * object and transaction metadata defaults. If this particular resolver is not concerned by this transaction, it
     * must return null. Otherwise it can specify any resolved metadata as the returned {@link TransactionMetadata} object.
     *
     * Resolving order is unspecified so resolvers should only return metadata that are specific to them.
     *
     * @param methodInvocation the current transaction method interception object.
     * @param defaults the transaction metadata defaults.
     * @return the resolved transaction metadata attributes as a {@link TransactionMetadata} object, null otherwise.
     */
    TransactionMetadata resolve(MethodInvocation methodInvocation, TransactionMetadata defaults);
}
