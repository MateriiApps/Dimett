package xyz.wingio.dimett.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.core.text.parseAsHtml
import org.koin.androidx.compose.get
import org.koin.core.qualifier.named
import xyz.wingio.dimett.BuildConfig
import xyz.wingio.dimett.rest.dto.CustomEmoji
import xyz.wingio.dimett.rest.dto.post.Mention
import xyz.wingio.dimett.rest.dto.post.Post

val String.plain: String
    get() = replace("<br> ", "<br>&nbsp;")
        .replace("<br /> ", "<br />&nbsp;")
        .replace("<br/> ", "&nbsp;&nbsp;")
        .replace("  ", "\n")
        .parseAsHtml()
        .trimEnd()
        .toString()

fun List<CustomEmoji>.toEmojiMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    forEach {
        map[it.shortcode] = it.url
    }
    return map
}

fun List<Mention>.toMentionMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    forEach {
        map[it.username] = it.id
    }
    return map
}

@Composable
fun getLogger(): Logger = get(named("default"))

fun String.toAnnotatedString() = AnnotatedString(this)

fun processPostContent(post: Post): String {
    val repliedTo = post.mentions.firstOrNull { mention ->
        mention.id == post.userRepliedTo
    }
    return post.content?.plain?.run {
        if (repliedTo != null) replaceFirst("@${repliedTo.username} ", "") else this
    } ?: ""
}

fun Context.getResId(emojiCode: String) =
    resources.getIdentifier(emojiCode, "raw", BuildConfig.APPLICATION_ID)