package xyz.wingio.dimett.ast.nodes

import com.materii.simplerast.core.node.Node
import com.materii.simplerast.core.text.RichTextBuilder
import com.materii.simplerast.core.text.StyleInclusion
import xyz.wingio.dimett.ast.StringAnnotation
import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext

class EmojiNode(val name: String, private val custom: Boolean) : Node<DefaultRenderContext>() {
    override fun render(builder: RichTextBuilder, renderContext: DefaultRenderContext) {
        val length = builder.length
        val url = renderContext.emojiMap[name]
        if (url != null || !custom) {
            builder.append(if (custom) url!! else name)
            builder.setStyle(
                StringAnnotation(
                    "androidx.compose.foundation.text.inlineContent",
                    if (custom) "emote" else "emoji"
                ),
                start = length,
                end = builder.length,
                inclusion = StyleInclusion.InclusiveInclusive
            )
        } else {
            builder.append(":$name:")
        }

    }
}