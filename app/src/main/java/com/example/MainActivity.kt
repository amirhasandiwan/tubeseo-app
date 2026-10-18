package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AppBottomBar
import com.example.ui.components.AppTopBar
import com.example.ui.screens.GeneratorScreen
import com.example.ui.screens.ResultsScreen
import com.example.ui.screens.SavedProjectsScreen
import com.example.ui.screens.SeoToolsScreen
import com.example.ui.theme.SurfaceDark0
import com.example.ui.theme.TubeSeoTheme
import com.example.ui.viewmodel.MainNavigationTab
import com.example.ui.viewmodel.TubeSeoViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TubeSeoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TubeSeoTheme {
                TubeSeoApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TubeSeoApp(viewModel: TubeSeoViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val savedProjects by viewModel.savedProjects.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle back button: return to generator tab if on secondary tab
    BackHandler(enabled = uiState.activeTab != MainNavigationTab.GENERATOR) {
        viewModel.onTabSelected(MainNavigationTab.GENERATOR)
    }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                hasApiKey = viewModel.hasApiKeyConfigured()
            )
        },
        bottomBar = {
            AppBottomBar(
                currentTab = uiState.activeTab,
                onTabSelected = { tab -> viewModel.onTabSelected(tab) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = SurfaceDark0
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(SurfaceDark0)
        ) {
            when (uiState.activeTab) {
                MainNavigationTab.GENERATOR -> GeneratorScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                MainNavigationTab.RESULTS -> ResultsScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                MainNavigationTab.SAVED -> SavedProjectsScreen(
                    savedProjects = savedProjects,
                    viewModel = viewModel
                )
                MainNavigationTab.TOOLS -> SeoToolsScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }
        }
    }
}
