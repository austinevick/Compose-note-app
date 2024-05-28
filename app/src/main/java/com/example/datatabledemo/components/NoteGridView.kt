package com.example.datatabledemo.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datatabledemo.database.NoteEntity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteGridView (
     noteList: State<List<NoteEntity>>,
     selectedNotes: SnapshotStateList<Int>
){
    val hapticFeedback = LocalHapticFeedback.current
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
                            }
                            ,
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