/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.validation.internal.test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class BeanField {
	    @Range(min = 0, max = 200)
	    private int age;

	    @NotNull
	    @Range(min = 10L, max = 999999999999999999L)
	    private Long longNumber;

	    @NotNull(message = "Nom obligatoire")
	    @Size(min = 1, max = 10)
	    private String name;

	    @NotNull(groups = { Groupe1Checks.class })
	    private String firstName;
	    
	    public BeanField()
	    {
	    }

	    public int getAge()
	    {
	        return age;
	    }

	    public void setAge(int age)
	    {
	        this.age = age;
	    }

	    public String getName()
	    {
	        return name;
	    }

	    public void setName(String name)
	    {
	        this.name = name;
	    }

	    public String getFirstName()
	    {
	        return firstName;
	    }

	    public void setFirstName(String firstName)
	    {
	        this.firstName = firstName;
	    }

	    public Long getLongNumber()
	    {
	        return longNumber;
	    }

	    public void setLongNumber(Long longNumber)
	    {
	        this.longNumber = longNumber;
	    }

}
