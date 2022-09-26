package com.zubi.muzyk.ui.screen.weird

import android.graphics.Bitmap
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.data.local.entity.WeirdSongEntity
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.util.*

@Composable
fun WeirdScreenResult(
    text: String,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
) {


    val currentSelected = remember {
        mutableStateOf<WeirdSongEntity?>(null)
    }


    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(key1 = currentSelected.value) {
        currentSelected.value?.albumCover?.let {
            bitmap.value = blurBitmap(it)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = bitmap.value,
            animationSpec = tween(1000)
        ) {
            AsyncImage(
                model = it,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        WeirdScreenBody(
            text,
            currentSelected,
            mainViewModel = mainViewModel,
            navHostController = navHostController
        )
    }

}

@Composable
fun WeirdScreenBody(
    text: String,
    currentSelected: MutableState<WeirdSongEntity?>,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {

    val weirdSongList = remember {
        mainViewModel.getWeirdSongs(song = "It's you mine butter not afraid wonders pink venom stay die for you party Soulful keys late night drive no more parties")
    }

    Column(
        modifier = Modifier.statusBarsPadding()
    ) {

        ActionBarView()

        when (val result = weirdSongList.value) {
            is DataState.Error -> Text("Something went wrong")
            DataState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth()) {
                    LottieLoading(
                        size = 100,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is DataState.Success -> {

                LaunchedEffect(key1 = true) {
                    currentSelected.value = result.data.first()
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            0.5.dp,
                            RoundedCornerShape(topStartPercent = 10),
                            ambientColor = Color.Transparent,
                            spotColor = Color.Black.ten(),
                        )
                        .background(Color.Black.twenty())
                ) {

                    Box(
                        Modifier
                            .fillMaxWidth(0.25f)
                            .padding(8.dp)
                            .height(3.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(50)
                            )
                            .align(Alignment.TopCenter)
                    )

                    LazyColumn() {

                        item {
                            VerticalSpacer(size = 20)
                        }

                        items(result.data.size) {
                            SongItemView(
                                result.data[it],
                                currentSelected,
                                mainViewModel = mainViewModel
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ActionBarView(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.MusicNote,
                contentDescription = "Music Note",
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
            Text(
                buildAnnotatedString {
                    append(" Playlist")
                },
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
        HorizontalSpacer(size = 5)
        Text(
            buildAnnotatedString {
                append("Create playlist")
            },
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )


    }
}


@Composable
fun SongItemView(
    weirdSongEntity: WeirdSongEntity,
    currentItem: MutableState<WeirdSongEntity?>,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStartPercent = 25, bottomEndPercent = 25))
            .background(if (currentItem.value === weirdSongEntity) BDBDBD.twenty() else Color.Transparent)
            .clickable {
                currentItem.value = weirdSongEntity
                mainViewModel.selectedWeirdSpotify.value = weirdSongEntity
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = weirdSongEntity.albumCover,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .shadow(
                        1.dp,
                        RoundedCornerShape(topStartPercent = 20, bottomEndPercent = 20),
                        // spotColor = Color.Black.ten(),
                    )
            )
            HorizontalSpacer(size = 16)
            Column(
                Modifier
            ) {
                Text(
                    text = weirdSongEntity.songName,
                    fontSize = 15.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                )
                VerticalSpacer(size = 3)
                Text(
                    text = weirdSongEntity.artistName,
                    fontSize = 12.sp,
                    color = BDBDBD
                )
            }
        }
    }

}