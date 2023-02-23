package xyz.wingio.dimett.ui.widgets.attachments

import android.net.Uri
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.annotation.ExperimentalCoilApi
import xyz.wingio.dimett.rest.dto.post.MediaAttachment
import xyz.wingio.dimett.ui.components.Text

@kotlin.OptIn(ExperimentalCoilApi::class)
@Composable
@OptIn(androidx.media3.common.util.UnstableApi::class)
fun SingleGifAttachment(
    attachment: MediaAttachment
) {
    val context = LocalContext.current
    var playing by remember {
        mutableStateOf(false)
    }

    val player = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultDataSource.Factory(context)
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(attachment.url)))

            setMediaSource(source)
        }
    }

    player.repeatMode = Player.REPEAT_MODE_ONE
    player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val height = with(LocalDensity.current) {
            ((9f / 16f) * constraints.maxWidth).toDp()
        }
        Box(
            modifier = Modifier
                .shadow(3.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .heightIn(max = height)
        ) {
            Box {
                DisposableEffect(
                    AndroidView(factory = {
                        PlayerView(it).apply {
                            hideController()
                            useController = false
                            this.player = player
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT
                            )
                        }
                    })
                ) {
                    onDispose { player.release() }
                }

                if (!playing) {
                    PlayButtonOverlay(
                        previewUrl = attachment.previewUrl ?: "",
                        contentDescription = attachment.description ?: "",
                        onPlay = {
                            player.prepare()
                            player.play()
                            playing = true
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "GIF",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.4f))
                            .padding(vertical = 4.dp, horizontal = 6.dp)
                    )
                }
            }
        }
    }
}