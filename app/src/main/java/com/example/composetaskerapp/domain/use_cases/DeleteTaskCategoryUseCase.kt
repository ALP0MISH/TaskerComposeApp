package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.data.repository.TaskCategoryRepository

class DeleteTaskCategoryUseCase (
    private val repository: TaskCategoryRepository
) {
    operator fun invoke(id: String) {
        repository.deleteTaskCategoryById(id)
    }
}
