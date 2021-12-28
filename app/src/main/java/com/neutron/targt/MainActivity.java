package com.neutron.targt;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neutron.targt.adapters.TasksAdapter;
import com.neutron.targt.models.Task;
import com.neutron.targt.view_model.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private TextView noTextFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noTextFound = findViewById(R.id.no_task_found);

        //initialise viewmodel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {

                if(taskList.size() > 0) {
                    //Hide No Text Found Textview
                    noTextFound.setVisibility(View.GONE);
                    setupRecyclerview(taskList);
                }else{
                    noTextFound.setVisibility(View.VISIBLE);
                    setupRecyclerview(taskList);
                }
            }
        });

        setupFloatingActionButton();
    }

    private void setupRecyclerview(List<Task> sampleTasks) {
        //Show recyclerview
        RecyclerView recyclerView = findViewById(R.id.tasks);
        recyclerView.setAdapter(new TasksAdapter(sampleTasks));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }
}