package xyz.wingio.dimett.ui.widgets.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val name = nodeInfo.metadata?.nodeName ?: url

    val icon = when (nodeInfo.software.name) {
        "pleroma" -> painterResource(R.drawable.img_logo_pleroma)
        "akkoma" -> painterResource(R.drawable.img_logo_akkoma)
        "mastodon" -> painterResource(R.drawable.img_logo_mastodon)
        else -> painterResource(R.drawable.img_logo_fediverse)
    }

    ElevatedCard(
        modifier = Modifier.width(350.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = icon,
                contentDescription = nodeInfo.software.name,
                modifier = Modifier
                    .size(55.dp)
                    .shadow(2.dp, CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
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

    }
}