package com.example.composetaskerapp.cammon.extensions

import androidx.compose.ui.graphics.Color

fun String.convertToColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
