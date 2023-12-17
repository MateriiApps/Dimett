package xyz.wingio.dimett.ui.widgets.posts

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.VpnLock
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import xyz.wingio.dimett.R
import xyz.wingio.dimett.rest.dto.post.Post

/**
 * Displays a relative timestamp along with the posts visibility
 *
 * @param createdAt Timestamp for the posts creation
 * @param visibility The visibility level of the post
 * @param modifier The [Modifier] for this component
 * @param color Content color for the icon and text
 * @param showVisibility Whether or not to show an icon for the posts [visibility]
 */
@Composable
fun PostTimestamp(
    createdAt: Instant,
    visibility: Post.Visibility,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current.copy(alpha = 0.5f),
    showVisibility: Boolean = true,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val (privacyIcon, privacyIconLabelRes) = remember(visibility) {
            when (visibility) {
                Post.Visibility.PUBLIC,
                Post.Visibility.LOCAL -> Icons.Outlined.Public to R.string.cd_privacy_public
                Post.Visibility.PRIVATE -> Icons.Outlined.Lock to R.string.cd_privacy_private
                Post.Visibility.DIRECT -> Icons.Outlined.Mail to R.string.cd_privacy_direct
                Post.Visibility.UNLISTED -> Icons.Outlined.VpnLock to R.string.cd_privacy_unlisted
            }
        }

        val timeString = remember(createdAt) {
            DateUtils.getRelativeTimeSpanString(
                /* time = */ createdAt.toEpochMilliseconds(),
                /* now = */ System.currentTimeMillis(),
                /* minResolution = */ 0L,
                /* flags = */ DateUtils.FORMAT_ABBREV_ALL
            ).toString()
        }

        Text(
            text = timeString,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )

        if (showVisibility) {
            Icon(
                imageVector = privacyIcon,
                contentDescription = stringResource(privacyIconLabelRes),
                tint = color,
                modifier = Modifier.size(13.dp)
            )
        }
    }
}