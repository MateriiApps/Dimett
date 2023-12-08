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

/**
 * Renders a formatted version of [text]
 *
 * @param text Text to parse and format
 * @param emojiMap Used to insert custom emoji into the text
 * @param mentionMap All the users mentioned in the post
 * @param actionHandler Callback that is passed the actions name
 */
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

/**
 * Creates a remembered version of [DefaultRenderContext]
 *
 * @see DefaultRenderContext
 */
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

/**
 * Contains all the rules for rendering post content
 */
val DefaultSyntakts = syntakts {
    addHashtagRule()
    addMentionRule()
    addUrlRule()
    addEmojiRule()
    addUnicodeEmojiRule()
}

/**
 * Contains the rules for rendering styled string resources
 */
val StringSyntakts = syntakts {
    addUrlRule()
    addClickableRule()
    addBasicMarkdownRules()
    addUnicodeEmojiRule()
}

/**
 * Only contains rules necessary for emotes and Twemoji
 */
val EmojiSyntakts = syntakts {
    addEmojiRule()
    addUnicodeEmojiRule()
}