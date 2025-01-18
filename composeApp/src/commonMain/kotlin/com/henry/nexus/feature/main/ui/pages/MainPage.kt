package com.henry.nexus.feature.main.ui.pages

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.henry.nexus.feature.main.util.Router
import com.henry.nexus.feature.main.util.RouterAlternative
import com.henry.nexus.core.components.BottomNavBar
import com.henry.nexus.core.components.TabItem
import com.henry.nexus.feature.main.ui.viewmodel.MainViewModel
import nexus.composeapp.generated.resources.Res
import nexus.composeapp.generated.resources.app_bar_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainPage(viewModel: MainViewModel = koinViewModel()) {
    val trade = stringResource(Res.string.app_bar_title)
    val tabItems by viewModel.tabItems.collectAsState()
    var currentRoute by remember { mutableStateOf(Router.Route.HOME) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(trade) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        bottomBar = {
            BottomNavBar(
                tabItems = tabItems,
                initTab = currentRoute,
                onNavigate = { tabItem: TabItem -> currentRoute = tabItem.route }
            )
        }
    ) { paddingValues ->
        RouterAlternative.buildComposeRoute(currentRoute, paddingValues)
    }
}
