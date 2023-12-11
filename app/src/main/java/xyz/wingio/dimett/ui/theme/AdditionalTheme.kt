package xyz.wingio.dimett.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

/**
 * Provides themed colors not available in [MaterialTheme]
 */
@Stable
class AdditionalColorScheme(
    favorite: Color,
    boost: Color
) {

    var favorite by mutableStateOf(favorite, structuralEqualityPolicy())
        private set

    var boost by mutableStateOf(boost, structuralEqualityPolicy())
        private set

}

val LocalAdditionalColorScheme = compositionLocalOf<AdditionalColorScheme> { error("No AdditionalColorScheme specified") }

val MaterialTheme.additionalColors: AdditionalColorScheme
    @Composable
    get() = LocalAdditionalColorScheme.current


// Default color schemes

val DarkAdditionalColorScheme = AdditionalColorScheme(
    favorite = HotPink,
    boost = Green
)

val LightAdditionalColorScheme = AdditionalColorScheme(
    favorite = HotPink100,
    boost = Green200
)