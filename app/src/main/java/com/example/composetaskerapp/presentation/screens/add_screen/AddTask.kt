package com.example.composetaskerapp.presentation.screens.add_screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composetaskerapp.presentation.screens.main_screen.MainScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Destination(start = false)
@Composable
fun AddTask(
    navigator: DestinationsNavigator
) {
    val viewModel : AddTaskViewModel = viewModel()

    viewModel.navigateUPTOFlow.filterNotNull().onEach {
        navigator.navigateUp()
    }.launchIn(rememberCoroutineScope())

    AddTaskScreen(
        uiState = viewModel.uiState,
        onSaveTask =  viewModel::addNewTask,
        toastFlow = viewModel.toastFlow,
        onCancelClick = { navigator.navigateUp() },
        updateSelectedTitle = viewModel::updateSelectedTitle,
        updateSelectedTime = viewModel::updateSelectedTime,
        updateSelectedDate = viewModel::updateSelectedDate,
        updateSelectedCategory = viewModel::updateSelectedCategory,
    )
}