package com.example.todo.service;

public interface NotificationService {
    void notifyCreated(Long id);
    void notifyToggled(Long id);
    void notifyDeleted(Long id);
}