package xyz.wingio.dimett.ui.widgets.attachments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPagerIndicator
import xyz.wingio.dimett.rest.dto.post.MediaAttachment

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Attachments(
    attachments: List<MediaAttachment>
) {
    if (attachments.size == 1) {
        SingleAttachment(
            attachment = attachments.first()
        )
    } else if (attachments.size > 1) {
        val pagerState = rememberPagerState {
            attachments.size
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            HorizontalPager(
                state = pagerState
            ) {
                SingleAttachment(attachment = attachments[it])
            }
            Box(modifier = Modifier.padding(12.dp)) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = pagerState.pageCount,
                    activeColor = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun SingleAttachment(
    attachment: MediaAttachment
) {
    when (attachment.type) {
        MediaAttachment.Type.UNKNOWN -> {}
        MediaAttachment.Type.IMAGE -> SingleImageAttachment(attachment)
        MediaAttachment.Type.GIFV -> SingleGifAttachment(attachment)
        MediaAttachment.Type.VIDEO -> VideoAttachment(attachment)
        MediaAttachment.Type.AUDIO -> AudioAttachment(attachment)
    }
}