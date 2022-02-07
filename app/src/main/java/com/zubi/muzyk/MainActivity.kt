package com.zubi.muzyk

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.zubi.muzyk.data.MainBody
import com.zubi.muzyk.ui.theme.AnimeQuotesTheme
import com.zubi.muzyk.util.LocalMediaPlayer
import com.zubi.muzyk.util.SPOTIFY_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var mediaPlayer: MediaPlayer

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mediaPlayer = MediaPlayer()
        setContent {
            mainViewModel = hiltViewModel()
            AnimeQuotesTheme {
                CompositionLocalProvider(
                    values = arrayOf(
                        LocalMediaPlayer provides mediaPlayer,
                    )
                ) {
                    MainBody(mainViewModel)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == SPOTIFY_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    val token = response.accessToken
                    mainViewModel.saveToken(token)
                }
                AuthorizationResponse.Type.ERROR -> {}
                else -> {}
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

}

