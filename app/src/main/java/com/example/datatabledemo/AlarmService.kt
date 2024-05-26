package com.example.datatabledemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class AlarmService:Service() {
    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
           // Action.START.name -> start()
            //Action.STOP.name -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }
}