package xyz.wingio.dimett.ast

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import com.materii.simplerast.core.text.RichTextBuilder
import com.materii.simplerast.core.text.StyleInclusion

class AnnotatedBuilder(initial: String) : RichTextBuilder {

    private var builder = AnnotatedString.Builder(initial)
    override val length: Int get() = builder.length

    override fun append(text: Char) {
        builder.append(text)
    }

    override fun append(text: CharSequence) {
        builder.append(text.toString())
    }

    override fun get(index: Int): Char {
        return builder.toAnnotatedString()[index]
    }

    override fun getChars(start: Int, end: Int, destination: CharArray, offset: Int) {}
    override fun insert(where: Int, text: CharSequence) {}

    override fun setStyle(style: Any, start: Int, end: Int, inclusion: StyleInclusion) {
        when (style) {
            is SpanStyle -> builder.addStyle(style, start, end)
            is ParagraphStyle -> builder.addStyle(style, start, end)
            is StringAnnotation -> builder.addStringAnnotation(style.tag, style.text, start, end)
        }
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return builder.toString().subSequence(startIndex, endIndex)
    }

    fun build() = builder.toAnnotatedString()

}

data class StringAnnotation(
    val tag: String,
    val text: String
)