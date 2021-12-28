package com.neutron.targt.repository;

import androidx.lifecycle.LiveData;

import com.neutron.targt.models.Task;

import java.util.List;

public interface TaskRepository {
    LiveData<List<Task>> getAllTasks();

    LiveData<Task> getTask(int id);

    void addTask(Task task);

    void deleteTask(Task task);

    void editTask(Task task);
}
