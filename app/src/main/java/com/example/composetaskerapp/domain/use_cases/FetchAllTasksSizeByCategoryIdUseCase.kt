package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.domain.repository.TaskRepository

class FetchAllTasksSizeByCategoryIdUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(categoryId: String): Int{
        return repository.fetchAllTasksSizeByCategoryId(categoryId)
    }
}