package com.example.datatabledemo.compose

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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.datatabledemo.components.CustomDropdown
import com.example.datatabledemo.components.CustomTextField
import com.example.datatabledemo.database.Priority
import com.example.datatabledemo.viewmodel.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(navController: NavHostController) {

    val selectedDate = remember { mutableStateOf("") }
    val viewModel = hiltViewModel<SharedViewModel>()
    val isExpanded = remember { mutableStateOf(false) }
    val selectedPriority = remember { mutableStateOf(Priority.LOW) }

    val state = viewModel.state.collectAsState()

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    CustomDropdown(
                        isExpanded = isExpanded,
                        selectedValue = selectedPriority,
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
                        onValueChange = { viewModel.onEvent(NoteEvent.SetTitle(it)) },
                        placeholder = "Enter title"
                    )

                    Text(
                        text = "Description(Optional)", style = textStyle,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            bottom = 4.dp, top = 16.dp
                        )
                    )
                    CustomTextField(
                        value = state.value.description,
                        onValueChange = { viewModel.onEvent(NoteEvent.SetDescription(it)) },
                        maxLines = 5, singleLine = false,
                        modifier = Modifier.height(150.dp),
                        placeholder = "Enter description"
                    )
                    Text(
                        text = "Set Reminder(Optional)", style = textStyle,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            bottom = 4.dp, top = 16.dp
                        )
                    )
                    CustomTextField(
                        value = selectedDate.value,
                        onValueChange = { selectedDate.value = it },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Outlined.DateRange,
                                contentDescription = null
                            )
                        },
                        placeholder = "Set reminder"
                    )

                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffff5700)
            ),
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .height(50.dp),
                onClick = {
                    viewModel.onEvent(NoteEvent.SaveNote)
                    navController.popBackStack()
                }) {
                Text(text = "Add Task", color = Color.White)
            }
            Spacer(modifier = Modifier.height(30.dp))

        }
    }

}