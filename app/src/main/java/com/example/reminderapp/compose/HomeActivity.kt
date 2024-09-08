package com.example.reminderapp.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.reminderapp.R
import com.example.reminderapp.calendar.CalendarActivity
import com.example.reminderapp.components.DeleteNoteBottomSheet
import com.example.reminderapp.components.NoteGridView
import com.example.reminderapp.components.NoteListView
import com.example.reminderapp.viewmodel.SharedViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class
)
class HomeActivity() : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val showGridView = remember { mutableStateOf(false) }
        val viewModel = hiltViewModel<SharedViewModel>()
        val noteList = viewModel.noteListState.collectAsState()
        val selectedNotes = viewModel.selectedNotes
        val context = LocalContext.current
        val showDialog = remember { mutableStateOf(false) }
        val permission = rememberMultiplePermissionsState(
            listOf(android.Manifest.permission.SCHEDULE_EXACT_ALARM,
                android.Manifest.permission.POST_NOTIFICATIONS)
        )

        LaunchedEffect(key1 = true) {

            permission.launchMultiplePermissionRequest()

        }


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
                            showDialog.value = true
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
                    navigator?.push(AddTaskActivity())
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
                            NoteGridView(
                                noteList = noteList,
                                selectedNotes = selectedNotes
                            )
                        }

                        false -> {
                            NoteListView(
                                noteList = noteList,
                                selectedNotes = selectedNotes
                            )
                        }
                    }
                }
            }
            if (showDialog.value) DeleteNoteBottomSheet(showDialog = showDialog) {
                viewModel.onEvent(NoteEvent.DeleteNotes(selectedNotes))
                showDialog.value = false
            }
        }
    }
}



























