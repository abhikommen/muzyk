package com.zubi.muzyk.ui.screen.profile

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.data.local.entity.ArtistEntity
import com.zubi.muzyk.data.local.entity.NewUser
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.ui.theme.APP_SEMI_BACKGROUND
import com.zubi.muzyk.util.*

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.fillMaxSize(), color = APP_SEMI_BACKGROUND
    ) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
            systemUiController.setNavigationBarColor(Color.Transparent)
        }
        ProfileScreenBody(mainViewModel, navHostController)
    }

}

@Composable
fun ProfileScreenBody(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val currentUser by remember {
        mainViewModel.getLoggedInUser()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ProfileHeader(
                currentUser, mainViewModel = mainViewModel, navHostController = navHostController
            )
        }

        item {

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceAround
            ) {
                RecentPlayBox(
                    mainViewModel = mainViewModel,
                    navHostController = navHostController,
                    modifier = Modifier
                        .weight(0.4f)
                )
                Spacer(modifier = Modifier.weight(0.1f))
                FavoriteArtist(
                    mainViewModel = mainViewModel,
                    navHostController = navHostController,
                    modifier = Modifier
                        .weight(0.4f)
                )
            }
        }

    }
}

@Composable
fun ProfileHeader(
    newUser: DataState<NewUser>,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(pxToDp(screenHeight() / 2).dp)
            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
            .background(
                Color(0xffc2fc4a)
            )
    ) {

        BackButton(
            navHostController, modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
        )

        when (newUser) {
            is DataState.Error -> {

            }
            DataState.Loading -> CircularProgressIndicator()
            is DataState.Success -> {
                ProfilePic(newUser.data, modifier = Modifier.align(Alignment.Center))
                ProfileInfo(
                    newUser.data,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp)
                )
            }
        }
    }
}

@Composable
fun BackButton(navHostController: NavHostController, modifier: Modifier = Modifier) {
    Icon(
        Icons.Rounded.ArrowBack,
        contentDescription = "Back button",
        tint = Color.White,
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(APP_BACKGROUND)
            .padding(14.dp)
    )
}

@Composable
fun ProfileInfo(newUser: NewUser, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "2", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "followers", fontSize = 12.sp, color = Color.Black.seventy())
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "380", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "following", fontSize = 12.sp, color = Color.Black.seventy())
        }

    }
}

@Composable
fun ProfilePic(
    newUser: NewUser, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = newUser.pfp,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp)
        )
        VerticalSpacer(size = 10)
        Text(newUser.name, fontWeight = FontWeight.SemiBold)
    }

}

