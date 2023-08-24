package com.example.composetaskerapp.presentation.models

import com.example.composetaskerapp.domain.models.Task
import java.util.UUID

data class TaskUI(
    val id: String,
    val title: String,
    val time: String,
    val date: String,
    val categoryId: String,
    var isSelected: Boolean,
    var categoryColor: String,
){ companion object {
    val preview = TaskUI(
        id = "1",
        time = "22:00",
        date = "04.05.2023",
        title = "Go to school",
        categoryId = "",
        isSelected = false,
        categoryColor = ""
    )
    val previews = listOf(
        preview.copy(
            id = UUID.randomUUID().toString(),
        ),
        preview.copy(
            id = UUID.randomUUID().toString(),
        ),
        preview.copy(
            id = UUID.randomUUID().toString(),
        ),
        preview.copy(
            id = UUID.randomUUID().toString(),
        ),
    )
}
}

