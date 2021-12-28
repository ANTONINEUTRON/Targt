package com.neutron.targt.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.neutron.targt.models.Task;
import com.neutron.targt.repository.TaskRepository;
import com.neutron.targt.repository.TaskRepositoryImpl;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private TaskRepository taskRepo;
    public MainViewModel(@NonNull Application application) {
        super(application);
        taskRepo = new TaskRepositoryImpl(application);
    }

    public LiveData<List<Task>> getTasks(){
        return taskRepo.getAllTasks();
    }
}
