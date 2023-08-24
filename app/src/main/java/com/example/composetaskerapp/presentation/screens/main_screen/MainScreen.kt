package com.example.composetaskerapp.presentation.screens.main_screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetaskerapp.R
import com.example.composetaskerapp.cammon.extensions.SpacerHeight
import com.example.composetaskerapp.cammon.extensions.SpacerWidth
import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.presentation.components.FABComponent
import com.example.composetaskerapp.presentation.components.TaskCategoryItem
import com.example.composetaskerapp.presentation.components.TaskItem
import com.example.composetaskerapp.presentation.models.TaskUI
import com.example.composetaskerapp.presentation.theme.ComposeTaskerAppTheme
import com.example.composetaskerapp.presentation.theme.ExtraMediumSpacing
import com.example.composetaskerapp.presentation.theme.LargeSpacing
import com.example.composetaskerapp.presentation.theme.MediumSpacing
import com.example.composetaskerapp.presentation.theme.SmallSpacing

@Preview
@Composable
fun MainScreenPreview() {
    ComposeTaskerAppTheme {
        MainScreen(
            uiState = MainUiState(tasks = TaskUI.previews),
            onSelectItem = { _, _ -> },
            onRemoveSelectedItems = {},
            onSelectAllItems = {},
            onUnSelectAllItems = {},
            onTaskClick = {},
            onTaskCategoryClick = {}
            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onSelectAllItems: () -> Unit,
    onUnSelectAllItems: () -> Unit,
    onTaskClick: () -> Unit,
    onTaskCategoryClick: () -> Unit,
    onSelectItem: (TaskUI, Boolean) -> Unit,
    onRemoveSelectedItems: () -> Unit,

    ) {
    Scaffold(floatingActionButton = {
        FABComponent(
            onTaskClick = onTaskClick,
            onTaskCategoryClick = onTaskCategoryClick,

            )
    }) { innerPaddings ->
        LazyColumn(
            modifier = modifier
                .padding(innerPaddings)
                .fillMaxWidth()
        ) {

            item {
                MainScreenHeader(
                    onRemoveSelectedItems = onRemoveSelectedItems,
                    onUnselectAllItems = onUnSelectAllItems,
                    onSelectAllItems = onSelectAllItems,

                    )
            }
            items(items = uiState.tasks,
                key = { item -> item.id + item.isSelected.hashCode() }) { task ->
                TaskItem(
                    task = task,
                    onClick = {},
                    onSelected = onSelectItem,
                    isSelected = uiState.selectedTasks.contains(task)
                )

                Spacer(modifier = modifier.height(LargeSpacing))
            }
            item {
                Spacer(modifier = modifier.height(LargeSpacing + LargeSpacing))
                Row {
                    Spacer(modifier = modifier.width(60.dp))
                    Text(
                        text = stringResource(id = R.string.list),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (isSystemInDarkTheme()) Color.White
                            else colorResource(id = R.color.large_gray)
                        )
                    )
                }
                Spacer(modifier = modifier.height(MediumSpacing))
            }
            items(
                items = uiState.taskCategories,
                key = { item -> item.first.id }) { categoryAndSize ->
                val (category, size) = categoryAndSize
                Column(
                    modifier = modifier.padding(start = 60.dp, end = SmallSpacing)
                ) {
                    SpacerHeight(dp = SmallSpacing)
                    TaskCategoryItem(category = category, count = size)
                    SpacerHeight(dp = SmallSpacing)
                }
            }
        }
    }
}

@Composable
fun MainScreenHeader(
    modifier: Modifier = Modifier,
    onRemoveSelectedItems: () -> Unit,
    onUnselectAllItems: () -> Unit,
    onSelectAllItems: () -> Unit,

    ) {
    var isDropDownVisible by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .padding(top = LargeSpacing)
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = modifier.width(60.dp))
        Text(
            text = stringResource(id = R.string.today),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = modifier.weight(1f))
        Column {
            IconButton(onClick = { isDropDownVisible = !isDropDownVisible }) {
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = null,
                    tint = colorResource(id = R.color.blue)
                )
            }
            OptionsDropDown(
                isVisible = isDropDownVisible,
                onDismissRequest = { isDropDownVisible = false },
                onRemoveItems = {
                    onRemoveSelectedItems()
                    isDropDownVisible = false
                },
                unSelectAllItems = {
                    onUnselectAllItems()
                    isDropDownVisible = false
                },
                selectAllItems = {
                    onSelectAllItems()
                    isDropDownVisible = false
                }

            )
        }
        Spacer(modifier = modifier.width(ExtraMediumSpacing))
    }
}

@Composable
fun OptionsDropDown(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveItems: () -> Unit,
    unSelectAllItems: () -> Unit,
    selectAllItems: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = MediumSpacing),
        expanded = isVisible,
        onDismissRequest = onDismissRequest,
    ) {
        DropDownOptionItem(
            textId = R.string.select,
            iconId = R.drawable.marked_icon,
            onClick = selectAllItems
        )
        Divider()
        DropDownOptionItem(
            textId = R.string.unselect_items,
            iconId = R.drawable.unmarked_icon,
            onClick = unSelectAllItems
        )
        Divider()
        DropDownOptionItem(
            textId = R.string.remove_selected_items,
            iconId = R.drawable.trash_icon,
            onClick = onRemoveItems,
            isDeleteMenu = true
        )
    }
}

@Composable
fun DropDownOptionItem(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
    isDeleteMenu: Boolean = false
) {
    val contentColor = if (isDeleteMenu) colorResource(id = R.color.red)
    else colorResource(id = R.color.blue)
    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = modifier.size(18.dp),
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = contentColor
                )
                SpacerWidth(dp = LargeSpacing)
                Text(
                    text = stringResource(id = textId),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    )
                )
            }
            Spacer(modifier = modifier.height(MediumSpacing))
        },
        onClick = onClick
    )
}