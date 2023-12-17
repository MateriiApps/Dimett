package xyz.wingio.dimett.ui.widgets.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ast.EmojiSyntakts
import xyz.wingio.dimett.ast.render
import xyz.wingio.dimett.ui.components.BadgedItem
import xyz.wingio.dimett.ui.components.Text

/**
 * Displays the avatar along with display name and username for a post's author
 *
 * @param avatarUrl Url pointing to the authors avatar
 * @param displayName Authors display name
 * @param acct The username of the author (@user or @user@example.social)
 * @param emojis The emojis present in the display name
 * @param bot Whether or not the author is an automated account
 * @param onAvatarClick Callback for when the avatar is clicked
 */
@Composable
fun PostAuthor(
    avatarUrl: String,
    displayName: String,
    acct: String,
    emojis: Map<String, String>,
    bot: Boolean,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        BadgedItem(badge = {
            if (bot) {
                Icon(
                    imageVector = Icons.Outlined.SmartToy,
                    contentDescription = stringResource(R.string.cd_bot),
                    modifier = Modifier.size(14.dp)
                )
            }
        }) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = stringResource(R.string.cd_avatar, displayName),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onAvatarClick)
            )
        }

        Column {
            Text(
                text = EmojiSyntakts.render(displayName, emojis),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = "@$acct",
                style = MaterialTheme.typography.labelMedium,
                color = LocalContentColor.current.copy(alpha = 0.5f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}