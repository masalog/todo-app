package com.example.todo.websocket;

import jakarta.websocket.Session;

public class TodoWebSocket {

    public static void broadcast(String message) {

        System.out.println(
                "Broadcast sessions="
                        + SessionManager.sessions.size()
        );

        for (Session session : SessionManager.sessions) {

            try {

                session.getAsyncRemote()
                        .sendText(message);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}