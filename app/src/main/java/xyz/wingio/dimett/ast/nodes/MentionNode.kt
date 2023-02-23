package xyz.wingio.dimett.ast.nodes

import androidx.compose.ui.text.SpanStyle
import com.materii.simplerast.core.node.Node
import com.materii.simplerast.core.text.RichTextBuilder
import com.materii.simplerast.core.text.StyleInclusion
import xyz.wingio.dimett.ast.StringAnnotation
import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext

class MentionNode(private val username: String) : Node<DefaultRenderContext>() {

    override fun render(builder: RichTextBuilder, renderContext: DefaultRenderContext) {
        val length = builder.length
        val mention = renderContext.mentionMap[username]
        builder.append("@$username")
        if (mention != null) {
            builder.setStyle(
                StringAnnotation("CLICKABLE", "mention:$mention"),
                length,
                builder.length,
                StyleInclusion.InclusiveInclusive
            )
            builder.setStyle(
                SpanStyle(
                    color = renderContext.linkColor
                ),
                length,
                builder.length,
                StyleInclusion.InclusiveInclusive
            )
        }
    }

}