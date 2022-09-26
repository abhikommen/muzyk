package com.zubi.muzyk

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zubi.muzyk.data.local.entity.ArtistEntity
import com.zubi.muzyk.ui.screen.artist.ArtistScreen
import com.zubi.muzyk.ui.screen.detail.DetailScreen
import com.zubi.muzyk.ui.screen.home.HomeScreen
import com.zubi.muzyk.ui.screen.login.LoginScreen
import com.zubi.muzyk.ui.screen.profile.ProfileScreen
import com.zubi.muzyk.ui.screen.splash.SplashScreen
import com.zubi.muzyk.ui.screen.splash.SplashScreenBody
import com.zubi.muzyk.ui.screen.trending.TrendingScreen
import com.zubi.muzyk.ui.screen.weird.WeirdScreen
import com.zubi.muzyk.ui.screen.weird.WeirdScreenResult
import com.zubi.muzyk.util.CustomNavType
import com.zubi.muzyk.util.appContext

object Routes {
    const val SPLASH = "splash/"
    const val HOME = "home/"
    const val DETAIL = "detail/"
    const val LOGIN = "login/"
    const val PROFILE = "profile/"
    const val WEIRD_INPUT = "weird_input/"
    const val WEIRD_RESULT = "weird_result/"
    const val TRENDING = "trending/"
    const val ARTIST = "artist/"
}

fun decideStartDestination(mainViewModel: MainViewModel): String {
    return if (mainViewModel.isLogin()) {
        Routes.SPLASH
    } else {
        Routes.LOGIN
    }
}


@Composable
fun AppGraph(mainViewModel: MainViewModel) {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = decideStartDestination(mainViewModel)
    ) {

        composable(route = Routes.SPLASH) {
            SplashScreen(mainViewModel, navHostController)
        }

        composable(route = Routes.HOME) {
            HomeScreen(navHostController, mainViewModel)
        }

        composable(
            route = Routes.DETAIL + "{trackId}",
            arguments = listOf(navArgument("trackId") { type = NavType.StringType })
        ) {
            val trackId = it.arguments?.getString("trackId")
            if (trackId != null) {
                DetailScreen(navHostController, trackId, mainViewModel)
            } else {
                Toast.makeText(appContext, "Track Id Is Null", Toast.LENGTH_SHORT).show()
            }
        }

        composable(route = Routes.LOGIN) {
            LoginScreen(navHostController = navHostController, viewModel = mainViewModel)
        }

        composable(route = Routes.PROFILE) {
            ProfileScreen(navHostController, mainViewModel)
        }
        composable(route = Routes.TRENDING) {
            TrendingScreen(mainViewModel, navHostController)
        }

        composable(route = Routes.WEIRD_INPUT) {
            WeirdScreen(mainViewModel, navHostController)
        }


        composable(
            route = Routes.WEIRD_RESULT + "{text}",
            arguments = listOf(navArgument("text") { type = NavType.StringType })
        ) {
            val text = it.arguments?.getString("text")
            WeirdScreenResult(text!!, mainViewModel, navHostController)
        }


        composable(
            route = Routes.ARTIST + "{artist}",
            arguments = listOf(navArgument("artist") {
                type = CustomNavType(ArtistEntity::class.java)
            })
        ) {
            val artistEntity: ArtistEntity? = it.arguments?.getParcelable<ArtistEntity>("artist")
            ArtistScreen(
                artistEntity = artistEntity!!,
                navHostController = navHostController,
                mainViewModel = mainViewModel
            )
        }


    }

}