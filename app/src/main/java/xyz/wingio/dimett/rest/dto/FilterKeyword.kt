package xyz.wingio.dimett.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterKeyword(
    val id: String,
    val keyword: String,
    @SerialName("whole_word") val wholeWord: String
)