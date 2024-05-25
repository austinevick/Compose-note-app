package com.example.datatabledemo.database

import androidx.compose.ui.graphics.Color
import com.example.datatabledemo.ui.theme.HighPriorityColor
import com.example.datatabledemo.ui.theme.LowPriorityColor
import com.example.datatabledemo.ui.theme.MediumPriorityColor
import com.example.datatabledemo.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}