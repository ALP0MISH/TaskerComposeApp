package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.data.repository.TaskCategoryRepository
import com.example.composetaskerapp.domain.models.TaskCategory

class FetchTaskCategoryUseCase(
    private val repository: TaskCategoryRepository
) {
    operator fun invoke(id: String): TaskCategory {
        return repository.fetchTaskCategoryById(id)
    }
}
