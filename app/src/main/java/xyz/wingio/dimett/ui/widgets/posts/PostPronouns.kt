package xyz.wingio.dimett.ui.widgets.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.wingio.dimett.R
import xyz.wingio.dimett.ast.EmojiSyntakts
import xyz.wingio.dimett.ast.render
import xyz.wingio.dimett.rest.dto.CustomEmoji
import xyz.wingio.dimett.ui.components.Text
import xyz.wingio.dimett.utils.toEmojiMap

/**
 * Displays the users pronouns
 *
 * @param pronouns The users pronouns
 * @param emoji Emoji used in the users profile, possibly used for their pronouns
 * @param modifier The [Modifier] used for this component
 * @param color Content color for the icon and text
 */
@Composable
fun PostPronouns(
    pronouns: String?,
    emoji: List<CustomEmoji>,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current.copy(alpha = 0.5f)
) {
    pronouns?.let {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.5.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Text(
                text = EmojiSyntakts.render(pronouns, emojiMap = emoji.toEmojiMap()),
                style = MaterialTheme.typography.labelSmall,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = stringResource(R.string.cd_pronouns),
                tint = color,
                modifier = Modifier.size(13.dp)
            )
        }
    }
}