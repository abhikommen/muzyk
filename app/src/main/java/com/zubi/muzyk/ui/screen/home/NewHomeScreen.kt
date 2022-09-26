package com.zubi.muzyk.ui.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.Routes
import com.zubi.muzyk.data.local.entity.ArtistEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.util.*

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                APP_BACKGROUND
            )
    ) {
        HomeScreenBody(navHostController, viewModel)
    }
}

@Composable
fun HomeScreenBody(
    navHostController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {

    val localDataList = viewModel.getRecentSearchLocal.collectAsState(initial = DataState.Loading)


    val inputText = remember {
        mutableStateOf("")
    }
    val resultState = remember {
        viewModel.searchQueryList
    }

    val topArtistsResult = remember {
        viewModel.getTopArtists()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .animateContentSize(), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            ProfileHeader(viewModel, navHostController, modifier = Modifier.padding(16.dp))
        }

        item {
            SearchBoxItem(
                inputText,
                viewModel,
                navHostController,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        if (inputText.value.trim().isNotEmpty())
            when (val result = resultState.value) {
                is DataState.Error -> item { Text("Something went wrong") }
                DataState.Loading -> item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LottieLoading(
                            size = 100,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                is DataState.Success -> items(result.data) {
                    SearchItem(it, navHostController)
                }
            }

        item {
            TopSongsView(topArtistsResult, navHostController = navHostController)
        }

        item {
            Column() {
                HomeDataList("top picks for you", localDataList, navHostController, viewModel)
            }
        }

        item {
            WeirdAdvertisement(
                mainViewModel = viewModel,
                navHostController = navHostController,
                this@LazyColumn,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }


        item {
            DataHeader(
                "what's your mood?",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(moodList) {
                    AsyncImage(
                        model = it.icon,
                        contentDescription = "",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }
        }

    }
}

@Composable
fun TopSongsView(
    chartsResult: State<DataState<List<ArtistEntity>>>,
    navHostController: NavHostController
) {
    when (val result = chartsResult.value) {
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
            Column {
                DataHeader(
                    "your top artists",
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    navHostController.navigate(Routes.TRENDING)
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(result.data) {
                        TopArtistCell(it, navHostController = navHostController)
                    }
                }
            }
        }
    }
}

@Composable
fun TopArtistCell(
    artistEntity: ArtistEntity,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .width(100.dp)
            .height(200.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    bottomStart = 15.dp,
                    topEnd = 35.dp,
                    bottomEnd = 70.dp
                )
            )
            .clickable {
                navHostController.navigate(Routes.ARTIST + artistEntity.toJson())
            }
    ) {
        AsyncImage(
            model = scrollFadeImage(context, artistEntity.pfp ?: ""),
            contentDescription = artistEntity.name,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.twenty(),
                            Color.Transparent
                        )
                    )
                )
        )

        val size = remember {
            mutableStateOf(IntSize.Zero)
        }

        Text(
            text = artistEntity.name,
            fontSize = 15.sp,
            overflow = TextOverflow.Visible,
            textAlign = TextAlign.Start,
            color = Color.White,
            maxLines = 1,
            modifier = Modifier
                .rotate(90f)
                .onSizeChanged {
                    size.value = it
                }
                .offset(
                    x = (pxToDp(size.value.width) / 2).dp,
                    y = (pxToDp(size.value.width) / 2.5).dp,
                )
            //.background(SPOTIFY_GREEN)

        )

    }
}


@Composable
fun HomeDataList(
    heading: String,
    dataList: State<DataState<List<Track>>>,
    navHostController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    DataHeader(heading, modifier = Modifier.padding(all = 16.dp))
    VerticalSpacer(size = 8)
    when (val result = dataList.value) {
        is DataState.Error -> Text(result.exception.message ?: "Something went wrong.")
        DataState.Loading -> CircularProgressIndicator()
        is DataState.Success -> LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(result.data) {
                HomeItemCell(it, navHostController)
            }
        }
    }
}


@Composable
fun HomeItemCell(
    track: Track?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .width(110.dp)
            .clickable {
                navHostController.navigate(Routes.DETAIL + track?.id)
            }
    ) {
        AsyncImage(
            model = scrollFadeImage(context, track?.image?.url ?: ""),
            contentDescription = track?.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            contentScale = ContentScale.FillBounds
        )
        VerticalSpacer(size = 8)
        Text(
            track?.title ?: "Na",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color.White
        )
        VerticalSpacer(size = 2)
        Text(
            track?.artists?.get(0) ?: "Na",
            color = Color(0xffbdbdbd),
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Visible,
            maxLines = 1,
            fontSize = 12.sp
        )
    }
}

@Composable
fun DataHeader(
    heading: String,
    modifier: Modifier = Modifier,
    clickable: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            heading,
            fontSize = 18.sp,
            color = Color.White,
        )
        clickable?.let {
            Text(
                "more",
                fontSize = 15.sp,
                color = BDBDBD,
                modifier = Modifier.clickable(onClick = it)
            )
        }
    }
}
