package com.example.todo.service;

import com.example.todo.websocket.TodoWebSocket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@DisplayName("NotificationServiceImpl のテスト")
class NotificationServiceImplTest {

    private final NotificationServiceImpl service = new NotificationServiceImpl();

    @Test
    @DisplayName("created 通知が正しい JSON を送信する")
    void created通知が正しいJSONを送信する() {

        try (var mocked = mockStatic(TodoWebSocket.class)) {

            service.notifyCreated(1L);

            mocked.verify(() -> TodoWebSocket.broadcast(
                    "{\"event\":\"todo.created\",\"id\":1}"
            ));
        }
    }

    @Test
    @DisplayName("toggled 通知が正しい JSON を送信する")
    void toggled通知が正しいJSONを送信する() {

        try (var mocked = mockStatic(TodoWebSocket.class)) {

            service.notifyToggled(5L);

            mocked.verify(() -> TodoWebSocket.broadcast(
                    "{\"event\":\"todo.toggled\",\"id\":5}"
            ));
        }
    }

    @Test
    @DisplayName("deleted 通知が正しい JSON を送信する")
    void deleted通知が正しいJSONを送信する() {

        try (var mocked = mockStatic(TodoWebSocket.class)) {

            service.notifyDeleted(99L);

            mocked.verify(() -> TodoWebSocket.broadcast(
                    "{\"event\":\"todo.deleted\",\"id\":99}"
            ));
        }
    }
}
