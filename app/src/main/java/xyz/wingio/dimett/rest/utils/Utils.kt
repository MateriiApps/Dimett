package xyz.wingio.dimett.rest.utils

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody

/**
 * Shorthand for setting a MultiPart Form Data request body
 */
fun HttpRequestBuilder.setForm(formBuilder: FormBuilder.() -> Unit) {
    setBody(
        MultiPartFormDataContent(
            formData(formBuilder)
        )
    )
}