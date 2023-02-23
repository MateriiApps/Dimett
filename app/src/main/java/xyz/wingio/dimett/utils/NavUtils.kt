package xyz.wingio.dimett.utils

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.accompanist.pager.ExperimentalPagerApi
import xyz.wingio.dimett.ui.screens.explore.ExploreTab
import xyz.wingio.dimett.ui.screens.feed.FeedTab
import xyz.wingio.dimett.ui.screens.main.LocalPager
import xyz.wingio.dimett.ui.screens.messages.MessagesTab
import xyz.wingio.dimett.ui.screens.notifications.NotificationsTab

enum class RootTab(val tab: Tab) {
    FEED(FeedTab()),
    EXPLORE(ExploreTab()),
    NOTIFICATIONS(NotificationsTab()),
    MESSAGES(MessagesTab()),
}

tailrec fun Navigator.navigate(screen: Screen) {
    return if (parent == null && items.firstOrNull { it.key == screen.key } == null) try {
        push(screen)
    } catch (_: Throwable) {
    }
    else parent!!.navigate(screen)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@SuppressLint("ComposableNaming")
fun Tab.TabOptions(
    @StringRes name: Int,
    icon: ImageVector,
    iconSelected: ImageVector
): TabOptions {
    val pager = LocalPager.currentOrThrow
    val selected = RootTab.values()[pager.currentPage].tab == this

    return TabOptions(
        0u,
        title = stringResource(name),
        icon = rememberVectorPainter(
            if (selected) iconSelected else icon
        )
    )
}