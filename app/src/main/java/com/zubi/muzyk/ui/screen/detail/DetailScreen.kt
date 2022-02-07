package com.zubi.muzyk.ui.screen

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.palette.graphics.Palette
import com.google.accompanist.coil.rememberCoilPainter
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.R
import com.zubi.muzyk.Routes
import com.zubi.muzyk.SpotifyLoginActivity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.ui.theme.signature
import com.zubi.muzyk.util.*

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    trackId: String,
    viewModel: MainViewModel
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        DetailScreenBody(trackId, navHostController, viewModel)
    }
}

@Composable
fun DetailScreenBody(
    trackId: String,
    navHostController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val similarTracksList = remember {
        viewModel.getSimilarTracksState(trackId)
    }

    val listState = rememberLazyListState()

    Box(modifier.fillMaxSize()) {

        when (val result = similarTracksList.value) {
            is DataState.Error -> Text(result.exception.message ?: "Something went wrong.")
            DataState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is DataState.Success -> {
                DetailScreenContent(
                    listState,
                    result.data,
                    navHostController,
                    viewModel = viewModel
                )
            }
        }

        SaveToSpotifyButton(listState, modifier = Modifier.align(Alignment.BottomCenter))

    }


}

@Composable
fun SaveToSpotifyButton(listState: LazyListState, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val showButton by remember {
        derivedStateOf {
            Log.d(
                "POSITIONCAP",
                "${listState.firstVisibleItemIndex}  ${listState.layoutInfo.totalItemsCount}"
            )
            listState.firstVisibleItemIndex < listState.layoutInfo.totalItemsCount - 4
        }
    }


    AnimatedVisibility(
        visible = showButton,
        modifier = modifier
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
                val intent = Intent(context, SpotifyLoginActivity::class.java)
                context.startActivity(intent)
            },
    ) {
        Row(

            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_spotify),
                modifier = Modifier.size(25.dp),
                contentDescription = ""
            )
            HorizontalSpacer(size = 8)
            Text(
                "SAVE TO SPOTIFY", fontSize = 15.sp,
                color = Color.White,
            )
        }
    }
}

@Composable
fun DetailScreenContent(
    listState: LazyListState,
    data: SimilarTrackEntity,
    navHostController: NavHostController,
    viewModel: MainViewModel,
) {

    val context = LocalContext.current
    val color = remember {
        viewModel.getColorPaletteState(context, data.originalTrack.image?.url ?: "")
    }


    SideEffect {
        viewModel.insertIntoRecentSearch(data.originalTrack)
    }

    Column {
        OriginalTrackBody(data.originalTrack, color)
        OriginalPlayButton(
            data.originalTrack,
            viewModel = viewModel,
            color,
            modifier = Modifier.align(Alignment.End)
        )
        SimilarTrackBody(listState, navHostController, data.similarTracks, color)
    }
}

@Composable
fun SimilarTrackBody(
    listState: LazyListState,
    navHostController: NavHostController,
    similarTracks: List<Track>,
    color: State<Palette?>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 25.dp)
    ) {
        HeaderCell(color)
        LazyColumn(state = listState) {
            items(similarTracks) {
                SimilarTrackCell(it, navHostController)
            }
        }
    }
}

@Composable
fun SimilarTrackCell(
    track: Track?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val mediaPlayer = LocalMediaPlayer.current

    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable {
                navHostController.navigate(Routes.DETAIL + track?.id)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberCoilPainter(request = track?.image?.url),
            contentDescription = track?.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .shadow(1.dp, shape = RoundedCornerShape(8))
        )
        HorizontalSpacer(size = 8)
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(
                    track?.title ?: "NA",
                    fontSize = 18.sp
                )
                VerticalSpacer(size = 2)
                Text(
                    track?.artists?.get(0) ?: "Not Found", color = Color(0xffbdbdbd),
                    fontSize = 14.sp
                )
                if (track?.explicit == true)
                    Image(
                        painter = painterResource(id = R.drawable.ic_parental_advisory),
                        contentDescription = "Parental Advisoray",
                        modifier = Modifier
                            .size(25.dp)
                    )

            }
            Icon(
                imageVector = Icons.Rounded.PlayCircle,
                contentDescription = "Play ${track?.title}",
                tint = MaterialTheme.colors.onBackground.withAlpha(0.6f),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (!track?.previewUrl.isNullOrEmpty())
                            mediaPlayer.play(track?.previewUrl!!)
                    }
            )
        }
    }
}

@Composable
fun HeaderCell(color: State<Palette?>, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Similar Tracks",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Show all",
            fontSize = 15.sp,
            color = Color(
                color.value
                    ?.getDominantColor(0xff0000ff.toInt())
                    ?: Color.Blue.value.toInt()
            ),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun OriginalPlayButton(
    originalTrack: Track,
    viewModel: MainViewModel,
    color: State<Palette?>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mediaPlayer = LocalMediaPlayer.current
    Icon(
        painter = painterResource(id = R.drawable.ic_play_button),
        contentDescription = "Play Buttons",
        tint = Color.White,
        modifier = modifier
            .offset(y = -30.dp, x = -30.dp)
            .size(60.dp)
            .shadow(20.dp, shape = CircleShape)
            .background(
                if (!originalTrack.previewUrl.isNullOrEmpty())
                    Color(
                        color.value
                            ?.getDominantColor(0xff0000ff.toInt())
                            ?: Color.Blue.value.toInt()
                    ) else Color(
                    color.value
                        ?.getDominantColor(0xff0000ff.toInt())
                        ?: Color.Blue.value.toInt()
                ).withAlpha(0.5f)
            )
            .padding(25.dp)
            .clickable {
                if (!originalTrack.previewUrl.isNullOrEmpty())
                    mediaPlayer.play(originalTrack.previewUrl)
            }
    )
}

@Composable
fun OriginalTrackBody(
    originalTrack: Track?,
    color: State<Palette?>
) {

    val getFirstLastNameArtist = getFirstLastName(originalTrack?.artists?.get(0) ?: "NA")
    val artistNamePadding = 25

    Card(
        shape = RoundedCornerShape(0, 0, 0, 10),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = rememberCoilPainter(request = originalTrack?.image?.url),
                contentDescription = originalTrack?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.withAlpha(0.5f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 20.dp, vertical = 0.dp)
            ) {
                Text(
                    originalTrack?.title?.capital() ?: "Na",
                    color = Color(
                        color.value
                            ?.getLightMutedColor(0xff0000ff.toInt())
                            ?: Color.Blue.value.toInt()
                    ),
                )
                VerticalSpacer(size = 20)
                Text(
                    text = getFirstLastNameArtist.first,
                    fontFamily = signature,
                    color = Color.White,
                    fontSize = 65.sp,
                )
                Text(
                    text = getFirstLastNameArtist.second,
                    fontFamily = signature,
                    color = Color.White,
                    fontSize = 55.sp,
                    modifier = Modifier
                        .offset(y = -artistNamePadding.dp)
                )
                if (originalTrack?.explicit == true)
                    Image(
                        painter = painterResource(id = R.drawable.ic_parental_advisory),
                        contentDescription = "Parental Advisoray",
                        modifier = Modifier
                            .size(50.dp)
                            .offset(y = -artistNamePadding.dp)
                    )
            }
        }

    }
}

