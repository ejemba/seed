/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.security.fixtures;

import org.seedstack.seed.security.api.annotations.RequiresPermissions;
import org.seedstack.seed.security.api.annotations.RequiresRoles;

public class AnnotatedClass4Security {

	final String place = "coruscant";
	
	@RequiresRoles("jedi")
	public boolean callTheForce() {
		return true;
	}
	
	@RequiresPermissions("academy:teach")
	public boolean teach(){
		return true;
	}
}