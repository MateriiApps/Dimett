package xyz.wingio.dimett.ast

import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext
import xyz.wingio.dimett.utils.EmojiUtils
import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.compose.style.appendInlineContent
import xyz.wingio.syntakts.compose.style.toSyntaktsColor
import xyz.wingio.syntakts.style.Style

const val urlRegex =
    "https?:\\/\\/([\\w+?]+\\.[\\w+]+)([a-zA-Z0-9\\~\\!\\@\\#\\\$\\%\\^\\&\\*\\(\\)_\\-\\=\\+\\\\\\/\\?\\.\\:\\;\\'\\,]*)?"

const val hyperlinkRegex =
    "\\[(.+?)\\]\\(($urlRegex)\\)"

const val clickableRegex =
    "\\[(.+?)\\]\\{(.+?)\\}"

const val emojiRegex =
    ":(.+?):"

const val mentionRegex =
    "@(\\S+?)\\b(@(\\S+)\\b)?"

const val hashtagRegex =
    "#(.+?)\\b"

fun Syntakts.Builder<DefaultRenderContext>.addUrlRule() {
    rule(urlRegex) { result, context ->
        appendClickable(
            result.value,
            style = Style(color = context.linkColor.toSyntaktsColor())
        ) {
            context.uriHandler.openUri(result.value)
        }
    }
}

fun Syntakts.Builder<DefaultRenderContext>.addHyperlinkRule() {
    rule(hyperlinkRegex) { result, context ->
        appendClickable(
            result.groupValues[1], // Text
            style = Style(color = context.linkColor.toSyntaktsColor())
        ) {
            context.uriHandler.openUri(result.groupValues[1]) // Url
        }
    }
}

fun Syntakts.Builder<DefaultRenderContext>.addClickableRule() {
    rule(clickableRegex) { result, context ->
        appendClickable(
            result.groupValues[1], // Text
            style = Style(color = context.linkColor.toSyntaktsColor())
        ) {
            context.clickActionHandler(result.groupValues[2]) // Click action
        }
    }
}

fun Syntakts.Builder<DefaultRenderContext>.addEmojiRule() {
    rule(emojiRegex) { result, context ->
        val name = result.groupValues[1]
        val url = context.emojiMap[name]
        if (url == null) {
            append(result.value)
        } else {
            appendInlineContent(
                id = "emote",
                alternateText = url
            )
        }
    }
}

fun Syntakts.Builder<DefaultRenderContext>.addUnicodeEmojiRule() {
    rule(EmojiUtils.regex.toRegex(RegexOption.IGNORE_CASE)) { result, _ ->
        val emoji = result.groupValues[1]
        appendInlineContent(
            id = "emoji",
            alternateText = emoji
        )
    }
}

fun Syntakts.Builder<DefaultRenderContext>.addMentionRule() {
    rule(mentionRegex) { result, context ->
        val mentionUsername = result.groupValues[1]
        val mention = context.mentionMap[mentionUsername]

        if (mention != null) {
            appendClickable(
                "@$mentionUsername", // Text
                style = Style(color = context.linkColor.toSyntaktsColor())
            ) {
                context.clickActionHandler("mention:$mention") // Click action
            }
        } else {
            append(result.value)
        }
    }
}

fun Syntakts.Builder<DefaultRenderContext>.addHashtagRule() {
    rule(hashtagRegex) { result, context ->
        val tag = result.groupValues[1]

        appendClickable(
            result.value, // Text
            style = Style(color = context.linkColor.toSyntaktsColor())
        ) {
            context.clickActionHandler("hashtag:$tag") // Click action
        }
    }
}