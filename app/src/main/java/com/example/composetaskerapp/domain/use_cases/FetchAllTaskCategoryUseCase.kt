package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.data.repository.TaskCategoryRepository
import com.example.composetaskerapp.domain.models.TaskCategory
import kotlinx.coroutines.flow.Flow

class FetchAllTaskCategoryUseCase(
    private val repository: TaskCategoryRepository
) {
    operator fun invoke(): Flow<List<TaskCategory>> {
        return repository.fetchAllTaskCategoriesFlow()
    }
}

