package com.neutron.targt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neutron.targt.models.Task;
import com.neutron.targt.view_model.ViewTaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.neutron.targt.adapters.TasksAdapter.TASK_ID;

public class ViewTaskActivity extends AppCompatActivity {
    private ImageButton editTaskBtn, deleteTaskBtn;
    private TextView taskDescription;
    private ViewTaskViewModel viewTaskViewModel;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        //Instantiate Viewmodel
        viewTaskViewModel = new ViewModelProvider(this).get(ViewTaskViewModel.class);

        editTaskBtn = findViewById(R.id.edit_btn);
        deleteTaskBtn = findViewById(R.id.delete_btn);
        taskDescription = findViewById(R.id.task_details);

        //Receive the task id
        int taskId = getIntent().getIntExtra(TASK_ID,0);

        //Fetch details from database
        viewTaskViewModel.getTaskDetails(taskId).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                if(task != null) {
                    ViewTaskActivity.this.task = task;
                    //Display details
                    Date date = new Date(task.getTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM dd\n HH:mm");
                    String timeText = simpleDateFormat.format(date);

                    String details = task.getDescription() + " \n\n " + timeText;

                    taskDescription.setText(details);
                }else{
                    ViewTaskActivity.this.finish();
                }
            }
        });
        //Set listeners to edit & delete button
        editTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTaskActivity.this, AddTaskActivity.class);
                intent.putExtra(TASK_ID, taskId);
                startActivity(intent);
            }
        });

        deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ask for user confirmation
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewTaskActivity.this);
                alert.setMessage("You are about to delete this task!")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewTaskViewModel.deleteTask(task);
                                Toast.makeText(ViewTaskActivity.this, "Done!", Toast.LENGTH_LONG).show();
                                ViewTaskActivity.this.finish();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
            }
        });
    }
}