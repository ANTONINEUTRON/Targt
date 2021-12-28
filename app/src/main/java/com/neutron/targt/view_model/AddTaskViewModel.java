package com.neutron.targt.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neutron.targt.models.Task;
import com.neutron.targt.repository.TaskRepository;
import com.neutron.targt.repository.TaskRepositoryImpl;

public class AddTaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepositoryImpl(application);
    }

    public LiveData<Task> getTask(int editTaskId) {
        return  taskRepository.getTask(editTaskId);
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
    }
}
