package com.example.reminderapp
import android.content.Context
import android.os.PowerManager
import android.view.WindowManager

object WakeLock {
    private var wakeLock: PowerManager.WakeLock? = null

    fun acquire(ctx: Context) {
        if (wakeLock != null) wakeLock!!.release()
        val pm = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE, "myApp:wakeLock"
        )
        wakeLock?.acquire(15 * 60 * 1000L /*15 minutes*/)
    }

    @JvmStatic
    fun release() {
        if (wakeLock != null) wakeLock!!.release()
        wakeLock = null
    }

}