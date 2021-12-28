package com.neutron.targt.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.neutron.targt.MainActivity;
import com.neutron.targt.ViewTaskActivity;
import com.neutron.targt.adapters.TasksAdapter;
import com.neutron.targt.models.Task;
import com.neutron.targt.receivers.AlarmReceiver;

public class AlarmHandler {
    private Context context;
    private Task task;


    private int requestCode = 123;
    public static final String TARGET_TAG = "TARGET_DESC";

    public AlarmHandler(Context context) {
        this.context = context;
    }

    public void setAlarm(Task task){
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(TasksAdapter.TASK_ID, task.getId())
                .putExtra(TARGET_TAG, task.getDescription());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, task.getTime(), pendingIntent);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP,task.getTime(),pendingIntent);
        }
    }
}
