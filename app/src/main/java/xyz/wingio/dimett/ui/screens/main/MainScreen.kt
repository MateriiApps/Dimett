package xyz.wingio.dimett.ui.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.ui.viewmodels.main.MainViewModel
import xyz.wingio.dimett.utils.LocalPagerState
import xyz.wingio.dimett.utils.RootTab

/**
 * Hosts the Home, Explore, Inbox (Notifications), and Profile tabs
 */
class MainScreen : Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun Content() {
        val viewModel: MainViewModel = getScreenModel()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState {
            RootTab.entries.size
        }

        // Navigate to the home tab when the back button is pressed
        BackHandler(
            enabled = pagerState.currentPage != 0 // We don't want to do this on the home tab
        ) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        }

        CompositionLocalProvider(
            LocalPagerState provides pagerState // Lets all children access the state used by the HorizontalPager
        ) {
            Scaffold(
                bottomBar = { TabBar(viewModel) },
                topBar = { TopBar(RootTab.entries[pagerState.currentPage].tab, scrollBehavior) }
            ) { pv ->
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val tab = RootTab.entries[it]

                    Box(
                        Modifier
                            .padding(pv)
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                            .fillMaxSize()
                    ) {
                        tab.tab.Content()
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar(
        tab: Tab,
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeTopAppBar(
            title = { Text(tab.options.title) },
            navigationIcon = {
                IconButton(
                    onClick = { /* TODO: Open some menu */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.action_open_menu)
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun TabBar(
        viewModel: MainViewModel
    ) {
        val pagerState = LocalPagerState.current // This PagerState should be the one used for tabs
        val tab = RootTab.entries[pagerState.currentPage].tab // Gets the currently selected/visible tab
        val coroutineScope = rememberCoroutineScope() // Just required for certain animations

        NavigationBar {
            RootTab.entries.forEach {
                NavigationBarItem(
                    selected = tab == it.tab,
                    onClick = {
                        val page = RootTab.entries.indexOf(it)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    },
                    icon = {
                        if(it == RootTab.PROFILE && viewModel.account != null) {
                            // For the profile tab we want to display the current users avatar
                            AsyncImage(
                                model = viewModel.account!!.avatar,
                                contentDescription = stringResource(
                                    R.string.cd_avatar,
                                    viewModel.account!!.username
                                ),
                                modifier = Modifier
                                    .size(24.dp) // About the same size that the other icons use
                                    .clip(CircleShape)
                            )
                        } else {
                            // All others use an icon that changes depending on whether or not the tab is selected
                            Icon(
                                painter = it.tab.options.icon!!,
                                contentDescription = it.tab.options.title
                            )
                        }
                    },
                    label = { Text(text = it.tab.options.title) }
                )
            }
        }
    }

}