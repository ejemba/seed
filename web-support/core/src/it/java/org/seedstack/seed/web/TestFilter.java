/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.web;

import org.seedstack.seed.web.api.WebFilter;
import org.seedstack.seed.web.api.WebInitParam;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@WebFilter(value = {"/testFilter1", "/testFilter2"}, initParams = {@WebInitParam(name = "param1", value = TestServlet.PARAM1_VALUE)})
public class TestFilter implements Filter {
    public static final String CONTENT = "Hello World!";
    public static final String PARAM1_VALUE = "value1";

    private String param1Value;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        param1Value = filterConfig.getInitParameter("param1");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String text = CONTENT + " " + param1Value;
        servletResponse.setContentType("text/plain");
        servletResponse.setContentLength(text.length());

        servletResponse.getWriter().write(text);
    }

    @Override
    public void destroy() {
    }
}