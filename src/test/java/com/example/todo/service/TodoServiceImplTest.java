package com.example.todo.service;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;
import io.quarkus.panache.common.Sort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("TodoService テスト")
class TodoServiceImplTest {

    private TodoRepository repository;
    private NotificationService notificationService;
    private TodoServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(TodoRepository.class);
        notificationService = mock(NotificationService.class); // ★ 追加
        service = new TodoServiceImpl(repository, notificationService); // ★ 修正
    }

    @Test
    @DisplayName("Todo作成時に初期値を設定して永続化する")
    void Todo作成時に初期値を設定して永続化する() {

        Todo created = service.create("牛乳を買う");

        assertEquals("牛乳を買う", created.getTitle());
        assertFalse(created.isCompleted());
        assertNotNull(created.getCreatedAt());

        verify(repository).persist(created);
    }

    @Test
    @DisplayName("未完了Todoをトグルすると完了になる")
    void 未完了Todoをトグルすると完了になる() {

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setCompleted(false);
        todo.setCreatedAt(Instant.now());

        when(repository.findById(1L))
                .thenReturn(todo);

        Optional<Todo> result = service.toggle(1L);

        assertTrue(result.isPresent());
        assertTrue(result.get().isCompleted());
    }

    @Test
    @DisplayName("完了済みTodoをトグルすると未完了になる")
    void 完了済みTodoをトグルすると未完了になる() {

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setCompleted(true);

        when(repository.findById(1L))
                .thenReturn(todo);

        Optional<Todo> result = service.toggle(1L);

        assertTrue(result.isPresent());
        assertFalse(result.get().isCompleted());
    }

    @Test
    @DisplayName("存在しないTodoをトグルすると空を返す")
    void 存在しないTodoをトグルすると空を返す() {

        when(repository.findById(999L))
                .thenReturn(null);

        Optional<Todo> result = service.toggle(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("ID指定取得をRepositoryへ委譲する")
    void ID指定取得をRepositoryへ委譲する() {

        Todo todo = new Todo();
        todo.setId(1L);

        when(repository.findById(1L))
                .thenReturn(todo);

        Optional<Todo> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("削除と全件取得をRepositoryへ委譲する")
    void 削除と全件取得をRepositoryへ委譲する() {

        when(repository.deleteById(1L))
                .thenReturn(true);

        Todo todo = new Todo();
        todo.setId(1L);

        when(repository.listAll(any(Sort.class)))
                .thenReturn(List.of(todo));

        assertTrue(service.delete(1L));

        List<Todo> result = service.findAll();

        assertEquals(1, result.size());

        verify(repository).deleteById(1L);
        verify(repository).listAll(any(Sort.class));
    }
}