package com.zubi.muzyk.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zubi.muzyk.App
import com.zubi.muzyk.R
import java.util.*


val LocalMediaPlayer = compositionLocalOf {
    MediaPlayer()
}


fun String?.capital(): String =
    this?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        ?: "NA"

fun isDarkMode(context: Context): Boolean {
    val darkModeFlag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
}


val appContext = App.appContext

fun Color.withAlpha(alphaValue: Float = 0f): Color {
    return this.copy(alpha = alphaValue)
}




@Composable
fun VerticalSpacer(size: Int) {
    Spacer(
        modifier = Modifier
            .height(size.dp)
            .background(Color.Transparent)
    )
}


@Composable
fun LottieLoading(
    size: Int = 35,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.unicorn))
    val progress by animateLottieCompositionAsState(
        composition,
        speed = 1f,
        iterations = Int.MAX_VALUE
    )
    LottieAnimation(
        composition,
        progress,
        modifier
            .size(size.dp)
    )

}

suspend fun blurBitmap(url: String): Bitmap {
    val loader = ImageLoader(appContext)
    val request = ImageRequest.Builder(appContext)
        .data(url)
        .allowHardware(false) // Disable hardware bitmaps.
        .build()

    val result = (loader.execute(request) as SuccessResult).drawable
    val bitmap = (result as BitmapDrawable).bitmap

    return blur(appContext, bitmap)!!
}

private const val BITMAP_SCALE = .2f
private const val BLUR_RADIUS_X = 17f


fun blur(context: Context?, image: Bitmap): Bitmap? {
    val width = Math.round(image.width * BITMAP_SCALE).toInt()
    val height = Math.round(image.height * BITMAP_SCALE).toInt()
    val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
    val outputBitmap = Bitmap.createBitmap(inputBitmap)
    val rs = RenderScript.create(context)
    val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
    val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
    theIntrinsic.setRadius(BLUR_RADIUS_X)
    theIntrinsic.setInput(tmpIn)
    theIntrinsic.forEach(tmpOut)
    tmpOut.copyTo(outputBitmap)
    return outputBitmap
}


fun blendColors(color1: Color, color2: Color, ratio: Float): Color {
    val inverseRation = 1f - ratio

    val r: Float = color1.red * ratio + color2.red * inverseRation
    val g: Float = color1.green * ratio + color2.green * inverseRation
    val b: Float = color1.blue * ratio + color2.blue * inverseRation

    return Color(
        r,
        g,
        b
    )
}


fun scrollFadeImage(context: Context, url: String): ImageRequest {
    return ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .build()
}

@Composable
fun HorizontalSpacer(size: Int) {
    Spacer(modifier = Modifier.width(size.dp))
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun screenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun screenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun getFirstLastName(fullName: String): Pair<String, String> {
    val firstSpace: Int = fullName.indexOf(" ") // detect the first space character
    if (firstSpace < 0) {
        return fullName to ""
    }
    val firstName: String =
        fullName.substring(0, firstSpace)

    val lastName: String = fullName.substring(firstSpace)
        .trim()
    return firstName to lastName
}

/**
 * Color Opacity/Alpha Extension Function..
 */

fun Color.ten() = this.copy(.10f)
fun Color.twenty() = this.copy(.20f)
fun Color.thirty() = this.copy(.30f)
fun Color.forty() = this.copy(.40f)
fun Color.fifty() = this.copy(.50f)
fun Color.sixty() = this.copy(.60f)
fun Color.seventy() = this.copy(.70f)
fun Color.eighty() = this.copy(.80f)
fun Color.ninety() = this.copy(.90f)
