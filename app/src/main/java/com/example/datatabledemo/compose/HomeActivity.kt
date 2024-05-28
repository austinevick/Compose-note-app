package com.example.datatabledemo.compose

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.datatabledemo.R
import com.example.datatabledemo.calendar.CalendarActivity
import com.example.datatabledemo.components.DeleteNoteBottomSheet
import com.example.datatabledemo.components.NoteGridView
import com.example.datatabledemo.components.NoteListView
import com.example.datatabledemo.routes.Screen
import com.example.datatabledemo.viewmodel.SharedViewModel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeActivity(navController: NavHostController) {

    val showGridView = remember { mutableStateOf(false) }
    val viewModel = hiltViewModel<SharedViewModel>()
    val noteList = viewModel.noteListState.collectAsState()
    val selectedNotes = viewModel.selectedNotes
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }


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



























