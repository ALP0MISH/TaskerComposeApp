package com.example.composetaskerapp.presentation.screens.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetaskerapp.cammon.extensions.mapToTaskUi
import com.example.composetaskerapp.data.repository.TaskCategoryRepositoryImpl
import com.example.composetaskerapp.data.repository.TaskRepositoryImp
import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.repository.TaskRepository
import com.example.composetaskerapp.domain.use_cases.FetchAllTaskCategoryUseCase
import com.example.composetaskerapp.domain.use_cases.FetchAllTaskUseCase
import com.example.composetaskerapp.domain.use_cases.FetchAllTasksSizeByCategoryIdUseCase
import com.example.composetaskerapp.domain.use_cases.RemoveTaskByIdsUseCase
import com.example.composetaskerapp.presentation.models.TaskUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.util.UUID

class MainViewModel : ViewModel() {

    val repository: TaskRepository = TaskRepositoryImp()
    val taskCategoryRepository: TaskCategoryRepositoryImpl = TaskCategoryRepositoryImpl()
    private val fetchAllTasksUseCase = FetchAllTaskUseCase(repository)
    private val removeTaskBuIdsUseCase = RemoveTaskByIdsUseCase(repository)
    private val fetchAllTaskCategoryUseCase = FetchAllTaskCategoryUseCase(taskCategoryRepository)
    private val fetchAllTasksSizeByCategoryIdUseCase = FetchAllTasksSizeByCategoryIdUseCase(repository)

    var uiState by mutableStateOf(MainUiState())

    init {

        fetchAllTasksUseCase()
            .onEach { tasks ->
            uiState = uiState.copy(tasks = tasks.map { it.mapToTaskUi() })
        }.launchIn(viewModelScope)

        fetchAllTaskCategoryUseCase()
            .map { taskCategories ->
                val taskCategoryAndCount = taskCategories.map { category ->
                    val count = fetchAllTasksSizeByCategoryIdUseCase(categoryId = category.id)
                    Pair(category, count)
                }
                taskCategoryAndCount
            }
            .onEach { taskCategories ->
            uiState = uiState.copy(taskCategories = taskCategories)
        }.launchIn(viewModelScope)
    }

    fun onSelectItem(task: TaskUI, isSelected: Boolean) {
        val isSelectedTasks = uiState.selectedTasks.toMutableSet()
        if (isSelected) {
            isSelectedTasks.add(task)
        } else {
            isSelectedTasks.remove(task)
        }
        uiState = uiState.copy(selectedTasks = isSelectedTasks)


    }
    fun onRemoveSelectedItems() {
        val removedTasks = uiState.selectedTasks
        val taskIds = removedTasks.map { task:TaskUI -> task.id }
        removeTaskBuIdsUseCase(taskIds)
    }
    fun onSelectAllItems(){
       val selectedTasks = uiState.selectedTasks.toMutableSet()
        selectedTasks.addAll(uiState.tasks)
        uiState = uiState.copy(selectedTasks = selectedTasks)
    }
    fun onUnSelectAllItems(){
        val selectedTasks = uiState.selectedTasks.toMutableSet()
        selectedTasks.clear()
        uiState = uiState.copy(selectedTasks = selectedTasks)
    }
}



