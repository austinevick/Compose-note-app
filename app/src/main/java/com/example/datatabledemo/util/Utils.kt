package com.example.datatabledemo.util

import java.text.SimpleDateFormat
import java.util.Date

fun dateFormatter(date: Date):String{
    return SimpleDateFormat.getDateInstance().format(date)
}