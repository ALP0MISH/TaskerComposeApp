package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.domain.repository.TaskRepository

class RemoveTaskByIdsUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(ids: List<String>) {
        repository.removeTasksByIds(ids)
    }
}
