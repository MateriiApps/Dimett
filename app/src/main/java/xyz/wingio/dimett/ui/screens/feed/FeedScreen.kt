package xyz.wingio.dimett.ui.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.materii.pullrefresh.pullRefresh
import dev.materii.pullrefresh.rememberPullRefreshState
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.RefreshIndicator
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.ui.viewmodels.feed.FeedViewModel
import xyz.wingio.dimett.ui.widgets.posts.Post
import xyz.wingio.dimett.utils.TabOptions
import xyz.wingio.dimett.utils.getString

/**
 * Shows the home timeline for the current user
 */
class FeedTab : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            R.string.title_home,
            icon = Icons.Outlined.Home,
            iconSelected = Icons.Filled.Home
        )

    @Composable
    override fun Content(){
        val viewModel: FeedViewModel = getScreenModel()
        val posts = viewModel.posts.collectAsLazyPagingItems()
        val refreshing = posts.loadState.refresh == LoadState.Loading
        val pullRefreshState =
            rememberPullRefreshState(refreshing = refreshing, onRefresh = { posts.refresh() })

        // UI has to be wrapped in a Box in order to display the RefreshIndicator
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if (posts.itemCount == 0 && !refreshing) {
                    // Display this when no posts could be loaded
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_thinking),
                                contentDescription = stringResource(R.string.cd_thinking_emoji),
                                modifier = Modifier.size(120.dp)
                            )
                            Text(
                                text = getString(R.string.msg_no_content),
                                style = MaterialTheme.typography.labelLarge,
                                color = LocalContentColor.current.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
                items(
                    count = posts.itemCount,
                    key = posts.itemKey(),
                    contentType = posts.itemContentType()
                ) { post ->
                    posts[post]?.let {
                        val realPost = viewModel.modifiedPosts[it.id] ?: it // A version of the post with local changes (such as boost or favorited status)
                        Post(
                            post = realPost,
                            onAvatarClick = { authorId ->

                            },
                            onReplyClick = { postId ->

                            },
                            onBoostClick = { postId ->
                                viewModel.toggleBoost(postId, realPost.hasBoosted ?: false, posts)
                            },
                            onFavoriteClick = { postId ->
                                viewModel.toggleFavorite(postId, realPost.favorited ?: false)
                            },
                            onMentionClick = { userId ->

                            },
                            onHashtagClick = { hashtag ->

                            },
                            onVotedInPoll = { pollId, options ->

                            }
                        )
                    }
                }
                if (posts.loadState.append is LoadState.Error) {
                    // Display at the bottom of the feed when receiving an error while trying to load more
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Button(onClick = { posts.retry() }) {
                                Text(text = getString(string = R.string.action_retry))
                            }
                        }
                    }
                }
                if (posts.loadState.append == LoadState.Loading) {
                    // Show a little loading indicator if the user reaches the bottom before we could paginate
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            RefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState
            )
        }
    }
}