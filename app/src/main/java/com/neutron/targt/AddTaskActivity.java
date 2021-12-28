package com.neutron.targt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.neutron.targt.models.Task;
import com.neutron.targt.utils.AlarmHandler;
import com.neutron.targt.view_model.AddTaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.neutron.targt.adapters.TasksAdapter.TASK_ID;

public class AddTaskActivity extends AppCompatActivity {
    private EditText descriptionEdt;
    private Button addNewTaskBtn, setTimeBtn, setDateBtn;
    private int taskId = (int) System.currentTimeMillis();
    private String description;
    private Long time;

    //Calendar instance for holding user selected values
    private Calendar selectedValuesCal = Calendar.getInstance();

    private AddTaskViewModel addTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Initialise view model
        addTaskViewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);

        descriptionEdt = findViewById(R.id.description);
        addNewTaskBtn = findViewById(R.id.add_btn);
        setTimeBtn = findViewById(R.id.time_chooser);
        setDateBtn = findViewById(R.id.date_chooser);

        //Check for edit request
        int editTaskId = getIntent().getIntExtra(TASK_ID, 0);
        if(editTaskId != 0){
            taskId = editTaskId;
           addTaskViewModel.getTask(editTaskId).observe(this, new Observer<Task>() {
               @Override
               public void onChanged(Task task) {
                   description = task.getDescription();
                   descriptionEdt.setText(task.getDescription());

                   time = task.getTime();
                   setTimeBtn.setText(new SimpleDateFormat("HH:mm").format(new Date(time)));
                   setDateBtn.setText(new SimpleDateFormat("EEE, MMM dd").format(new Date(time)));
               }
           });
        }

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show Time selector dialog
                OnTimeSetListener timeSetListener = new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        //Set User Selected Values to the selectedValuesCal
                        AddTaskActivity.this.selectedValuesCal.set(Calendar.HOUR_OF_DAY, hour);
                        AddTaskActivity.this.selectedValuesCal.set(Calendar.MINUTE, minute);

                        setTimeBtn.setText(hour+":"+minute);
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                        timeSetListener,
                        selectedValuesCal.get(Calendar.HOUR_OF_DAY),
                        selectedValuesCal.get(Calendar.MINUTE),
                        true);

                timePickerDialog.show();
            }
        });

        setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnDateSetListener dateSetListener = new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        AddTaskActivity.this.selectedValuesCal.set(Calendar.DATE, day);
                        AddTaskActivity.this.selectedValuesCal.set(Calendar.MONTH, month);
                        AddTaskActivity.this.selectedValuesCal.set(Calendar.YEAR, year);

                        displaySelectedDateOnButton();
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        dateSetListener,
                        selectedValuesCal.get(Calendar.YEAR),
                        selectedValuesCal.get(Calendar.MONTH),
                        selectedValuesCal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });

        addNewTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set values
                description = descriptionEdt.getText().toString();
                time = AddTaskActivity.this.selectedValuesCal.getTimeInMillis();

                //Validate Values
                if(description == null || description.isEmpty()){
                    Toast.makeText(AddTaskActivity.this, "Task Description cannot be Empty!", Toast.LENGTH_LONG).show();
                }else if(time < System.currentTimeMillis()){
                    Toast.makeText(AddTaskActivity.this,"Set a valid time", Toast.LENGTH_LONG).show();
                }else {
                    Task task = new Task(taskId, description,time);

                    setAlarm(task);

                    addTaskViewModel.addTask(task);

                    AddTaskActivity.this.finish();
                }
            }
        });

    }

    private void setAlarm(Task task) {
        AlarmHandler alarmHandler = new AlarmHandler(getApplicationContext());
        alarmHandler.setAlarm(task);
    }

    private void displaySelectedDateOnButton() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM dd");
        Date date = new Date(selectedValuesCal.getTimeInMillis());
        setDateBtn.setText(simpleDateFormat.format(date));
    }
}