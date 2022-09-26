package com.zubi.muzyk.ui.screen.login

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.insets.statusBarsPadding
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.R
import com.zubi.muzyk.Routes
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.ui.theme.SPOTIFY_GREEN
import com.zubi.muzyk.ui.theme.signature
import com.zubi.muzyk.util.HorizontalSpacer
import com.zubi.muzyk.util.VerticalSpacer
import com.zubi.muzyk.util.seventy
import com.zubi.muzyk.util.ten


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    Box(
        Modifier
            .background(
                Color(0xff080A0B)
            )
            .fillMaxSize()
    ) {
        AsyncImage(
            model = R.drawable.login_bg,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            contentDescription = "",
            alpha = 0.4f
        )
        NewLoginScreenBody(viewModel, navHostController)
    }

}

@Composable
fun NewLoginScreenBody(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Later",
                color = BDBDBD,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
            VerticalSpacer(size = 10)
            Text(
                text = buildAnnotatedString {
                    append("Like")
                    withStyle(style = SpanStyle(color = Color(0xff5CB0E5))) {
                        append("ly")
                    }
                },
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Black
            )
            VerticalSpacer(size = 50)
            AsyncImage(
                model = R.drawable.login_title,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = ""
            )
        }

        LoginButton(
            mainViewModel = mainViewModel,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}


@Composable
fun LoginButton(mainViewModel: MainViewModel?, modifier: Modifier = Modifier) {
    val activityContext = LocalContext.current as Activity
    Row(
        modifier = modifier
            .shadow(
                5.dp,
                RoundedCornerShape(50),
                spotColor = Color(0xffFF106F),
                ambientColor = Color(0xffFF005D)
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xff009FFF),
                        Color(0xff654ea3),
                    )
                )
            )
            .clickable {
                mainViewModel?.requestSpotifySignIn(activityContext)
            }
            .padding(vertical = 15.dp, horizontal = 50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            "Sign in with", fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        HorizontalSpacer(size = 5)
        AsyncImage(
            model = R.drawable.spotify_logo,
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )
    }
}

