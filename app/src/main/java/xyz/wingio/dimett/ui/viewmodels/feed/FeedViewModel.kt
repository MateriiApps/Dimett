package xyz.wingio.dimett.ui.viewmodels.feed

import androidx.compose.runtime.mutableStateMapOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.repository.MastodonRepository
import xyz.wingio.dimett.rest.dto.post.Post
import xyz.wingio.dimett.rest.utils.ApiResponse
import xyz.wingio.dimett.rest.utils.fold

class FeedViewModel(
    private val repo: MastodonRepository
) : ScreenModel {

    val modifiedPosts = mutableStateMapOf<String, Post>()

    val posts = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<String, Post>() {
            override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
                val next = params.key

                return when (val response = repo.getFeed(max = next)) {
                    is ApiResponse.Success -> {
                        val nextKey = response.data.lastOrNull()?.id
                        if (next == null) modifiedPosts.clear()

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
    }.flow.cachedIn(coroutineScope)

    fun toggleFavorite(id: String, favorited: Boolean) {
        coroutineScope.launch {
            val res = if (favorited) repo.unfavoritePost(id) else repo.favoritePost(id)
            res.fold(
                success = {
                    modifiedPosts[id] = it
                }
            )
        }
    }

    fun toggleBoost(id: String, boosted: Boolean, items: LazyPagingItems<Post>) {
        coroutineScope.launch {
            val res = if (boosted) repo.unboostPost(id) else repo.boostPost(id)
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