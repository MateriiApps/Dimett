package xyz.wingio.dimett.ui.widgets.attachments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PlayButtonOverlay(
    previewUrl: String,
    contentDescription: String,
    onPlay: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onPlay
            )
    ) {
        AsyncImage(
            model = previewUrl,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        )
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(5.dp)
        )
    }
}