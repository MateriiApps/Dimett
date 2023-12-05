package xyz.wingio.dimett.utils

import android.graphics.drawable.BitmapDrawable
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import xyz.wingio.dimett.ast.StringSyntakts
import xyz.wingio.dimett.ast.rendercontext.DefaultRenderContext
import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.compose.rememberRendered

/**
 * Required in order to support custom emotes and Twemoji
 *
 * @param textStyle Used for calculating the size of the emoji
 */
@Composable
fun inlineContent(
    textStyle: TextStyle = LocalTextStyle.current
): Map<String, InlineTextContent> {
    val emoteSize = remember(textStyle) { (textStyle.fontSize.value + 2f).sp }
    val ctx = LocalContext.current

    return remember(emoteSize, ctx) {
        mapOf(
            "emote" to InlineTextContent(
                placeholder = Placeholder(
                    width = emoteSize,
                    height = (emoteSize.value - 2f).sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                ),
            ) { emoteUrl ->
                AsyncImage(
                    model = emoteUrl,
                    contentDescription = null,
                    modifier = Modifier.size(emoteSize.value.dp)
                )
            },

            "emoji" to InlineTextContent(
                placeholder = Placeholder(
                    width = emoteSize,
                    height = (emoteSize.value - 2f).sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                ),
            ) { emoji ->
                val emojiImage = BitmapDrawable(EmojiUtils.emojis[emoji]?.let {
                    ctx.getResId(it)
                }?.let { ctx.resources.openRawResource(it) }).bitmap.asImageBitmap()

                Image(
                    bitmap = emojiImage,
                    contentDescription = null,
                    modifier = Modifier.size(emoteSize.value.dp)
                )
            },
        )
    }
}

/**
 * Retrieves a string resource and formats it
 *
 * @param string Resource id for the desired string
 * @param args Formatting arguments
 * ---
 * Ex: "%1$s has favorited your post" with user.username
 * @param syntakts The [Syntakts] instance used to render the text
 * @param actionHandler Ran whenever any text is clicked
 */
@Composable
fun getString(
    @StringRes string: Int,
    vararg args: Any,
    syntakts: Syntakts<DefaultRenderContext> = StringSyntakts,
    actionHandler: (actionName: String) -> Unit = {}
): AnnotatedString {
    val _string = stringResource(string, *args)
    return syntakts.rememberRendered(
        text = _string,
        context = DefaultRenderContext(
            emojiMap = emptyMap(),
            mentionMap = emptyMap(),
            linkColor = MaterialTheme.colorScheme.primary,
            uriHandler = LocalUriHandler.current,
            clickActionHandler = actionHandler
        )
    )
}