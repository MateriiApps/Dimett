package xyz.wingio.dimett.ui.widgets.attachments

import android.net.Uri
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import xyz.wingio.dimett.rest.dto.post.MediaAttachment

/**
 * Displays a basic video player
 */
@Composable
@OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoAttachment(attachment: MediaAttachment) {
    val context = LocalContext.current

    // Whether or not the MediaControls component is visible
    var showControls by remember {
        mutableStateOf(false)
    }

    // Whether or not the video is currently loading
    var loading by remember {
        mutableStateOf(false)
    }

    val player = remember(context, attachment) {
        ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultDataSource.Factory(context)
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(attachment.url)))

            setMediaSource(source)
        }
    }
    var playing by remember(player) {
        mutableStateOf(player.isPlaying)
    }

    player.playWhenReady = true
    player.repeatMode = Player.REPEAT_MODE_OFF
    player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    player.addListener(object : Player.Listener {
        override fun onIsLoadingChanged(isLoading: Boolean) {
            loading = isLoading
        }
    })

    Box(
        modifier = Modifier
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    ) {
        DisposableEffect(
            AndroidView(factory = {
                PlayerView(it).apply {
                    hideController()
                    useController = false
                    this.player = player
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    setOnClickListener {
                        showControls = !showControls
                    }
                }
            })
        ) {
            onDispose { player.release() }
        }

        if (loading) CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )

        AnimatedVisibility(
            visible = showControls,
            enter = slideInVertically(
                initialOffsetY = { it * 2 }
            ),
            exit = slideOutVertically(
                targetOffsetY = { it * 2 }
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
        ) {
            MediaControls(player)
        }

        if (!playing) {
            PlayButtonOverlay(
                previewUrl = attachment.previewUrl ?: "",
                contentDescription = attachment.description ?: "",
                onPlay = {
                    player.prepare()
                    player.playWhenReady = true
                    playing = true
                }
            )
        }
    }
}