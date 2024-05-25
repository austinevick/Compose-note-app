package com.example.datatabledemo.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
) {
    LazyRow {
        items(items = data.visibleDates) { date ->
            ContentItem(date, onDateClickListener = onDateClickListener)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentItem(
    date: CalendarUiModel.Date,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .background(
                if (date.isSelected) Color(0xffff5700)
                else Color(0xff333333),
                RoundedCornerShape(8.dp)
            )
            .height(60.dp)
            .width(50.dp)
            .clickable { onDateClickListener(date) },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = date.date.dayOfMonth.toString(),
                fontSize = 13.sp,
                fontWeight = FontWeight.W700, color = Color.White)
            Text(text = date.day, fontSize = 13.sp,
                fontWeight = FontWeight.W700,
                color = Color.White)
        }
    }
}