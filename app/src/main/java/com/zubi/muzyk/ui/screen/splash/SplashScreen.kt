package com.zubi.muzyk.ui.screen.splash

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.Routes
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.util.LoginStatus
import com.zubi.muzyk.util.LottieLoading

@Composable
fun SplashScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize(), color = APP_BACKGROUND) {
        SplashScreenBody(mainViewModel, navHostController)
    }
}

@Composable
fun SplashScreenBody(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val activity = LocalContext.current as Activity

    LaunchedEffect(key1 = true) {
        mainViewModel.requestSpotifySignIn(activity)
    }


    LaunchedEffect(key1 = mainViewModel.loginState.value){
        when (mainViewModel.loginState.value) {
            LoginStatus.LOADING -> {
            }
            LoginStatus.SUCCESS -> {
                navHostController.navigate(Routes.HOME){
                    popUpTo(Routes.SPLASH) {
                        this.inclusive = true
                    }
                }
            }
            LoginStatus.FAILED -> {
            }
        }
    }


    Box(modifier = modifier.fillMaxSize()) {
        LottieLoading(size = 70, modifier = Modifier.align(Alignment.Center))


    }
}
