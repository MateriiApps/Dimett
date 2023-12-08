package xyz.wingio.dimett.ui.widgets.posts

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import xyz.wingio.dimett.ui.components.Text

/**
 * Displays some additional information about a post
 */
@Composable
fun PostInfoBar(
    icon: ImageVector,
    @StringRes iconDescription: Int,
    text: AnnotatedString
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            Modifier.width(10.dp) // Push to the side a little bit to line the space up with the avatar in a post
        )
        Icon(
            imageVector = icon,
            contentDescription = stringResource(iconDescription),
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}