package com.neutron.targt.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neutron.targt.models.Task;
import com.neutron.targt.repository.TaskRepository;
import com.neutron.targt.repository.TaskRepositoryImpl;

public class ViewTaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;

    public ViewTaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepositoryImpl(application);
    }

    public LiveData<Task> getTaskDetails(int id){
        return taskRepository.getTask(id);
    }

    public void deleteTask(Task task) {
        taskRepository.deleteTask(task);
    }
}
