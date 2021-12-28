package com.neutron.targt.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Chronometer;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.neutron.targt.AddTaskActivity;
import com.neutron.targt.R;
import com.neutron.targt.ViewTaskActivity;
import com.neutron.targt.adapters.TasksAdapter;
import com.neutron.targt.utils.AlarmHandler;

public class AlarmReceiver extends BroadcastReceiver {
    private final String CHANNEL_ID = "Target_Notification";
    private final int NOTIFICATION_ID = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String description = intent.getStringExtra(AlarmHandler.TARGET_TAG);
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(new VibrationEffect());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(context);
        }

        Intent i = new Intent(context, ViewTaskActivity.class);
        i.putExtra(TasksAdapter.TASK_ID, intent.getIntExtra(TasksAdapter.TASK_ID,0));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 11222, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(description)
                .setUsesChronometer(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        String name = "TARGT Alert";
        String description = "Shows the Targt to undertake";

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }
}
