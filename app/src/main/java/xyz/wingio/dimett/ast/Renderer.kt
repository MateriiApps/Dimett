package xyz.wingio.dimett.ast

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.materii.simplerast.core.node.Node
import com.materii.simplerast.core.parser.Parser
import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext

object Renderer {
    private val stringParser = createStringParser()
    private val regularParser = createRegularParser()

    private fun Parser<DefaultRenderContext, Node<DefaultRenderContext>, Any>.render(
        text: String,
        renderContext: DefaultRenderContext
    ) = AnnotatedBuilder("").apply {
        for (node in parse(
            text
                .replace("\uD83C\uDFF3\u200D⚧", "\uD83C\uDFF3️\u200D⚧️") // trans flag fix
                .replace(
                    "\uD83C\uDFF3\u200D\uD83C\uDF08",
                    "\uD83C\uDFF3️\u200D\uD83C\uDF08"
                ), // pride flag fix
            ""
        ))
            node.render(this, renderContext)
    }

    private fun createStringParser() =
        Parser<DefaultRenderContext, Node<DefaultRenderContext>, Any>(false).apply {
//            addRule(hyperlinkRule)
            addRule(urlRule)
            addRule(clickableRule)
            addRule(unicodeEmojiRule)
            addRules(basicRules)
        }

    private fun createRegularParser() =
        Parser<DefaultRenderContext, Node<DefaultRenderContext>, Any>(false).apply {
            addRule(hashtagRule)
            addRule(mentionRule)
            addRule(urlRule)
            addRule(emojiRule)
            addRule(unicodeEmojiRule)
            addRule(plainTextRule)
        }

    @Composable
    fun renderString(
        text: String,
        renderContext: DefaultRenderContext = DefaultRenderContext.BLANK.copy(linkColor = MaterialTheme.colorScheme.secondary)
    ) =
        stringParser.render(text, renderContext)

    @Composable
    fun render(text: String, emojiMap: Map<String, String>, mentionMap: Map<String, String>) =
        regularParser.render(
            text,
            DefaultRenderContext(emojiMap, mentionMap, MaterialTheme.colorScheme.secondary)
        )
}