package com.example.datatabledemo

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class AlarmWorker(context: Context, workerParams: WorkerParameters):Worker(context, workerParams) {
    override fun doWork(): Result {

        Log.d("MyWorker", "Hello world")
        return Result.success()
    }
}