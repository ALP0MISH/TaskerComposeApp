package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class FetchAllTaskUseCase (
    private val repository: TaskRepository
){
    operator fun invoke(): Flow<List<Task>> {
        return repository.fetchAllTasksFlow()
    }
}
