package xyz.wingio.dimett.utils

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.regex.Pattern

object EmojiUtils : KoinComponent {
    private val context: Context by inject()
    private val json: Json by inject()

    val emojis by lazy {
        val _json = String(context.assets.open("emoji.json").readBytes())
        json.decodeFromString<Map<String, String>>(_json)
    }

    val regex by lazy {
        "^(${
            emojis.keys.sortedByDescending { it.length }.joinToString("|") { emoji ->
                Pattern.quote(emoji)
            }
        })"
    }
}