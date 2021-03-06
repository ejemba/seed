/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.metrics.api;

import org.seedstack.seed.core.api.ErrorCode;

/**
 * Enumerates error codes of metrics support.
 *
 * @author adrien.lauer@mpsa.com
 */
public enum MetricsErrorCode implements ErrorCode {
    ERROR_ACCESSING_METRIC_FIELD,
    INVALID_METRIC_TYPE,
    ERROR_EVALUATING_METRIC
}
