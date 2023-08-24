package com.example.composetaskerapp.cammon.extensions

import com.example.composetaskerapp.data.models.TaskCache
import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.presentation.models.TaskUI

fun Task.mapTOCache(): TaskCache {
    return TaskCache(
        id = id,
        title = title,
        time = time,
        date = date,
        categoryId = categoryId,
        categoryColor = categoryColor
    )
}

fun TaskCache.mapToTask(): Task {
    return Task(
        id = id,
        title = title,
        time = time,
        date = date,
        categoryId = categoryId,
        categoryColor = categoryColor
    )
}

fun Task.mapToTaskUi(): TaskUI {
    return TaskUI(
        id = id,
        title = title,
        time = time,
        date = date,
        categoryId = categoryId,
        isSelected = false,
        categoryColor = categoryColor
    )
}