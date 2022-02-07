package com.zubi.muzyk

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zubi.muzyk.ui.screen.DetailScreen
import com.zubi.muzyk.ui.screen.home.HomeScreen
import com.zubi.muzyk.ui.screen.login.LoginScreen
import com.zubi.muzyk.util.appContext

object Routes {
    const val HOME = "home/"
    const val DETAIL = "detail/"
    const val LOGIN = "login/"
}

@Composable
fun AppGraph(mainViewModel: MainViewModel) {

    val navHostController = rememberNavController()


    NavHost(navController = navHostController, startDestination = Routes.LOGIN) {

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
            if (mainViewModel.isLogin()) {
                HomeScreen(navHostController, mainViewModel)
            } else
                LoginScreen(navHostController = navHostController, viewModel = mainViewModel)
        }

    }

}