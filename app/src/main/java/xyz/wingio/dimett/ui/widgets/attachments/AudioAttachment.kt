package xyz.wingio.dimett.ui.widgets.attachments

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import xyz.wingio.dimett.rest.dto.post.MediaAttachment

/**
 * For audio attachments we just use the [MediaControls]
 */
// TODO: Make more distinct player for audio
@Composable
@OptIn(UnstableApi::class)
fun AudioAttachment(
    attachment: MediaAttachment
) {
    val context = LocalContext.current
    val player = remember(context, attachment) {
        val tSelector = DefaultTrackSelector(context)
        ExoPlayer.Builder(context)
            .setTrackSelector(tSelector)
            .build()
            .apply {
                val dataSourceFactory = DefaultDataSource.Factory(context)
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(attachment.url)))

                setMediaSource(source)
                prepare()
            }
    }

    player.playWhenReady = false
    player.repeatMode = Player.REPEAT_MODE_OFF

    DisposableEffect(
        MediaControls(player)
    ) {
        onDispose {
            player.release()
        }
    }
}