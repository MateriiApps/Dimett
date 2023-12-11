package xyz.wingio.dimett.rest.dto

import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/FilterKeyword/
@Stable
@Serializable
data class FilterKeyword(
    val id: String,
    val keyword: String,
    @SerialName("whole_word") val wholeWord: String
)