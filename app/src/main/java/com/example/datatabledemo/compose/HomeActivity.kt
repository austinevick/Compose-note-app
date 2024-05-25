package com.example.datatabledemo.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.datatabledemo.R
import com.example.datatabledemo.calendar.CalendarActivity
import com.example.datatabledemo.routes.Screen
import com.example.datatabledemo.util.dateFormatter
import com.example.datatabledemo.viewmodel.SharedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun HomeActivity(navController: NavHostController) {
    val hapticFeedback = LocalHapticFeedback.current
    val showGridView = remember { mutableStateOf(false) }
    val viewModel = hiltViewModel<SharedViewModel>()
    val noteList = viewModel.noteListState.collectAsState()
    val selectedNotes = viewModel.selectedNotes


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = Color(0xff212121),
                    titleContentColor = Color.White,
                    scrolledContainerColor = Color.White,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                title = {
                    Text(text = if (selectedNotes.isNotEmpty()) "${selectedNotes.size} Selected" else "Notes")
                },
                actions = {
                    if (selectedNotes.isNotEmpty()) IconButton(onClick = {
                        viewModel.onEvent(NoteEvent.DeleteNotes(selectedNotes))
                    }) {
                        Icon(Icons.Outlined.Delete, contentDescription = null)
                    }
                    else
                        IconButton(onClick = { showGridView.value = !showGridView.value }) {
                            Icon(
                                painterResource(
                                    id = if (showGridView.value) R.drawable.list else
                                        R.drawable.grid_view
                                ),
                                contentDescription = null
                            )
                        }
                }
            )
        },
        containerColor = Color(0xff212121),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.ADDTASK.name)
            }) {
                Icon(Icons.Outlined.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {

            CalendarActivity()

            Spacer(modifier = Modifier.height(25.dp))

            AnimatedContent(
                targetState = showGridView.value,
                label = ""
            ) { targetState ->
                when (targetState) {
                    true -> {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            maxItemsInEachRow = 2,
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            List(noteList.value.size) {
                                val note = noteList.value[it]
                                OutlinedCard(
                                    border = BorderStroke(
                                        2.dp,
                                        if (selectedNotes.contains(note.id)) Color.Green else Color.Red
                                    ),
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .animateContentSize()
                                        .size(150.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = {
                                                    if (selectedNotes.contains(note.id)) {
                                                        selectedNotes.remove(note.id)
                                                    } else {
                                                        selectedNotes.add(note.id)
                                                    }
                                                },
                                                onLongPress = {
                                                    hapticFeedback.performHapticFeedback(
                                                        HapticFeedbackType.LongPress
                                                    )
                                                    if (selectedNotes.contains(note.id)) {
                                                        selectedNotes.remove(note.id)
                                                    } else {
                                                        selectedNotes.add(note.id)
                                                    }
                                                }
                                            )
                                        }
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = note.title, fontSize = 17.sp,
                                            fontWeight = FontWeight.SemiBold, color = Color.White
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = note.description,
                                            color = Color.White.copy(alpha = 0.9f),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    false -> {
                        Column {
                            List(noteList.value.size) {
                                val note = noteList.value[it]
                                OutlinedCard(
                                    border = BorderStroke(
                                        2.dp,
                                        if (selectedNotes.contains(note.id)) Color.Green else Color.Red
                                    ),
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .animateContentSize()
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = {
                                                    if (selectedNotes.size > 0) {
                                                        if (selectedNotes.contains(note.id)) {
                                                            selectedNotes.remove(note.id)
                                                        } else {
                                                            selectedNotes.add(note.id)
                                                        }
                                                    } else {
                                                        //TODO: Navigate to detail page
                                                    }
                                                },
                                                onLongPress = {
                                                    hapticFeedback.performHapticFeedback(
                                                        HapticFeedbackType.LongPress)
                                                    if (selectedNotes.contains(note.id)) {
                                                        selectedNotes.remove(note.id)
                                                    } else {
                                                        selectedNotes.add(note.id)
                                                    }
                                                }
                                            )
                                        }) {
                                    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                                        Text(
                                            text = note.title, fontSize = 17.sp,
                                            fontWeight = FontWeight.SemiBold, color = Color.White
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = note.description,
                                                color = Color.White,
                                               fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                        Text(modifier = Modifier.align(Alignment.End),
                                            text = dateFormatter(note.date),
                                            color = Color.White.copy(alpha = 0.7f),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



























