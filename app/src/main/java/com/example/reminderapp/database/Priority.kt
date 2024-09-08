package com.example.reminderapp.database

import androidx.compose.ui.graphics.Color
import com.example.reminderapp.ui.theme.HighPriorityColor
import com.example.reminderapp.ui.theme.LowPriorityColor
import com.example.reminderapp.ui.theme.MediumPriorityColor
import com.example.reminderapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}