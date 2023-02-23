package xyz.wingio.dimett.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import xyz.wingio.dimett.utils.inlineContent
import xyz.wingio.dimett.utils.toAnnotatedString

@Composable
fun Text(
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier
) = Text(
    text = text.toAnnotatedString(),
    color = color,
    fontSize = fontSize,
    fontWeight = fontWeight,
    fontStyle = fontStyle,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    style = style,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    modifier = modifier
)

@Composable
fun Text(
    text: AnnotatedString,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = inlineContent(),
    modifier: Modifier = Modifier,
    clickHandler: ((String) -> Unit) = {}
) {
    val canBeClicked = text.getStringAnnotations(0, text.length).any {
        listOf("URL", "CLICKABLE").contains(it.tag)
    }
    val uriHandler = LocalUriHandler.current
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current
        }
    }
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val pressIndicator = if (canBeClicked) Modifier.pointerInput(clickHandler) {
        detectTapGestures { offset ->
            layoutResult.value?.let { layoutResult ->
                val pos = layoutResult.getOffsetForPosition(offset)
                text.getStringAnnotations(pos, pos).firstOrNull()
                    ?.let {
                        when (it.tag) {
                            "URL" -> uriHandler.openUri(it.item)
                            "CLICKABLE" -> clickHandler(it.item)
                        }
                    }
            }
        }
    } else Modifier

    val textStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing
        )
    )

    BasicText(
        text = text,
        modifier = modifier
            .then(pressIndicator),
        style = textStyle,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = {
            layoutResult.value = it
        },
        inlineContent = inlineContent
    )
}