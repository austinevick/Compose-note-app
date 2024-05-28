package com.example.datatabledemo.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datatabledemo.R
import com.example.datatabledemo.database.NoteEntity
import com.example.datatabledemo.util.dateFormatter

@Composable
fun NoteListView (
    noteList: State<List<NoteEntity>>,
    selectedNotes: SnapshotStateList<Int>
){
    val hapticFeedback = LocalHapticFeedback.current

    Column {
        List(noteList.value.size) {
            val note = noteList.value[it]
            OutlinedCard(
                border = BorderStroke(
                    2.dp,
                    if (selectedNotes.contains(note.id)) Color.Green else note.priority.color
                ),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .animateContentSize()
                    .fillMaxWidth()

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
                                    HapticFeedbackType.LongPress
                                )
                                if (selectedNotes.contains(note.id)) {
                                    selectedNotes.remove(note.id)
                                } else {
                                    selectedNotes.add(note.id)
                                }
                            }
                        )
                    }) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)) {
                    Text(
                        text = note.title, fontSize = 17.sp, maxLines = 1,
                        fontWeight = FontWeight.SemiBold, color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = note.description,
                        color = Color.White,
                        fontSize = 12.sp, maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = dateFormatter(note.date),
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                       if (note.alarmDate !=null) Icon(painterResource(id = R.drawable.access_alarm),
                           tint = Color.White, modifier = Modifier.size(18.dp), contentDescription = null)
                        if (note.alarmDate !=null)  Text(
                            text = note.alarmDate,
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