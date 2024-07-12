package com.example.stocksapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//private val DarkColorScheme = darkColorScheme(
//    primary = Whitish,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary =  Whitish,
//    secondary =  Color.Blue,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)


val LightColorScheme = lightColorScheme(
    primaryContainer = InstaLightBackground,
    onBackground = InstaLightContent,
    surface = InstaLightBackground,
    onSurface = InstaLightContent,
    primary = InstaLightAccent,
    onPrimary = Color.White,
    surfaceVariant = Whitish,
    secondary = InstaLightAccent,
    onSecondary = Color.White
)

val DarkColorScheme = darkColorScheme(
    primaryContainer= InstaDarkBackground,
    onBackground = InstaDarkContent,
    surfaceContainer = InstaDarkBackground,
    surfaceContainerLow = InstaDarkBackground,
    surfaceContainerLowest = InstaDarkBackground,
    surfaceContainerHigh = InstaDarkBackground,
    surfaceContainerHighest = InstaDarkBackground,
    surface = InstaDarkBackground,
    onSurface = InstaDarkContent,
    primary = InstaDarkAccent,
    onPrimary = Color.White,
    surfaceVariant = greyish,
    secondary = InstaDarkAccent,
    onSecondary = Color.White
)

@Composable
fun StocksAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}