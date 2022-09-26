package com.zubi.muzyk.ui.screen.home

import android.media.MediaPlayer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun PlayButton(
    url: String,
    animatedColor: Color,
    modifier: Modifier = Modifier
) {

    val mediaPlayerState = rememberMediaView()

    val playState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = playState.value) {
        if (playState.value) {
            mediaPlayerState.start()
        } else if (mediaPlayerState.isPlaying) {
            mediaPlayerState.pause()
        } else {
            mediaPlayerState.setDataSource(url)
            mediaPlayerState.prepare()
        }
    }


    val lifecycleOwner = LocalLifecycleOwner.current


    val icon = if (playState.value) {
        Icons.Rounded.PauseCircle
    } else {
        Icons.Rounded.PlayCircle
    }

    Icon(
        icon, contentDescription = "",
        tint = animatedColor,
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                playState.value = !playState.value
            }

    )

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mediaPlayerState.start()
                Lifecycle.Event.ON_PAUSE -> mediaPlayerState.pause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            mediaPlayerState.stop()
            mediaPlayerState.release()
        }
    }

}

@Composable
fun rememberMediaView(): MediaPlayer = remember {
    MediaPlayer()
}
