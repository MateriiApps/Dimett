package xyz.wingio.dimett.domain.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.wingio.dimett.rest.dto.CustomEmoji
import xyz.wingio.dimett.rest.dto.user.Field

@ProvidedTypeConverter
class Converters(
    private val json: Json
) {

    @TypeConverter
    fun jsonToEmojiList(emojiJson: String?): List<CustomEmoji>? {
        return emojiJson?.let { json.decodeFromString(it) }
    }

    @TypeConverter
    fun emojiListToJson(emojiList: List<CustomEmoji>?): String {
        return json.encodeToString(emojiList)
    }

    @TypeConverter
    fun jsonToFieldList(fieldJson: String?): List<Field>? {
        return fieldJson?.let { json.decodeFromString(it) }
    }

    @TypeConverter
    fun fieldListToJson(fieldList: List<Field>?): String {
        return json.encodeToString(fieldList)
    }

    @TypeConverter
    fun stringListToString(list: List<String>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun stringToStringList(string: String): List<String> {
        return json.decodeFromString(string)
    }

}