package com.example.composetaskerapp.data.repository

import com.example.composetaskerapp.cammon.extensions.mapTOCache
import com.example.composetaskerapp.cammon.extensions.mapToTask
import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.domain.repository.TaskRepository
import com.example.composetaskerapp.presentation.TaskApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImp : TaskRepository {

    private val dao = TaskApp.database.fetchTaskDao()


    override fun fetchAllTasksFlow(): Flow<List<Task>> {
        return dao.fetchAllTasksFlow()
            .map { it.map { it.mapToTask() } }
    }

    override fun fetchAllTaskCategories(): List<TaskCategory> {
        TODO("Not yet implemented")
    }

    override fun fetchAllTasksByCategoryId(categoryId: String): List<Task> {
        TODO("Not yet implemented")
    }

    override fun fetchAllTasksSizeByCategoryId(categoryId: String): Int {
        return  dao.fetchTasksSizeByCategory(categoryId).size
    }

    override fun fetchTaskById(taskId: String): Task {
        TODO("Not yet implemented")
    }

    override fun fetchTaskCategoryById(categoryId: String): TaskCategory {
        TODO("Not yet implemented")
    }

    override fun addNewTask(task: Task): Boolean {
        dao.addNewTask(task.mapTOCache())
        return true
    }

    override fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun removeTaskById(id: String) {
        TODO("Not yet implemented")
    }

    override fun removeTasksByIds(ids: List<String>) {
        dao.deleteTaskByIds(ids)
    }
}