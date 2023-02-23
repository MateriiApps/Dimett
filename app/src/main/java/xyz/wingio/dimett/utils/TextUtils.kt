package xyz.wingio.dimett.utils

import android.graphics.drawable.BitmapDrawable
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import xyz.wingio.dimett.ast.Renderer

@Composable
fun inlineContent(): Map<String, InlineTextContent> {
    val emoteSize = (LocalTextStyle.current.fontSize.value + 2f).sp
    val ctx = LocalContext.current

    return mapOf(
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


@Composable
fun getString(
    @StringRes string: Int,
    vararg args: Any
): AnnotatedString {
    val _string = stringResource(string, *args)
    return Renderer.renderString(_string).build()
}