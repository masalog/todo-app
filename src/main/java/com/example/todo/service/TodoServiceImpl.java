package com.example.todo.service;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;
    private final NotificationService notificationService; // ★ WebSocket を知らない

    @Override
    public List<Todo> findAll() {
        return repository.listAll(
                Sort.by("createdAt").descending()
        );
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    @Transactional
    public Todo create(String title) {

        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        todo.setCreatedAt(Instant.now());

        repository.persist(todo);

        // ★ WebSocket を知らずに「通知」だけ呼ぶ
        notificationService.notifyCreated(todo.getId());

        return todo;
    }

    @Override
    @Transactional
    public Optional<Todo> toggle(Long id) {

        Todo todo = repository.findById(id);

        if (todo == null) {
            return Optional.empty();
        }

        todo.setCompleted(!todo.isCompleted());

        notificationService.notifyToggled(id);

        return Optional.of(todo);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {

        boolean deleted = repository.deleteById(id);

        if (deleted) {
            notificationService.notifyDeleted(id);
        }

        return deleted;
    }
}
