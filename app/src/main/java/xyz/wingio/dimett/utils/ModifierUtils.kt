package xyz.wingio.dimett.utils

import androidx.compose.ui.Modifier

/**
 * Applies additional modifiers when the [predicate] is met
 *
 * @param predicate Condition for applying additional modifiers
 */
fun Modifier.thenIf(predicate: Boolean, block: Modifier.() -> Modifier) =
    if(predicate) then(block()) else this