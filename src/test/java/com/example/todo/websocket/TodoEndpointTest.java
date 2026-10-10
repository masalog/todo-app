package com.example.todo.websocket;

import jakarta.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TodoEndpoint のテスト")
class TodoEndpointTest {

    private TodoEndpoint endpoint;

    @BeforeEach
    void setUp() {
        // ★ final フィールドなので再代入不可 → 中身をクリアする
        SessionManager.sessions.clear();
        endpoint = new TodoEndpoint();
    }

    @Test
    @DisplayName("onOpen で Session が追加される")
    void onOpenでSessionが追加される() {

        Session session = (Session) mock(Session.class); // ★ キャストで警告ゼロ

        endpoint.onOpen(session);

        assertEquals(1, SessionManager.sessions.size());
        assertTrue(SessionManager.sessions.contains(session));
    }

    @Test
    @DisplayName("onClose で Session が削除される")
    void onCloseでSessionが削除される() {

        Session session = (Session) mock(Session.class); // ★ キャスト

        // まず追加
        SessionManager.sessions.add(session);

        // onClose を呼ぶ
        endpoint.onClose(session);

        assertTrue(SessionManager.sessions.isEmpty());
    }

    @Test
    @DisplayName("複数 Session が正しく追加・削除される")
    void 複数Sessionが正しく追加削除される() {

        Session s1 = (Session) mock(Session.class); // ★ キャスト
        Session s2 = (Session) mock(Session.class); // ★ キャスト

        endpoint.onOpen(s1);
        endpoint.onOpen(s2);

        assertEquals(2, SessionManager.sessions.size());

        endpoint.onClose(s1);

        assertEquals(1, SessionManager.sessions.size());
        assertFalse(SessionManager.sessions.contains(s1));
        assertTrue(SessionManager.sessions.contains(s2));
    }
}