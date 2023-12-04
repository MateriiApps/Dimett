package xyz.wingio.dimett.ast.rendercontext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.UriHandler

data class DefaultRenderContext(
    val emojiMap: Map<String, String>,
    val mentionMap: Map<String, String>,
    val linkColor: Color,
    val uriHandler: UriHandler,
    val clickActionHandler: (String) -> Unit
)