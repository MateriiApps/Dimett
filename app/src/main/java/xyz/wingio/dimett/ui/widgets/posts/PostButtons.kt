package xyz.wingio.dimett.ui.widgets.posts

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RepeatOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.utils.formatNumber

/**
 * Set of buttons and their interaction counts
 *
 * @param replies Number of replies to the post
 * @param favorites Number of people that favorited this post
 * @param boosts Number of people that boosted this post
 * @param boosted Whether the current user has boosted this post
 * @param favorited Whether the current user has favorited this post
 * @param onReplyClick Called when the reply button is pressed
 * @param onFavoriteClick Called when the favorite button is pressed
 * @param onBoostClick Called when the boost button is pressed
 * @param onShareClick Called when the share button is pressed
 */
@Composable
@OptIn(ExperimentalLayoutApi::class)
fun PostButtons(
    replies: Int,
    favorites: Int,
    boosts: Int,
    boosted: Boolean,
    favorited: Boolean,
    onReplyClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onBoostClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
    ) {
        PostButton(
            icon = Icons.Outlined.ChatBubbleOutline,
            contentDescription = R.string.cd_reply,
            text = { Text(formatNumber(replies)) },
            onClick = onReplyClick
        )
        PostButton(
            icon = if (boosted) Icons.Filled.RepeatOn else Icons.Outlined.Repeat,
            contentDescription = R.string.cd_boost,
            text = { Text(formatNumber(boosts)) },
            onClick = onBoostClick
        )
        PostButton(
            icon = if (favorited) Icons.Filled.Star else Icons.Outlined.StarBorder,
            contentDescription = R.string.cd_favorite,
            text = { Text(formatNumber(favorites)) },
            onClick = onFavoriteClick
        )
        Spacer(Modifier.weight(1f))
        PostButton(
            icon = Icons.Outlined.Share,
            contentDescription = R.string.action_share,
            text = { Text(stringResource(R.string.action_share)) },
            onClick = onShareClick
        )
    }
}

/**
 * Version of [TextButton][androidx.compose.material3.TextButton] with an icon
 *
 * @param icon Icon displayed next to the label
 * @param contentDescription Describes the button for screen readers
 * @param text Label to be displayed in the button
 * @param onClick Called when the button is clicked
 */
@Composable
fun PostButton(
    icon: ImageVector,
    @StringRes contentDescription: Int,
    text: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(contentDescription),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        ProvideTextStyle(MaterialTheme.typography.labelLarge) {
            text()
        }
    }
}