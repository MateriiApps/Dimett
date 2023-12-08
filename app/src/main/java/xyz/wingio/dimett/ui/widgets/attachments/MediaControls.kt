package xyz.wingio.dimett.ui.widgets.attachments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import xyz.wingio.dimett.R
import kotlin.time.Duration.Companion.seconds

/**
 * Displays controls such as a play/pause button and a seekbar
 *
 * @param player [ExoPlayer] instance getting controlled
 */
@Composable
fun MediaControls(
    player: ExoPlayer
) {
    // Whether or not the user is actively using the seekbar
    var seeking by remember(player) {
        mutableStateOf(false)
    }

    // Whether or not the video/audio is playing
    var playing by remember(player) {
        mutableStateOf(player.isPlaying)
    }

    // The length of the video/audio
    var duration by remember(player) {
        mutableLongStateOf(player.duration)
    }

    // Current position in the video/audio
    var position by remember(player) {
        mutableLongStateOf(player.currentPosition)
    }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            // Updates all our state variables
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                playing = isPlaying
                duration = player.duration
                position = player.currentPosition
            }
        }
        player.addListener(listener)

        onDispose { player.removeListener(listener) } // Remove the listener when the component leaves the scope
    }

    if (playing) {
        LaunchedEffect(Unit) {
            // We kinda have to do this bc ExoPlayer doesn't provide a listener for player position
            while (!seeking) {
                position = player.currentPosition
                delay(1.seconds)
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        IconButton(
            onClick = {
                if (playing)
                    player.pause()
                else {
                    if (player.currentPosition >= player.duration)
                        player.seekTo(0) // Replay the media if its finished playing
                    player.play()
                }
            }
        ) {
            val (icon, cd) = if (playing)
                Icons.Filled.Pause to R.string.cd_pause
            else Icons.Filled.PlayArrow to R.string.cd_play

            Icon(
                imageVector = icon,
                contentDescription = stringResource(cd),
                tint = Color.White
            )
        }

        Slider(
            value = position.toFloat(),
            onValueChange = {
                position = it.toLong()
                seeking = true
            },
            onValueChangeFinished = {
                player.seekTo(position)
                player.play()
                seeking = false
            },
            valueRange = 0f..if (player.duration == C.TIME_UNSET) 0f else player.duration.toFloat(),
            modifier = Modifier.padding(end = 24.dp)
        )
    }
}