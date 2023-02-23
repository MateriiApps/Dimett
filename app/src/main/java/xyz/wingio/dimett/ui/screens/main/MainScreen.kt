package xyz.wingio.dimett.ui.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.ui.viewmodels.main.MainViewModel
import xyz.wingio.dimett.utils.RootTab

class MainScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
    private fun Screen() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        BackHandler(
            enabled = pagerState.currentPage != 0
        ) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        }

        CompositionLocalProvider(
            LocalPager provides pagerState
        ) {
            Scaffold(
                bottomBar = { TabBar(pagerState) },
                topBar = { TopBar(RootTab.values()[pagerState.currentPage].tab, scrollBehavior) }
            ) { pv ->
                HorizontalPager(
                    count = RootTab.values().size,
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val tab = RootTab.values()[it]

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
        scrollBehavior: TopAppBarScrollBehavior,
        viewModel: MainViewModel = getScreenModel()
    ) {
        LargeTopAppBar(
            title = { Text(tab.options.title) },
            navigationIcon = {
                Row {
                    Spacer(Modifier.width(8.dp))
                    AsyncImage(
                        model = viewModel.account?.avatar,
                        contentDescription = stringResource(
                            R.string.cd_avatar,
                            viewModel.account?.username ?: ""
                        ),
                        modifier = Modifier
                            .size(33.dp)
                            .clip(CircleShape)
                            .clickable { }
                    )
                    Spacer(Modifier.width(12.dp))
                }

            },
            scrollBehavior = scrollBehavior
        )
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun TabBar(
        pagerState: PagerState
    ) {
        val tab = RootTab.values()[pagerState.currentPage].tab
        val coroutineScope = rememberCoroutineScope()

        NavigationBar {
            RootTab.values().forEach {
                NavigationBarItem(
                    selected = tab == it.tab,
                    onClick = {
                        val page = RootTab.values().indexOf(it)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    },
                    icon = {
                        Icon(
                            painter = it.tab.options.icon!!,
                            contentDescription = it.tab.options.title
                        )
                    },
                    label = { Text(text = it.tab.options.title) }
                )
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
val LocalPager = compositionLocalOf<PagerState?> { null }