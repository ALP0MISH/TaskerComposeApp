package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.data.repository.TaskCategoryRepository
import com.example.composetaskerapp.domain.models.TaskCategory

class AddTaskCategoryUseCase(
    private val repository: TaskCategoryRepository

){
    operator fun invoke(taskCategory: TaskCategory) {
        repository.addTaskCategory(taskCategory)
    }
}