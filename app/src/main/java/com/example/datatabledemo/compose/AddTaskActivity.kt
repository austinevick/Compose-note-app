package com.example.datatabledemo.compose

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.datatabledemo.AlarmReceiver
import com.example.datatabledemo.components.CustomDropdown
import com.example.datatabledemo.components.CustomTextField
import com.example.datatabledemo.components.TimePickerDialog
import com.example.datatabledemo.database.Priority
import com.example.datatabledemo.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
class AddTaskActivity():Screen {
@Composable
    override fun Content() {
val navigator = LocalNavigator.current
    val context = LocalContext.current
    val viewModel = hiltViewModel<SharedViewModel>()
    val isExpanded = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }

    val state = viewModel.state.collectAsState()

    /// Alarm settings
    val setReminder = remember { mutableStateOf(false) }
    val hours = remember { mutableIntStateOf(0) }
    val minutes = remember { mutableIntStateOf(0) }
    val alarmTime: LocalDateTime =
        LocalDateTime.of(LocalDate.now(), LocalTime.of(hours.intValue, minutes.intValue))
    val zoneDateTime: ZonedDateTime = ZonedDateTime.of(alarmTime, ZoneId.systemDefault())
    val startAtMillis = zoneDateTime.toInstant().toEpochMilli()
    val simpleFormatter: String = SimpleDateFormat.getDateTimeInstance().format(Date(startAtMillis))
    val alarmTimeFormatter = SimpleDateFormat("hh:mm a").format(Date(startAtMillis))


    val timePickerState = rememberTimePickerState(
        initialHour = LocalDateTime.now().hour,
        initialMinute = LocalDateTime.now().minute,
        is24Hour = true
    )

    val alarmManager = context.getSystemService(AlarmManager::class.java)


    val textStyle = TextStyle(
        color = Color.White, fontWeight = FontWeight.SemiBold
    )
    Scaffold(containerColor = Color(0xff212121),
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarColors(
                containerColor = Color(0xff212121),
                titleContentColor = Color.White,
                scrolledContainerColor = Color.White,
                actionIconContentColor = Color.White,
                navigationIconContentColor = Color.White
            ),
                navigationIcon = {
                    IconButton(onClick = { navigator?.pop() }) {
                        Icon(
                            Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    CustomDropdown(
                        isExpanded = isExpanded,
                        selectedValue = viewModel.selectedPriority,
                        list = Priority.entries
                    )
                },
                title = { Text(text = "New Task") })
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            LazyColumn {
                item {

                    Text(
                        text = "Title", style = textStyle,
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                    )
                    CustomTextField(
                        value = state.value.title,
                        textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold),
                        onValueChange = { viewModel.onEvent(NoteEvent.SetTitle(it)) },
                        placeholder = "Enter title"
                    )

                    Text(
                        text = "Description", style = textStyle,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            bottom = 4.dp, top = 16.dp
                        )
                    )
                    CustomTextField(
                        value = state.value.description,
                        onValueChange = { viewModel.onEvent(NoteEvent.SetDescription(it)) },
                        maxLines = 8, singleLine = false,
                        modifier = Modifier.height(150.dp),
                        placeholder = "Enter description"
                    )
                    Text(
                        text = "Set Reminder", style = textStyle,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            bottom = 4.dp, top = 16.dp
                        )
                    )
                    CustomTextField(
                        value = if (setReminder.value) simpleFormatter else "Set reminder",
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showTimePicker.value = true
                                setReminder.value = true
                            }) {
                                Icon(
                                    Icons.Outlined.DateRange,
                                    contentDescription = null
                                )
                            }
                        },
                        placeholder = "Set reminder"
                    )
                }
            }
            if (showTimePicker.value) TimePickerDialog(
                onDismissRequest = { showTimePicker.value = false },
                onConfirm = {
                    hours.intValue = timePickerState.hour
                    minutes.intValue = timePickerState.minute
                    showTimePicker.value = false
                    Log.d("asd", "${hours.intValue}:${minutes.intValue}")
                },
                onDismiss = { showTimePicker.value = false }) {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        periodSelectorSelectedContainerColor = Color(0xffff5700)
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffff5700)
            ),
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .height(50.dp),
                onClick = {
                    viewModel.onEvent(NoteEvent.SaveNote, alarmTime = alarmTimeFormatter)
                    navigator?.pop()
                    val intent = Intent(context, AlarmReceiver::class.java).apply {
                        putExtra("EXTRA_TITLE", viewModel.state.value.title)
                        putExtra("EXTRA_DESCRIPTION", viewModel.state.value.description)
                    }

                    if (setReminder.value) alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        startAtMillis,
                        PendingIntent.getBroadcast(
                            context, Date().time.hashCode(), intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                }) {
                Text(text = "Add Task", color = Color.White)
            }
            Spacer(modifier = Modifier.height(30.dp))

        }
    }
}
}
