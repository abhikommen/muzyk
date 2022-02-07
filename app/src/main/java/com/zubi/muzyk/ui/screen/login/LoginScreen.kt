package com.zubi.muzyk.ui.screen.login

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.statusBarsPadding
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.R
import com.zubi.muzyk.Routes
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.ui.theme.signature
import com.zubi.muzyk.util.HorizontalSpacer
import com.zubi.muzyk.util.VerticalSpacer


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        LoginScreenBody(viewModel, navHostController)
    }

}

@Composable
fun LoginScreenBody(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "",
                    modifier = Modifier.size(56.dp)
                )
                HorizontalSpacer(size = 10)
                Text(
                    "Muzyk",
                    fontSize = 32.sp,
                    fontFamily = signature,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                "SKIP", fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    navHostController.navigate(Routes.HOME)
                }
            )
        }
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text("Let's Get\nStarted", fontWeight = FontWeight.Bold, fontSize = 40.sp)
            VerticalSpacer(size = 20)
            Text(
                "Give us your favourite track and we’ll serve up a sweet playlist with similar songs that you’ll love!",
                fontWeight = FontWeight.SemiBold, fontSize = 14.sp,
                color = BDBDBD
            )
            VerticalSpacer(size = 20)
            LoginButton(mainViewModel = mainViewModel)
        }

    }

}


@Composable
fun LoginButton(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    val activityContext = LocalContext.current as Activity
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(25.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xff1DD660),
                        Color(0xff1DD660),
                        Color(0xff4D8A98)
                    )
                )
            )
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .clickable {
                mainViewModel.requestSpotifySignIn(activityContext)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_spotify),
            modifier = Modifier.size(25.dp),
            contentDescription = ""
        )
        HorizontalSpacer(size = 8)
        Text(
            "Sign in with Spotify", fontSize = 15.sp,
            color = Color.White,
        )
    }
}

