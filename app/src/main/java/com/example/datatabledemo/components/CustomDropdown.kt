package com.example.datatabledemo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.datatabledemo.database.Priority


@Composable
fun CustomDropdown(
    isExpanded: MutableState<Boolean>,
    selectedValue: MutableState<Priority>,
    list: List<Priority>
) {
    Box {
        TextButton(onClick = { isExpanded.value = true }) {
            Text(text = selectedValue.value.toString(), color = Color.White)
            Icon(
                Icons.Outlined.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White
            )
        }
        DropdownMenu(modifier = Modifier
            .width(180.dp)
            .background(Color.White, RoundedCornerShape(14.dp)),
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }) {
            List(list.size) {
                DropdownMenuItem(
                    text = {
                        Row {
                            Text(text = list[it].toString(),
                                modifier = Modifier.weight(1f),
                                color = Color.Black)
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        list[it].color,
                                        CircleShape
                                    )
                            )
                        }
                    },
                    onClick = {
                        selectedValue.value = list[it]
                        isExpanded.value = false
                    })
            }
        }
    }
}

