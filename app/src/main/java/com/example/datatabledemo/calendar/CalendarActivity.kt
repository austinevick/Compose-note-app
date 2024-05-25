package com.example.datatabledemo.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarActivity() {
    val dataSource = CalendarDataSource()
    val dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    var calendarUiModel by remember { mutableStateOf(dataSource
        .getData(lastSelectedDate = dataSource.today)) }
    val currentDate = remember { mutableStateOf(calendarUiModel
        .selectedDate.date.format(dateTimeFormatter)) }


    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Header(calendarUiModel,
            date = currentDate.value,
            onPrevClickListener = { startDate ->
                val finalStartDate = startDate.minusDays(1)
                calendarUiModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarUiModel.selectedDate.date
                )
                currentDate.value = finalStartDate.format(dateTimeFormatter)
            },
            onNextClickListener = { endDate ->
                val finalStartDate = endDate.plusDays(2)
                calendarUiModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarUiModel.selectedDate.date
                )
                currentDate.value = finalStartDate.format(dateTimeFormatter)
            }
        )
        Content(calendarUiModel, onDateClickListener = { date ->
            currentDate.value = date.date.format(dateTimeFormatter)
            calendarUiModel = calendarUiModel.copy(
                selectedDate = date,
                visibleDates = calendarUiModel.visibleDates.map {
                    it.copy(isSelected = it.date.isEqual(date.date))
                }
            )
        })
    }
}