package com.zubi.muzyk.ui.screen.artist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.data.local.entity.ArtistEntity
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.ui.theme.SPOTIFY_GREEN
import com.zubi.muzyk.util.*

@Composable
fun ArtistScreen(
    artistEntity: ArtistEntity,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Surface(
        color = APP_BACKGROUND,
        modifier = modifier.fillMaxSize()
    ) {
        ArtistScreenBody(
            artistEntity = artistEntity,
            mainViewModel = mainViewModel,
            navHostController = navHostController
        )
    }
}

@Composable
fun ArtistScreenBody(
    artistEntity: ArtistEntity,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        VerticalSpacer(size = 16)
        ArtistHeader(artistEntity)
    }

}

@Composable
fun ArtistHeader(artistEntity: ArtistEntity, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = scrollFadeImage(context, artistEntity.pfp ?: ""),
            contentDescription = artistEntity.name,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1.1f)
                .clip(
                    shape = RoundedCornerShape(
                        topEnd = 45.dp,
                        bottomEnd = 90.dp
                    )
                ),
            contentScale = ContentScale.Crop
        )
        HorizontalSpacer(size = 8)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
        ) {
            Text(
                text = artistEntity.name,
                fontSize = 35.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text =artistEntity.genres.joinToString(", "),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = BDBDBD
            )
        }

    }
}

