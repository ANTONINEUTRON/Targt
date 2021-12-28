package com.neutron.targt.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.neutron.targt.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task ORDER BY time")
    LiveData<List<Task>> fetchTask();

    @Query("SELECT * FROM task WHERE id = :id")
    LiveData<Task> getTask(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(Task task);

    @Delete
    void deleteTask(Task task);
}
