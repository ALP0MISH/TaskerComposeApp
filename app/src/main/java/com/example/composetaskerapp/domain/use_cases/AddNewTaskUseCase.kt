package com.example.composetaskerapp.domain.use_cases

import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.repository.TaskRepository

class AddNewTaskUseCase(
    private val repository: TaskRepository
){
    operator fun invoke(task: Task): Boolean {
        return repository.addNewTask(task)
    }
}
