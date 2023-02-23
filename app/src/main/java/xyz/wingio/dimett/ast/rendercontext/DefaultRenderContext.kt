package xyz.wingio.dimett.ast.rendercontext

import androidx.compose.ui.graphics.Color

data class DefaultRenderContext(
    val emojiMap: Map<String, String>,
    val mentionMap: Map<String, String>,
    val linkColor: Color
) {
    companion object {
        val BLANK = DefaultRenderContext(
            emojiMap = emptyMap(),
            mentionMap = emptyMap(),
            linkColor = Color.Blue
        )
    }
}