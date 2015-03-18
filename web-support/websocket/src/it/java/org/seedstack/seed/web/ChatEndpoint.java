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

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * This WebSocket endpoint get messages and retrieve them.
 */
@ServerEndpoint(value = "/chat")
public class ChatEndpoint {
    @Inject
    EchoService echoService;

    @OnMessage
    public void message(String message, Session client) throws IOException, EncodeException {
        for (Session peer : client.getOpenSessions())
            peer.getBasicRemote().sendText(echoService.echo(message));
    }
}