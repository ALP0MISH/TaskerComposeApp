package com.example.composetaskerapp.presentation.screens.add_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetaskerapp.R
import com.example.composetaskerapp.data.repository.TaskCategoryRepositoryImpl
import com.example.composetaskerapp.data.repository.TaskRepositoryImp
import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.domain.repository.TaskRepository
import com.example.composetaskerapp.domain.use_cases.AddNewTaskUseCase
import com.example.composetaskerapp.domain.use_cases.FetchAllTaskCategoryUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID

class AddTaskViewModel : ViewModel() {

    private val repository: TaskRepository = TaskRepositoryImp()
    private val taskCategoryRepository = TaskCategoryRepositoryImpl()

    private val addNewUseCase = AddNewTaskUseCase(repository)
    private val fetchAllCategoryUseCase = FetchAllTaskCategoryUseCase(taskCategoryRepository)

    var uiState by mutableStateOf(AddTaskScreenUiState())

    private val _toastFlow = MutableSharedFlow<Int?>(1)
    val toastFlow = _toastFlow.asSharedFlow()

    private val _navigateUpTOFlow = MutableStateFlow<Unit?>(null)
    val navigateUPTOFlow = _navigateUpTOFlow.asStateFlow()

    init {
        fetchAllCategoryUseCase().onEach { taskCategories ->
            uiState = uiState.copy(tasksCategories = taskCategories)
        }.launchIn(viewModelScope)
    }

    fun updateSelectedDate(date: String) {
        uiState = uiState.copy(selectedDate = date)
    }

    fun updateSelectedTime(time: String) {
        uiState = uiState.copy(selectedTime = time)
    }
    fun updateSelectedTitle(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun updateSelectedCategory(category: TaskCategory) {
        uiState = uiState.copy(selectedCategory = category)
    }

    fun addNewTask() {
        if (uiState.title.isNullOrBlank()){
            _toastFlow.tryEmit(R.string.error_empty_title)
            return

        }
        if (uiState.selectedDate.isNullOrBlank()){
            _toastFlow.tryEmit(R.string.error_empty_selectedDate)
            return

        }
        if (uiState.selectedTime.isNullOrBlank()){
            _toastFlow.tryEmit(R.string.error_empty_selectedTime)
            return

        }
        if (uiState.selectedCategory == null){
            _toastFlow.tryEmit(R.string.error_empty_title)
            return

        }

        var task = Task(
            id = UUID.randomUUID().toString(),
            time = uiState.selectedTime!!,
            date = uiState.selectedDate!!,
            categoryId = uiState.selectedCategory!!.id,
            title = uiState.title!!,
            categoryColor = uiState.selectedCategory!!.colorCode
        )
        addNewUseCase(task)
        uiState = AddTaskScreenUiState()
        _navigateUpTOFlow.tryEmit(Unit)
    }

    
}