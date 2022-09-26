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
    Font(R.font.euclid_bold, FontWeight.Bold),
    Font(R.font.euclid_light, FontWeight.Light),
    Font(R.font.euclid_regular, FontWeight.Normal),
    Font(R.font.euclid_medium, FontWeight.Medium),
    Font(R.font.euclid_light, FontWeight.Thin),
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