package xyz.wingio.dimett.ui.widgets.attachments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ondev.imageblurkt_lib.AsyncBlurImage
import com.ondev.imageblurkt_lib.IBlurModel
import com.ondev.imageblurkt_lib.R
import xyz.wingio.dimett.rest.dto.post.MediaAttachment

/**
 * Displays an image (with blurhash if supported)
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleImageAttachment(
    attachment: MediaAttachment
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val height = with(LocalDensity.current) {
            ((9f / 16f) * constraints.maxWidth).toDp()
        } // Make sure the image is always 16:9

        Box(
            modifier = Modifier
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(12.dp), clip = true)
                .background(Color.Black)
                .fillMaxWidth()
                .heightIn(max = height)
        ) {
            if (attachment.blurhash != null) {
                val data = IBlurModel(
                    blurHash = attachment.blurhash,
                    imageUrl = attachment.url
                )

                AsyncBlurImage(
                    data = data,
                    contentDescription = attachment.description,
                    contentScale = ContentScale.Crop,
                    notImageFoundRes = R.drawable.no_image,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                val ctx = LocalContext.current
                val model = remember {
                    derivedStateOf {
                        ImageRequest.Builder(ctx)
                            .data(attachment.url)
                            .error(R.drawable.no_image)
                            .crossfade(700)
                            .build()
                    }
                }

                AsyncImage(
                    model = model.value,
                    contentDescription = attachment.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}