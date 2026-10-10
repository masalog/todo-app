package com.example.todo.websocket;

import jakarta.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SessionManager のテスト")
class SessionManagerTest {

    @BeforeEach
    void setUp() {
        // ★ final フィールドなので再代入不可 → 中身をクリアする
        SessionManager.sessions.clear();
    }

    @Test
    @DisplayName("初期状態では sessions は空である")
    void 初期状態では空である() {
        assertTrue(SessionManager.sessions.isEmpty());
    }

    @Test
    @DisplayName("Session を追加できる")
    void Sessionを追加できる() {

        Session session = (Session) mock(Session.class); // ★ キャストで警告ゼロ

        SessionManager.sessions.add(session);

        assertEquals(1, SessionManager.sessions.size());
        assertTrue(SessionManager.sessions.contains(session));
    }

    @Test
    @DisplayName("Session を削除できる")
    void Sessionを削除できる() {

        Session session = (Session) mock(Session.class); // ★ キャスト

        SessionManager.sessions.add(session);
        SessionManager.sessions.remove(session);

        assertTrue(SessionManager.sessions.isEmpty());
    }

    @Test
    @DisplayName("複数 Session を追加できる")
    void 複数Sessionを追加できる() {

        Session s1 = (Session) mock(Session.class); // ★ キャスト
        Session s2 = (Session) mock(Session.class); // ★ キャスト

        SessionManager.sessions.add(s1);
        SessionManager.sessions.add(s2);

        assertEquals(2, SessionManager.sessions.size());
        assertTrue(SessionManager.sessions.contains(s1));
        assertTrue(SessionManager.sessions.contains(s2));
    }
}
