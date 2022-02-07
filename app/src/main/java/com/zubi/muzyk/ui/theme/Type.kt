package com.zubi.muzyk.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zubi.muzyk.R


val signature = FontFamily(
    Font(R.font.signature, FontWeight.Normal)
)

val AvertaFont = FontFamily(
    Font(R.font.averta_black, FontWeight.Black),
    Font(R.font.averta_bold, FontWeight.Bold),
    Font(R.font.averta_extrabold, FontWeight.ExtraBold),
    Font(R.font.averta_light, FontWeight.Light),
    Font(R.font.averta_regular, FontWeight.Normal),
    Font(R.font.averta_semibold, FontWeight.SemiBold),
    Font(R.font.averta_thin, FontWeight.Thin),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = AvertaFont,
    body1 = TextStyle(
        fontFamily = AvertaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
/* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)