package xyz.wingio.dimett.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.CompactDecimalFormat
import android.os.Handler
import android.os.Looper
import androidx.compose.ui.text.AnnotatedString
import androidx.core.text.parseAsHtml
import org.koin.core.context.GlobalContext
import org.koin.core.qualifier.named
import xyz.wingio.dimett.BuildConfig
import xyz.wingio.dimett.R
import xyz.wingio.dimett.rest.dto.CustomEmoji
import xyz.wingio.dimett.rest.dto.post.Mention
import xyz.wingio.dimett.rest.dto.post.Post
import java.util.Locale
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.firstOrNull
import kotlin.collections.forEach
import kotlin.collections.mutableMapOf
import kotlin.collections.set

/**
 * Convert the html returned from the server into plain text that can be parsed on device
 */
val String.plain: String
    get() = replace("<br> ", "<br>&nbsp;")
        .replace("<br /> ", "<br />&nbsp;")
        .replace("<br/> ", "&nbsp;&nbsp;")
        .replace("  ", "\n")
        .parseAsHtml()
        .trimEnd()
        .toString()

/**
 * Converts the list to a map (shortcode: url)
 */
fun List<CustomEmoji>.toEmojiMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    forEach {
        map[it.shortcode] = it.url
    }
    return map
}

/**
 * Converts the list to a map (username: id)
 */
fun List<Mention>.toMentionMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    forEach {
        map[it.username] = it.id
    }
    return map
}

/**
 * Injects the default [Logger]
 */
fun getLogger(): Logger = GlobalContext.get().get(named("default"))

/**
 * Converts a string to an [AnnotatedString]
 */
fun String.toAnnotatedString() = AnnotatedString(this)

/**
 * Removes the mention for the replied user from the start of the text
 *
 * @param post The post to process
 */
fun processPostContent(post: Post): String {
    val repliedTo = post.mentions.firstOrNull { mention ->
        mention.id == post.userRepliedTo
    }
    return post.content?.plain?.run {
        if (repliedTo != null)
            this
                .replaceFirst("@${repliedTo.username} ", "")
                .replaceFirst("@${repliedTo.acct}", "")
        else
            this
    } ?: ""
}

/**
 * Get the resource id for a raw resource from its name
 *
 * @param resName Name of the raw resource (without the file extension)
 */
@SuppressLint("DiscouragedApi")
fun Context.getResId(resName: String) =
    resources.getIdentifier(resName, "raw", BuildConfig.APPLICATION_ID)

/**
 * Converts a number into a more compact form (1,121 -> 1.1k)
 *
 * @param number The number to format
 */
fun formatNumber(number: Int): String =
    CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT)
        .format(number)

/**
 * Opens the share dialog with the specified [string]
 *
 * @param string The text to share, such as a link
 */
fun Context.shareText(string: String) = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, string)
    startActivity(Intent.createChooser(this, getString(R.string.action_share)))
}

/**
 * Runs the [block] on the main (UI) thread
 */
fun mainThread(block: () -> Unit) {
    Handler(Looper.getMainLooper()).post(block)
}