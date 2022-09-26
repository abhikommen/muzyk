package com.zubi.muzyk.ui.screen.detail

import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavHostController
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.R
import com.zubi.muzyk.Routes
import com.zubi.muzyk.SpotifyLoginActivity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.ui.screen.home.PlayButton
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.ui.theme.SPOTIFY_GREEN
import com.zubi.muzyk.ui.theme.signature
import com.zubi.muzyk.util.*

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    trackId: String,
    viewModel: MainViewModel
) {
    Surface(
        color = APP_BACKGROUND,
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
            DataState.Loading -> LottieLoading(
                size = 80,
                modifier = Modifier.align(Alignment.Center)
            )
            is DataState.Success -> {
                DetailScreenContent(
                    listState,
                    result.data,
                    navHostController,
                    viewModel = viewModel
                )
            }
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
    val colorState = remember {
        viewModel.getColorPaletteState(context, data.originalTrack.image?.url ?: "")
    }

    val color = Color(
        colorState.value
            ?.getDominantColor(0xff12D69E.toInt())
            ?: SPOTIFY_GREEN.value.toInt()
    )

    val animatedColor = animateColorAsState(
        color,
        animationSpec = tween<Color>(
            durationMillis = 1500
        )
    )

    val textColor = blendColors(animatedColor.value, Color.White, 0.7f)


    LaunchedEffect(key1 = true) {
        viewModel.insertIntoRecentSearch(data.originalTrack)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderView(originalTrack = data.originalTrack, color = color)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            blendColors(animatedColor.value, Color.Black, 0.15f),
                            blendColors(animatedColor.value, Color.Black, 0.1f)
                        )
                    )
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomStart),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                ListHeader(data.originalTrack, textColor)
            }


            item {
                ArtistCell(data.originalTrack, textColor)
            }

            item {
                Column {
                    Divider(
                        color = color.fifty(), thickness = 1.5.dp, modifier = Modifier.clip(
                            RoundedCornerShape(50)
                        )
                    )
                    VerticalSpacer(size = 2)
                    Text(
                        text = "Similar Songs :",
                        color = textColor,
                        fontSize = 13.sp
                    )
                }
            }

            items(data.similarTracks) {
                SimilarTrackItem(it, textColor)
            }

        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!data.originalTrack.previewUrl.isNullOrEmpty()) {
                PlayButton(
                    data.originalTrack.previewUrl,
                    textColor,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(60.dp)
                )
            }
            Text(
                text = "+ Add As A PlayList",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
            )
        }
    }
}

@Composable
fun SimilarTrackItem(
    track: Track,
    color: Color,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Row(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = track.image?.url,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(70.dp),
            contentDescription = ""
        )
        HorizontalSpacer(size = 8)
        Column() {
            Text(
                text = track.title ?: "",
                fontSize = 13.sp,
                color = color,
            )
            Text(
                text = track.artists?.joinToString(", ") ?: "",
                fontSize = 13.sp,
                color = color,
            )
        }
    }
}

@Composable
fun ArtistCell(
    originalTrack: Track,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Divider(
            color = color.fifty(), thickness = 1.5.dp, modifier = Modifier.clip(
                RoundedCornerShape(50)
            )
        )
        VerticalSpacer(size = 2)
        Text(
            text = "Artists :",
            color = color,
            fontSize = 13.sp
        )
        originalTrack.artists?.let {
            repeat(it.size) {
                Text(
                    text = originalTrack.artists[it],
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = color,
                )
            }
        }
    }
}

@Composable
fun ListHeader(track: Track, color: Color) {
    Text(
        buildAnnotatedString {
            append(track.title ?: "")
            //  this.append("\n    " + originalTrack.title)
        }, color = color,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Visible,
        fontSize = flexibleFontSize(track.title!!)
    )
}

fun flexibleFontSize(title: String): TextUnit {
    return when (title.length) {
        in 0..16 -> 60.sp
        in 16..100 -> 30.sp
        else -> 40.sp
    }
}


@Composable
fun HeaderView(
    originalTrack: Track,
    color: Color
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
    ) {
        AsyncImage(
            model = scrollFadeImage(context, originalTrack.image?.url!!),
            contentDescription = originalTrack.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

