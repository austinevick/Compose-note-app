package com.example.datatabledemo

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.datatabledemo.compose.HomeActivity
import com.example.datatabledemo.ui.theme.DataTableDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color(0xff212121).toArgb()
            )
        )
        setContent {
            DataTableDemoTheme(darkTheme = true) {
                Navigator(HomeActivity()){
                    SlideTransition(navigator = it)
                }

            }
        }
    }
}


@Composable
fun MyApp() {
    var countDownTimer: CountDownTimer? = null
    val timerDuration = 60000L
    var pauseOffset = 0L

    val timerText = remember { mutableStateOf("${(timerDuration / 1000)}") }

    fun startTimer(pauseOffsetL:Long) {
        countDownTimer = object : CountDownTimer(timerDuration - pauseOffsetL, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration - millisUntilFinished
                timerText.value = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
              Log.d("timer","finish")
            }

        }.start()
    }
    fun pauseTimer() {
        countDownTimer?.cancel()
    }
    fun resetTimer() {
        pauseOffset = 0L
        timerText.value = "${(timerDuration / 1000)}"
        countDownTimer?.cancel()
        countDownTimer = null
    }



    Column(modifier = Modifier.fillMaxSize()) {
      Spacer(modifier = Modifier.height(100.dp))

       Text(text = timerText.value)
        TextButton(onClick = { startTimer(pauseOffset) }) {
            Text(text = "START")
        }

        TextButton(onClick = { pauseTimer() }) {
            Text(text = "PAUSE")
        }
        TextButton(onClick = { resetTimer() }) {
            Text(text = "RESET")
        }


    }



}
