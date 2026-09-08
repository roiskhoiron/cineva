package io.codingskuy.cineva

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import io.codingskuy.cineva.di.createAppContainer
import io.codingskuy.cineva.presentation.ui.CinevaApp

@Composable
@Preview
fun App() {
    val container = remember { createAppContainer() }
    CinevaApp(container)
}