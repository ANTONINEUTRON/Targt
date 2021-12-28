package com.neutron.targt.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.neutron.targt.models.Task;
import com.neutron.targt.room.TaskDao;
import com.neutron.targt.room.TaskRoomDatabase;

import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {
    private TaskDao taskDao;

    public TaskRepositoryImpl(Application application) {
        TaskRoomDatabase taskDb = TaskRoomDatabase.getDatabase(application);
        taskDao = taskDb.taskDao();
    }

    @Override
    public LiveData<List<Task>> getAllTasks() {
        return taskDao.fetchTask();
    }

    @Override
    public LiveData<Task> getTask(int id) {
        return taskDao.getTask(id);
    }

    @Override
    public void addTask(Task task) {
        TaskRoomDatabase.DB_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.addTask(task);
            }
        });
    }

    @Override
    public void deleteTask(Task task) {
        TaskRoomDatabase.DB_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }

    @Override
    public void editTask(Task task) {
        TaskRoomDatabase.DB_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.addTask(task);
            }
        });
    }

}
