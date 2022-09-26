package com.zubi.muzyk.data

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zubi.muzyk.AppGraph
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.util.isDarkMode

@Composable
fun MainBody(mainViewModel: MainViewModel) {
    ProvideWindowInsets {
        val context = LocalContext.current
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController
                .setStatusBarColor(Color.Transparent, darkIcons = false)
            systemUiController.setNavigationBarColor(Color.Black)
        }

        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier.navigationBarsPadding(),
        ) {
            AppGraph(mainViewModel)
        }
    }

}