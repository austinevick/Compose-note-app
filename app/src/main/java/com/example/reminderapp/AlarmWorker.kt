package com.example.reminderapp

import android.content.Context
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class AlarmWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {

       // Toast.makeText(applicationContext, "Worker has started", Toast.LENGTH_LONG).show()

        try {
            TimeUnit.MINUTES.sleep(10)
            Toast.makeText(applicationContext, "Worker is ongoing", Toast.LENGTH_LONG).show()

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Toast.makeText(applicationContext, "Worker has ended", Toast.LENGTH_LONG).show()
        return Result.success()
    }
}

val workRequest: WorkRequest = OneTimeWorkRequestBuilder<AlarmWorker>().build()
val periodicWorkRequest: WorkRequest =
    PeriodicWorkRequestBuilder<AlarmWorker>(12, TimeUnit.MINUTES).build()