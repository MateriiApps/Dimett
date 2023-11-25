package xyz.wingio.dimett.ast

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.materii.simplerast.core.node.Node
import com.materii.simplerast.core.parser.ParseSpec
import com.materii.simplerast.core.parser.Parser
import com.materii.simplerast.core.parser.Rule
import com.materii.simplerast.core.simple.CoreMarkdownRules
import com.materii.simplerast.code.CodeRules
import xyz.wingio.dimett.ast.nodes.ClickableNode
import xyz.wingio.dimett.ast.nodes.EmojiNode
import xyz.wingio.dimett.ast.nodes.MentionNode
import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext
import xyz.wingio.dimett.utils.EmojiUtils
import java.util.regex.Matcher
import java.util.regex.Pattern

const val urlRegex =
    "https?:\\/\\/([\\w+?]+\\.[\\w+]+)([a-zA-Z0-9\\~\\!\\@\\#\\\$\\%\\^\\&\\*\\(\\)_\\-\\=\\+\\\\\\/\\?\\.\\:\\;\\'\\,]*)?"

val urlRule = object : Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>(
    Pattern.compile("^$urlRegex")
) {
    override fun parse(
        matcher: Matcher,
        parser: Parser<DefaultRenderContext, in Node<DefaultRenderContext>, Any>,
        state: Any
    ): ParseSpec<DefaultRenderContext, Any> {
        val url = matcher.group(0)
        return ParseSpec.createNonterminal(
            ClickableNode(
                text = url,
                tag = "URL",
                annotation = url
            ),
            Unit,
            matcher.start(1),
            matcher.end(1),
        )
    }
}

val hyperlinkRule = object : Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>(
    Pattern.compile("^\\[(.+?)\\]\\(($urlRegex)\\)")
) {
    override fun parse(
        matcher: Matcher,
        parser: Parser<DefaultRenderContext, in Node<DefaultRenderContext>, Any>,
        state: Any
    ): ParseSpec<DefaultRenderContext, Any> {
        val text = matcher.group(1)
        val url = matcher.group(2)
        return ParseSpec.createNonterminal(
            ClickableNode(
                text = text,
                tag = "URL",
                annotation = url
            ),
            Unit,
            matcher.start(1),
            matcher.end(1),
        )
    }
}

val clickableRule = object : Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>(
    Pattern.compile("^\\[(.+?)\\]\\{(.+?)\\}")
) {
    override fun parse(
        matcher: Matcher,
        parser: Parser<DefaultRenderContext, in Node<DefaultRenderContext>, Any>,
        state: Any
    ): ParseSpec<DefaultRenderContext, Any> {
        val text = matcher.group(1)
        val clickAction = matcher.group(2)
        return ParseSpec.createNonterminal(
            ClickableNode(
                text = text,
                tag = "CLICKABLE",
                annotation = clickAction
            ),
            Unit,
            matcher.start(1),
            matcher.end(1),
        )
    }
}

val emojiRule = object : Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>(
    Pattern.compile("^:(.+?):")
) {
    override fun parse(
        matcher: Matcher,
        parser: Parser<DefaultRenderContext, in Node<DefaultRenderContext>, Any>,
        state: Any
    ): ParseSpec<DefaultRenderContext, Any> {
        val emoji = matcher.group(1)
        return ParseSpec.createNonterminal(
            EmojiNode(
                name = emoji,
                custom = true
            ),
            Unit,
            matcher.start(1),
            matcher.end(1),
        )
    }
}

val unicodeEmojiRule = object : Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>(
    Pattern.compile(
        EmojiUtils.regex,
        Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE
    )
) {
    override fun parse(
        matcher: Matcher,
        parser: Parser<DefaultRenderContext, in Node<DefaultRenderContext>, Any>,
        state: Any
    ): ParseSpec<DefaultRenderContext, Any> {
        val emoji = matcher.group(1)
        return ParseSpec.createTerminal(
            EmojiNode(
                name = emoji,
                custom = false
            ),
            Unit
        )
    }
}

val mentionRule = object : Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>(
    Pattern.compile("^@(\\w+?)\\b")
) {
    override fun parse(
        matcher: Matcher,
        parser: Parser<DefaultRenderContext, in Node<DefaultRenderContext>, Any>,
        state: Any
    ): ParseSpec<DefaultRenderContext, Any> {
        val username = matcher.group(1)
        return ParseSpec.createNonterminal(
            MentionNode(username),
            Unit,
            matcher.start(1),
            matcher.end(1),
        )
    }
}

val hashtagRule = object : Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>(
    Pattern.compile("^#(.+?)\\b")
) {
    override fun parse(
        matcher: Matcher,
        parser: Parser<DefaultRenderContext, in Node<DefaultRenderContext>, Any>,
        state: Any
    ): ParseSpec<DefaultRenderContext, Any> {
        val text = matcher.group(0)
        val tag = matcher.group(1)
        return ParseSpec.createNonterminal(
            ClickableNode(
                text = text,
                tag = "CLICKABLE",
                annotation = "hashtag:$tag"
            ),
            Unit,
            matcher.start(1),
            matcher.end(1),
        )
    }
}

val plainTextRule = CoreMarkdownRules.createTextRule<DefaultRenderContext, Any>()
val boldRule = CoreMarkdownRules.createBoldRule<DefaultRenderContext, Any> {
    SpanStyle(fontWeight = FontWeight.Bold)
}
val italicsRule = CoreMarkdownRules.createItalicsRule<DefaultRenderContext, Any> {
    SpanStyle(fontStyle = FontStyle.Italic)
}
val strikethroughRule = CoreMarkdownRules.createStrikethroughRule<DefaultRenderContext, Any> {
    SpanStyle(textDecoration = TextDecoration.LineThrough)
}
val underlineRule = CoreMarkdownRules.createUnderlineRule<DefaultRenderContext, Any> {
    SpanStyle(textDecoration = TextDecoration.Underline)
}

val basicRules = listOf<Rule<DefaultRenderContext, Node<DefaultRenderContext>, Any>>(
    boldRule,
    italicsRule,
    strikethroughRule,
    underlineRule,
    plainTextRule
)