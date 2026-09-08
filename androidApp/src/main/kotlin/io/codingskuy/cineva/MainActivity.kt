package io.codingskuy.cineva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Init SQLDelight driver context for Fase 3 LocalDataSource
        io.codingskuy.cineva.data.datasources.local.initDatabaseContext(this)

        setContent {
            // TODO Fase 5: switch to CinevaApp(AppContainer(...)) when DI ready
            // CinevaApp(AppContainer(DatabaseDriverFactory(), apiKey = ""))
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}