package xyz.wingio.dimett.ui.widgets.posts

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.ui.theme.additionalColors
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
            text = { Text(formatNumber(replies)) },
            inactiveIcon = Icons.Outlined.ChatBubbleOutline,
            contentDescription = R.string.cd_reply,
            onClick = onReplyClick,
        )

        PostButton(
            active = boosted,
            text = { Text(formatNumber(boosts)) },
            inactiveIcon = Icons.Outlined.Repeat,
            contentDescription = R.string.cd_boost,
            activeColor = MaterialTheme.additionalColors.boost,
            onClick = onBoostClick
        )

        PostButton(
            active = favorited,
            text = { Text(formatNumber(favorites)) },
            activeIcon = Icons.Filled.Favorite,
            inactiveIcon = Icons.Outlined.FavoriteBorder,
            contentDescription = R.string.cd_favorite,
            activeColor = MaterialTheme.additionalColors.favorite,
            onClick = onFavoriteClick
        )

        Spacer(Modifier.weight(1f))

        PostButton(
            text = { Text(stringResource(R.string.action_share)) },
            inactiveIcon = Icons.Filled.Share,
            contentDescription = R.string.action_share,
            onClick = onShareClick
        )
    }
}

/**
 * Version of [TextButton][androidx.compose.material3.TextButton] with an icon and an active state, used for social actions such as sharing or favoriting
 *
 * @param onClick Called when the button is clicked
 * @param text Label to be displayed in the button
 * @param active Whether or not this button is in the active state (Ex. Post already favorited)
 * @param inactiveIcon The icon to use when this button is in the default inactive state ([active] is `false`)
 * @param activeIcon Icon displayed next to the label when [active] is `true`
 * @param contentDescription Describes the button for screen readers
 * @param inactiveColor Content color used when [active] is `false`
 * @param activeColor Content color used when [active] is `true`
 */
@Composable
fun PostButton(
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    active: Boolean = false,
    inactiveIcon: ImageVector,
    activeIcon: ImageVector = inactiveIcon,
    @StringRes contentDescription: Int,
    inactiveColor: Color = LocalContentColor.current.copy(alpha = 0.6f),
    activeColor: Color = inactiveColor,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = activeColor),
                onClick = onClick
            )
            .padding(vertical = 6.dp, horizontal = 10.dp)
    ) {
        Icon(
            imageVector = if (active) activeIcon else inactiveIcon,
            contentDescription = stringResource(contentDescription),
            tint = if (active) activeColor else inactiveColor,
            modifier = Modifier.size(16.dp)
        )

        Spacer(
            modifier = Modifier.width(ButtonDefaults.IconSpacing)
        )

        ProvideTextStyle(
            MaterialTheme.typography.labelLarge.copy(
                color = if (active) activeColor else inactiveColor,
                fontWeight = if(active) FontWeight.Bold else FontWeight.Medium
            )
        ) {
            text()
        }
    }
}