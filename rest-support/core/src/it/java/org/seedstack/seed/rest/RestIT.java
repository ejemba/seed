/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.rest;

import org.junit.Ignore;
import org.seedstack.seed.it.AbstractSeedWebIT;
import org.seedstack.seed.rest.fixtures.Activity1;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.inject.Inject;
import java.net.URL;

import static com.jayway.restassured.RestAssured.expect;

public class RestIT extends AbstractSeedWebIT {
    @Inject
    Activity1 activity1;

    @ArquillianResource
    URL baseURL;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).setWebXML("WEB-INF/web.xml");
    }

    @Test
    @Ignore("not working with Arquillian 1.1.5")
    public void activities_are_injected() {
        Assertions.assertThat(activity1).isNotNull();
    }

    @RunAsClient
    @Test
    public void rest_resources_are_working() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("summary", "The world's highest resolution notebook");
        obj.put("categoryId", 1);
        obj.put("designation", "macbook pro");
        obj.put("picture", "mypictureurl");
        obj.put("price", 200.0);

        //assert response code
        String response = expect().statusCode(201).given().
                header("Accept", "application/json").header("Content-Type", "application/json").
                body(obj.toString()).post(baseURL.toString() + "rest/products/").asString();

        // assert body
        JSONAssert.assertEquals(obj, new JSONObject(response), false);
    }
}