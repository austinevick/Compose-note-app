package com.example.datatabledemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.datatabledemo.compose.HomeActivity
import com.example.datatabledemo.routes.Routes
import com.example.datatabledemo.ui.theme.DataTableDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color(0xff212121).toArgb()))
        setContent {
            DataTableDemoTheme(darkTheme = true) {
            Routes()
            }
        }
    }
}