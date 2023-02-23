package xyz.wingio.dimett.ui.widgets.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import coil.size.pxOrElse
import xyz.wingio.dimett.R
import xyz.wingio.dimett.rest.dto.PreviewCard
import xyz.wingio.dimett.ui.components.Text

@Composable
fun Card(
    card: PreviewCard
) {
    when (card.type) {
        PreviewCard.Type.LINK -> LinkCard(card)
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkCard(
    card: PreviewCard
) {
    val ctx = LocalContext.current
    val uriHandler = LocalUriHandler.current
    var size by remember {
        mutableStateOf(Size.ORIGINAL)
    }
    val imageRequest = remember {
        ImageRequest.Builder(ctx)
            .error(R.drawable.img_preview_placeholder)
            .placeholder(R.drawable.img_preview_placeholder)
            .data(card.image)
            .crossfade(300)
            .listener { _, result ->
                size = Size(result.drawable.intrinsicWidth, result.drawable.intrinsicHeight)
                println(size)
            }
            .build()
    }
    val isBig = size.width.pxOrElse { 0 } > size.height.pxOrElse { 0 }
    val aspectRatio = size.width.pxOrElse { 0 }.toFloat() / size.height.pxOrElse { 0 }.toFloat()

    ElevatedCard(
        onClick = {
            uriHandler.openUri(card.url)
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (isBig) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 85.dp)
        ) {
            if (!isBig) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(85.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                card.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium,
                        color = LocalContentColor.current.copy(alpha = 0.5f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}