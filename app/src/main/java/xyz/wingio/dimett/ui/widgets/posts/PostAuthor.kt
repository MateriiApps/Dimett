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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ast.EmojiSyntakts
import xyz.wingio.dimett.ast.render
import xyz.wingio.dimett.ui.components.BadgedItem
import xyz.wingio.dimett.ui.components.Text

@Composable
fun PostAuthor(
    avatarUrl: String,
    displayName: String,
    acct: String,
    emojis: Map<String, String>,
    bot: Boolean,
    onAvatarClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                text = EmojiSyntakts.render(displayName, emojis, emptyMap()),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "@$acct",
                style = MaterialTheme.typography.labelMedium,
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
        }
    }
}