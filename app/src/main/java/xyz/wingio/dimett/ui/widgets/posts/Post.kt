package xyz.wingio.dimett.ui.widgets.posts

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ast.DefaultSyntakts
import xyz.wingio.dimett.ast.EmojiSyntakts
import xyz.wingio.dimett.ast.render
import xyz.wingio.dimett.rest.dto.post.Post
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.ui.theme.additionalColors
import xyz.wingio.dimett.ui.widgets.attachments.Attachments
import xyz.wingio.dimett.utils.getString
import xyz.wingio.dimett.utils.processPostContent
import xyz.wingio.dimett.utils.shareText
import xyz.wingio.dimett.utils.toEmojiMap
import xyz.wingio.dimett.utils.toMentionMap

/**
 * Post displayed in a feed
 *
 * @param post Information about the post
 * @param onAvatarClick Called when the authors avatar is clicked
 * @param onMentionClick Called when any mention (@user or @user@domain) is clicked
 * @param onHashtagClick Called when a hashtag (#tag) is clicked
 * @param onReplyClick Called when the reply button is pressed
 * @param onFavoriteClick Called when the favorite button is pressed
 * @param onBoostClick Called when the boost button is pressed
 * @param onVotedInPoll Called when the user submits their selection in a poll
 */
@Composable
@Suppress("LocalVariableName")
fun Post(
    post: Post,
    onAvatarClick: (String) -> Unit = {},
    onMentionClick: (String) -> Unit = {},
    onHashtagClick: (String) -> Unit = {},
    onReplyClick: (String) -> Unit = {},
    onFavoriteClick: (String) -> Unit = {},
    onBoostClick: (String) -> Unit = {},
    onVotedInPoll: (String, List<Int>) -> Unit = { _, _ -> }
) {
    val ctx = LocalContext.current
    val _post = post.boosted ?: post // The actually displayed post, not the same if its a boost
    val timeString = remember(post.createdAt) {
        DateUtils.getRelativeTimeSpanString(
            /* time = */ post.createdAt.toEpochMilliseconds(),
            /* now = */ System.currentTimeMillis(),
            /* minResolution = */ 0L,
            /* flags = */ DateUtils.FORMAT_ABBREV_ALL
        ).toString()
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 1.dp,
        shadowElevation = 3.dp
    ) {
        Text(
            text = timeString,
            style = MaterialTheme.typography.labelSmall,
            color = LocalContentColor.current.copy(alpha = 0.5f),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (post.boosted != null) {
                val str = stringResource(R.string.post_user_boosted, post.author.displayName)

                SocialContext(
                    icon = Icons.Outlined.Repeat,
                    iconDescription = R.string.cd_boosted,
                    iconColor = MaterialTheme.additionalColors.boost,
                    text = EmojiSyntakts.render(str, post.author.emojis.toEmojiMap(), emptyMap())
                )
            }

            PostAuthor(
                avatarUrl = _post.author.avatar,
                displayName = _post.author.displayName,
                acct = _post.author.acct,
                emojis = _post.author.emojis.toEmojiMap(),
                bot = _post.author.bot,
                onAvatarClick = { onAvatarClick(_post.author.id) }
            )

            _post.userRepliedTo?.let { repliedTo ->
                _post.mentions.firstOrNull {
                    it.id == repliedTo
                }?.let { mention ->
                    Text(
                        text = getString(R.string.post_replying_to, mention.username) {
                            if (it == "onUserClick") onMentionClick(mention.id)
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = LocalContentColor.current.copy(alpha = 0.5f)
                    )
                }
            }

            _post.content?.let {
                val content = processPostContent(_post)
                if (content.isNotEmpty()) {
                    Text(
                        text = DefaultSyntakts.render(
                            content,
                            _post.emojis.toEmojiMap(),
                            _post.mentions.toMentionMap()
                        ) { annotation ->
                            val (key, value) = annotation.split(':')
                            when (key) {
                                "mention" -> onMentionClick(value)
                                "hashtag" -> onHashtagClick(value)
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (_post.media.isNotEmpty()) {
                Attachments(
                    attachments = _post.media
                )
            }

            _post.poll?.let {
                Poll(
                    poll = it,
                    onVote = onVotedInPoll
                )
            }

            _post.card?.let {
                if (_post.media.isEmpty()) { // We don't want to show both the media and card at the same time, too big
                    Card(
                        card = it
                    )
                }
            }

            PostButtons(
                replies = _post.replies,
                favorites = _post.favorites,
                boosts = _post.boosts,
                boosted = _post.hasBoosted ?: false,
                favorited = _post.favorited ?: false,
                onReplyClick = { onReplyClick(_post.id) },
                onFavoriteClick = { onFavoriteClick(_post.id) },
                onBoostClick = { onBoostClick(_post.id) },
                onShareClick = { ctx.shareText(_post.uri) }
            )
        }
    }
}
