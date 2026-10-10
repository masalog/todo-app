package com.example.todo.websocket;

import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@DisplayName("TodoWebSocket のテスト")
class TodoWebSocketTest {

    @BeforeEach
    void setUp() {
        SessionManager.sessions.clear();
    }

    @Test
    @DisplayName("broadcast が全セッションに sendText を送信する")
    void broadcastが全セッションに送信する() {

        Session session = (Session) mock(Session.class);
        RemoteEndpoint.Async async = (RemoteEndpoint.Async) mock(RemoteEndpoint.Async.class);

        when(session.getAsyncRemote()).thenReturn(async);

        SessionManager.sessions.add(session);

        TodoWebSocket.broadcast("hello");

        verify(async).sendText("hello");
    }

    @Test
    @DisplayName("複数セッションに送信される")
    void 複数セッションに送信される() {

        Session s1 = (Session) mock(Session.class);
        Session s2 = (Session) mock(Session.class);

        RemoteEndpoint.Async a1 = (RemoteEndpoint.Async) mock(RemoteEndpoint.Async.class);
        RemoteEndpoint.Async a2 = (RemoteEndpoint.Async) mock(RemoteEndpoint.Async.class);

        when(s1.getAsyncRemote()).thenReturn(a1);
        when(s2.getAsyncRemote()).thenReturn(a2);

        SessionManager.sessions.add(s1);
        SessionManager.sessions.add(s2);

        TodoWebSocket.broadcast("test");

        verify(a1).sendText("test");
        verify(a2).sendText("test");
    }
}
