package com.example.reminderapp.util

import java.text.SimpleDateFormat
import java.util.Date

fun dateFormatter(date: Date):String{
    return SimpleDateFormat.getDateInstance().format(date)
}