package com.zubi.muzyk.util

import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zubi.muzyk.App
import java.util.*

val LocalMediaPlayer = compositionLocalOf {
    MediaPlayer()
}



fun String?.capital(): String =
    this?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        ?: "NA"


val appContext = App.appContext

fun Color.withAlpha(alphaValue: Float = 0f): Color {
    return this.copy(alpha = alphaValue)
}

fun MediaPlayer.play(url: String) {
    if (this.isPlaying) {
        this.pause()
        this.stop()
        this.reset()
    }

    this.setDataSource(url)
    this.prepare()
    this.seekTo(0)
    this.start()
}


@Composable
fun VerticalSpacer(size: Int) {
    Spacer(modifier = Modifier.height(size.dp))
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
