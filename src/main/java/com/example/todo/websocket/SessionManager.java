package com.example.todo.websocket;

import jakarta.websocket.Session;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class SessionManager {

    public static final Set<Session> sessions =
            new CopyOnWriteArraySet<>();
}