/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.seed.persistence.inmemory.internal;

import org.seedstack.seed.transaction.spi.TransactionalLink;

/**
 * @author redouane.loulou@ext.mpsa.com
 */
class InMemoryTransactionLink implements TransactionalLink<String> {
    private final ThreadLocal<String> inMemoryStoreNameContainer;

    InMemoryTransactionLink() {
    	inMemoryStoreNameContainer =  new ThreadLocal<String>();
    }

	@Override
	public String get() {
		return inMemoryStoreNameContainer.get();
	}

    void set(String store) {
    	inMemoryStoreNameContainer.set(store);
    }

    String getCurrentTransaction() {
        return inMemoryStoreNameContainer.get();
    }

}
