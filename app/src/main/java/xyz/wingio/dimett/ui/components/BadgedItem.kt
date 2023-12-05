package xyz.wingio.dimett.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Overlays a [badge] component on top of some [content] with a cutout
 *
 * @param badge The component to display over the content
 * @param modifier Modifiers for the BadgedItem
 * @param badgeAlignment How to align the badge relative to the content
 */
// Credit to Xinto (https://github.com/X1nto)
@Composable
fun BadgedItem(
    badge: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    badgeAlignment: Alignment = Alignment.BottomEnd,
    content: @Composable (() -> Unit)
) {
    Box(modifier) {
        content()
        if (badge != null) {
            Surface(
                modifier = Modifier
                    .align(badgeAlignment)
                    .absoluteOffset(3.dp, 3.dp),
                shape = CircleShape
            ) {
                Box(modifier = Modifier.padding(3.dp)) {
                    badge()
                }
            }
        }
    }
}