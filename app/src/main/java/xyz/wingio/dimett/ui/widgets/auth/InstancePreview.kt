package xyz.wingio.dimett.ui.widgets.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.wingio.dimett.R
import xyz.wingio.dimett.rest.dto.meta.NodeInfo

@Composable
fun InstancePreview(
    url: String,
    nodeInfo: NodeInfo
) {
    val iconSize = 56.dp
    val name = nodeInfo.metadata?.nodeName ?: url

    val icon = when (nodeInfo.software.name) {
        "pleroma" -> painterResource(R.drawable.img_logo_pleroma)
        "akkoma" -> painterResource(R.drawable.img_logo_akkoma)
        "mastodon" -> painterResource(R.drawable.img_logo_mastodon)
        else -> painterResource(R.drawable.img_logo_fediverse)
    }

    Box(
        modifier = Modifier
            .width(350.dp)
            .padding(bottom = iconSize / 2)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = iconSize / 2)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(Modifier.height(16.dp))

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                nodeInfo.metadata?.nodeDescription?.let {
                    if (it.isNotBlank()) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Text(
                    text = "${nodeInfo.software.name} ${nodeInfo.software.version}",
                    style = MaterialTheme.typography.labelMedium,
                    color = LocalContentColor.current.copy(alpha = 0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Image(
            icon,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(iconSize)
                .align(Alignment.TopCenter)
                .shadow(10.dp, CircleShape)
        )
    }
}