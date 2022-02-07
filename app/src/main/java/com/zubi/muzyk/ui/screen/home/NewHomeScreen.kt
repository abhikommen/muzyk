package com.zubi.muzyk.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.insets.statusBarsPadding
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.R
import com.zubi.muzyk.Routes
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.util.DataState
import com.zubi.muzyk.util.VerticalSpacer
import com.zubi.muzyk.util.withAlpha

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel,
) {

    val scrollState = rememberScrollState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(
                state = scrollState,
            )
    ) {
        HomeScreenBody(navHostController, viewModel)
    }
}

@Composable
fun HomeScreenBody(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {

    val localDataList = viewModel.getRecentSearchLocal.collectAsState(initial = DataState.Loading)
    val trendingChart = viewModel.getTrendingCharts.collectAsState(initial = DataState.Loading)


    Column(

    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(65.dp)
        )
        VerticalSpacer(size = 20)
        Text(
            text = "I want to know\nmore song like..",
            fontSize = 30.sp
        )
        VerticalSpacer(size = 20)
        SearchBox(navHostController, viewModel)
        VerticalSpacer(size = 30)
        HomeDataList("Made For You", localDataList, navHostController, viewModel)
        VerticalSpacer(size = 30)
        HomeDataList(
            "Trending Songs",
            trendingChart,
            navHostController,
            viewModel,
            cellType = CellType.TRENDING_CELL
        )
        VerticalSpacer(size = 30)
//        HomeDataList("Recently Searched", dataList.va, navHostController, viewModel)
    }
}


@Composable
fun HomeDataList(
    heading: String,
    dataList: State<DataState<List<Track>>>,
    navHostController: NavHostController,
    viewModel: MainViewModel,
    cellType: CellType = CellType.NORMAL_CELL,
    modifier: Modifier = Modifier
) {

    DataHeader(heading)
    VerticalSpacer(size = 8)
    when (val result = dataList.value) {
        is DataState.Error -> Text(result.exception.message ?: "Something went wrong.")
        DataState.Loading -> CircularProgressIndicator()
        is DataState.Success -> LazyRow {
            items(result.data) {
                when (cellType) {
                    CellType.NORMAL_CELL -> HomeItemCell(it, navHostController)
                    CellType.TRENDING_CELL -> TrendingCell(it, navHostController)
                }

            }
        }
    }
}

@Composable
fun TrendingCell(
    track: Track?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .width(180.dp)
            .clickable {
                navHostController.navigate(Routes.DETAIL + track?.id)
            }
    ) {
        Image(
            painter = rememberCoilPainter(request = track?.image?.url),
            contentDescription = track?.title,
            modifier = Modifier
                .width(180.dp)
                .shadow(1.dp)
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            track?.title ?: "Na",
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            fontSize = 14.sp,
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
fun HomeItemCell(
    track: Track?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .width(120.dp)
            .clickable {
                navHostController.navigate(Routes.DETAIL + track?.id)
            }
    ) {
        Image(
            painter = rememberCoilPainter(request = track?.image?.url),
            contentDescription = track?.title,
            modifier = Modifier
                .width(120.dp)
                .shadow(1.dp, shape = RoundedCornerShape(4))
                .height(120.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            track?.title ?: "Na",
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            fontSize = 14.sp,
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                heading,
                fontSize = 23.sp,
                color = MaterialTheme.colors.onBackground.withAlpha(0.7f),
                fontWeight = FontWeight.ExtraBold
            )
            VerticalSpacer(size = 8)
            Text(
                "Daily Mixes",
                fontSize = 14.sp,
                color = Color(0xffA5A5A9),
                fontWeight = FontWeight.SemiBold
            )
        }
        Icon(
            Icons.Rounded.ArrowForwardIos,
            tint = Color(0xffA5A5A9),
            contentDescription = "",
            modifier = Modifier.size(12.dp)
        )
    }
}
