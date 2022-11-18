package com.edwin.countriesapp.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = backgroundColor,
        content = content
    )
}