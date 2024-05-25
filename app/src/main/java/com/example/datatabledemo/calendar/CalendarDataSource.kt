package com.example.datatabledemo.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SelectableDates
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarDataSource {
    val today: LocalDate @RequiresApi(Build.VERSION_CODES.O)
    get(){
        return LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(startDate: LocalDate=today, lastSelectedDate: LocalDate):CalendarUiModel{
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek,endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDatesBetween(firstDayOfWeek: LocalDate, endDayOfWeek: LocalDate): List<LocalDate> {
val numOfDays = ChronoUnit.DAYS.between(firstDayOfWeek,endDayOfWeek)
        return Stream.iterate(firstDayOfWeek){date->
            date.plusDays(1)
        }.limit(numOfDays).collect(Collectors.toList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectableDate: LocalDate
    ):CalendarUiModel{
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectableDate,true),
            visibleDates = dateList.map {
                toItemUiModel(it,it.isEqual(lastSelectableDate))
            }
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}