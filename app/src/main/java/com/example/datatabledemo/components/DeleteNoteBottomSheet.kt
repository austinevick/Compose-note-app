package com.example.datatabledemo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteNoteBottomSheet(
    showDialog: MutableState<Boolean>,
    onDeleteNote: () -> Unit,

    ) {
    ModalBottomSheet(
        containerColor = Color.White,
        shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
        onDismissRequest = { showDialog.value = false }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Delete Note", fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "This action cannot be undone.")
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(
                    onClick = { showDialog.value = false }) {
                    Text(text = "CANCEL", fontSize = 18.sp, color = Color.Black)
                }

                TextButton(onClick = onDeleteNote) {
                    Text(text = "DELETE", fontSize = 18.sp, color = Color.Red)
                }
            }

        }
    }
}