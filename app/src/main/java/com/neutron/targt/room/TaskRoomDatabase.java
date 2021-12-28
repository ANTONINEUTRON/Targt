package com.neutron.targt.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.neutron.targt.models.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskRoomDatabase extends RoomDatabase {
    private static volatile  TaskRoomDatabase INSTANCE;
    private static final int NUM_OF_THREADS = 2;

    public static final ExecutorService DB_EXECUTOR = Executors.newFixedThreadPool(NUM_OF_THREADS);

    public abstract TaskDao taskDao();

    public static TaskRoomDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskRoomDatabase.class, "task_database")
                    .build();
        }
        return INSTANCE;
    }
}
