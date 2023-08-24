package com.example.composetaskerapp.presentation.screens.main_screen

import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.presentation.models.TaskUI

data class MainUiState (
    val tasks: List<TaskUI> = emptyList(),
    val taskCategories: List<Pair<TaskCategory, Int>> = emptyList(),
    val selectedTasks: Set<TaskUI> = emptySet()
)