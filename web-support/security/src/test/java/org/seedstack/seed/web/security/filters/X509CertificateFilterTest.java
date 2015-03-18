/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.web.security.filters;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.seed.security.api.X509CertificateToken;
import org.seedstack.seed.security.internal.realms.AuthenticationTokenWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class X509CertificateFilterTest {

    private X509CertificateFilter underTest;

    @Before
    public void before() {
        underTest = new X509CertificateFilter();
    }

    @Test
    public void createToken_should_create_token() throws Exception {
        X509Certificate certificate = mock(X509Certificate.class);
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        when(request.getAttribute("javax.servlet.request.X509Certificate")).thenReturn(new X509Certificate[] { certificate });
        AuthenticationToken token = underTest.createToken(request, response);

        assertThat(token).isInstanceOf(AuthenticationTokenWrapper.class);
        AuthenticationTokenWrapper w = (AuthenticationTokenWrapper) token;

        assertThat(w.getSeedToken()).isInstanceOf(X509CertificateToken.class);
        X509CertificateToken x = (X509CertificateToken) w.getSeedToken();
        assertThat(x.getAuthenticatingCertificates()).containsExactly(certificate);
    }

    @Test
    public void onLoginFailure_should_return_false() throws IOException {
        ServletRequest request = mock(ServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationToken token = mock(AuthenticationToken.class);
        AuthenticationException ex = mock(AuthenticationException.class);
        boolean result = underTest.onLoginFailure(token, ex, request, response);

        assertThat(result).isFalse();
        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }
}
