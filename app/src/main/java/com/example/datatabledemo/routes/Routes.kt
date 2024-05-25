package com.example.datatabledemo.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.datatabledemo.compose.AddTask
import com.example.datatabledemo.compose.HomeActivity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Routes() {
    val navController = rememberNavController()
    NavHost(navController =navController , startDestination = Screen.HOME.name ){
        composable(Screen.HOME.name){
            HomeActivity(navController)
        }
        composable(Screen.ADDTASK.name){
            AddTask(navController)
        }
    }

}

enum class Screen{
    HOME,
    ADDTASK
}