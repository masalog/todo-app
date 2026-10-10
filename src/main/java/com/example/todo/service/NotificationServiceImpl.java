package com.example.todo.service;

import com.example.todo.websocket.TodoWebSocket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@ApplicationScoped
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notifyCreated(Long id) {
        JsonObject json = Json.createObjectBuilder()
                .add("event", "todo.created")
                .add("id", id == null ? -1 : id)  // ★ create のテストで null になるため安全に処理
                .build();

        TodoWebSocket.broadcast(json.toString());
    }

    @Override
    public void notifyToggled(Long id) {
        JsonObject json = Json.createObjectBuilder()
                .add("event", "todo.toggled")
                .add("id", id)
                .build();

        TodoWebSocket.broadcast(json.toString());
    }

    @Override
    public void notifyDeleted(Long id) {
        JsonObject json = Json.createObjectBuilder()
                .add("event", "todo.deleted")
                .add("id", id)
                .build();

        TodoWebSocket.broadcast(json.toString());
    }
}
