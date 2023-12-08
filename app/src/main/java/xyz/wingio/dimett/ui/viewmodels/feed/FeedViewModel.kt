package xyz.wingio.dimett.ui.viewmodels.feed

import androidx.compose.runtime.mutableStateMapOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.repository.MastodonRepository
import xyz.wingio.dimett.rest.dto.post.Post
import xyz.wingio.dimett.rest.utils.ApiResponse
import xyz.wingio.dimett.rest.utils.fold

/**
 * [ViewModel][ScreenModel] used by [xyz.wingio.dimett.ui.screens.feed.FeedTab]
 */
class FeedViewModel(
    private val mastodonRepository: MastodonRepository
) : ScreenModel {

    // Client side modifications (such as favorited status)
    val modifiedPosts = mutableStateMapOf<String, Post>()

    // Used for request pagination (infinite scroll)
    // TODO: Possibly extract logic so that it can be reused with other requests
    val posts = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<String, Post>() {
            override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
                val next = params.key // Should be the last post's id

                return when (val response = mastodonRepository.getFeed(max = next)) {
                    is ApiResponse.Success -> {
                        val nextKey = response.data.lastOrNull()?.id
                        if (next == null) modifiedPosts.clear() // Reset modifications when refreshing

                        LoadResult.Page(
                            data = response.data,
                            nextKey = nextKey,
                            prevKey = next
                        )
                    }

                    is ApiResponse.Failure -> {
                        LoadResult.Error(response.error)
                    }

                    is ApiResponse.Error -> {
                        LoadResult.Error(response.error)
                    }

                    else -> LoadResult.Page(
                        data = emptyList(),
                        nextKey = null,
                        prevKey = next
                    )
                }
            }

            override fun getRefreshKey(state: PagingState<String, Post>): String? =
                state.anchorPosition?.let {
                    state.closestPageToPosition(it)?.prevKey
                }
        }
    }.flow.cachedIn(coroutineScope) // Binds requests to the lifecycle of this ScreenModel

    /**
     * (Un)favorite a post based on whether it was previously favorited
     */
    fun toggleFavorite(id: String, favorited: Boolean) {
        coroutineScope.launch {
            val res = if (favorited) mastodonRepository.unfavoritePost(id) else mastodonRepository.favoritePost(id)
            res.fold(
                success = {
                    modifiedPosts[id] = it
                }
            )
        }
    }

    /**
     * (Un)boost a post based on whether it was previously boosted
     */
    fun toggleBoost(id: String, boosted: Boolean) {
        coroutineScope.launch {
            val res = if (boosted) mastodonRepository.unboostPost(id) else mastodonRepository.boostPost(id)
            res.fold(
                success = {
                    if (boosted)
                        modifiedPosts[it.id] = it
                    else
                        modifiedPosts[it.boosted!!.id] = it.boosted
                }
            )
        }
    }

}