package com.example.reminderapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        WakeLock.acquire(context!!)
        val title = intent?.getStringExtra("EXTRA_TITLE") ?: return
        val description = intent.getStringExtra("EXTRA_DESCRIPTION") ?: return
        val channelId = "alarm_id"
        Log.d("AlarmReceiver", "Alarm received")

        context.let { ctx ->
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.access_alarm)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            notificationManager.notify(1, builder.build())
            WakeLock.release()
            Log.d("AlarmReceiver", "Notification sent")
        }
    }
}