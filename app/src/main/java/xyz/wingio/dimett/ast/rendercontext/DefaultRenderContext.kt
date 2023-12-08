package xyz.wingio.dimett.ast.rendercontext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.UriHandler

data class DefaultRenderContext(
    val emojiMap: Map<String, String>, // shortcode: url
    val mentionMap: Map<String, String>, // username to id
    val linkColor: Color,
    val uriHandler: UriHandler,
    val clickActionHandler: (String) -> Unit // Is passed the action name (Ex. onUserClick)
)