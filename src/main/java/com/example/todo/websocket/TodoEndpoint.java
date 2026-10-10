package com.example.todo.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws/todos")
public class TodoEndpoint {

    @OnOpen
    public void onOpen(Session session) {

        SessionManager.sessions.add(session);

        System.out.println(
                "OPEN sessions="
                        + SessionManager.sessions.size()
        );
    }

    @OnClose
    public void onClose(Session session) {

        SessionManager.sessions.remove(session);

        System.out.println(
                "CLOSE sessions="
                        + SessionManager.sessions.size()
        );
    }
}