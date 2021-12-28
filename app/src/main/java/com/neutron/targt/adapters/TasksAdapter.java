package com.neutron.targt.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.targt.R;
import com.neutron.targt.ViewTaskActivity;
import com.neutron.targt.models.Task;
import com.neutron.targt.utils.AlarmHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<Task> taskList;
    private Context context;
    private AlarmHandler alarmHandler;

    public static final String TASK_ID = "TASK_ID";


    public TasksAdapter(List<Task> taskList){
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        alarmHandler = new AlarmHandler(context);

        View view = LayoutInflater.from(context).inflate(R.layout.task_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Task taskInstance = taskList.get(position);

        holder.description.setText(taskInstance.getDescription());

        String formattedDate = formatDate(taskInstance);
        holder.time.setText(formattedDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewTaskActivity.class);
                intent.putExtra(TASK_ID, taskInstance.getId());
                context.startActivity(intent);
            }
        });
        setAlarm(taskInstance);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView time, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            description = itemView.findViewById(R.id.description);
        }
    }

    private void setAlarm(Task taskInstance) {
        alarmHandler.setAlarm(taskInstance);
    }

    private String formatDate(Task taskInstance) {
        Date time = new Date(taskInstance.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM dd \nHH:mm");
        return simpleDateFormat.format(time);
    }

}
