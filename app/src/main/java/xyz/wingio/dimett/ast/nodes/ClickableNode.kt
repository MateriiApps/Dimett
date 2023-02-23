package xyz.wingio.dimett.ast.nodes

import androidx.compose.ui.text.SpanStyle
import com.materii.simplerast.core.node.Node
import com.materii.simplerast.core.text.RichTextBuilder
import com.materii.simplerast.core.text.StyleInclusion
import xyz.wingio.dimett.ast.StringAnnotation
import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext

class ClickableNode(val text: String, private val tag: String, private val annotation: String) :
    Node<DefaultRenderContext>() {
    override fun render(builder: RichTextBuilder, renderContext: DefaultRenderContext) {
        val length = builder.length
        builder.append(text)
        builder.setStyle(
            StringAnnotation(tag, annotation),
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