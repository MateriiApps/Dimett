package xyz.wingio.dimett.ast

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalUriHandler
import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext
import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.compose.rememberAsyncRendered
import xyz.wingio.syntakts.markdown.addBasicMarkdownRules
import xyz.wingio.syntakts.syntakts

@Composable
fun Syntakts<DefaultRenderContext>.render(
    text: String,
    emojiMap: Map<String, String> = emptyMap(),
    mentionMap: Map<String, String> = emptyMap(),
    actionHandler: (String) -> Unit = {}
) = rememberAsyncRendered(
    text = text,
    context = rememberRenderContext(emojiMap, mentionMap, actionHandler)
)

@Composable
fun rememberRenderContext(
    emojiMap: Map<String, String>,
    mentionMap: Map<String, String>,
    actionHandler: (String) -> Unit = {}
): DefaultRenderContext {
    val uriHandler = LocalUriHandler.current
    val linkColor = MaterialTheme.colorScheme.primary

    return remember(emojiMap, mentionMap, uriHandler, linkColor, actionHandler) {
        DefaultRenderContext(emojiMap, mentionMap, linkColor, uriHandler, actionHandler)
    }
}

val DefaultSyntakts = syntakts {
    addHashtagRule()
    addMentionRule()
    addUrlRule()
    addEmojiRule()
    addUnicodeEmojiRule()
}

val StringSyntakts = syntakts {
    addUrlRule()
    addClickableRule()
    addBasicMarkdownRules()
    addUnicodeEmojiRule()
}

val EmojiSyntakts = syntakts {
    addEmojiRule()
    addUnicodeEmojiRule()
}