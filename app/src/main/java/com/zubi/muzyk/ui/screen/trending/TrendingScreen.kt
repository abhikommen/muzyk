package com.zubi.muzyk.ui.screen.trending

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.R
import com.zubi.muzyk.data.local.entity.ChartsEntity
import com.zubi.muzyk.data.local.entity.Rank
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.util.*

@Composable
fun TrendingScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = APP_BACKGROUND
    ) {
        TrendingScreenBody(mainViewModel = mainViewModel, navHostController = navHostController)
    }

}

@Composable
fun TrendingScreenBody(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val chartsResult = remember {
        mainViewModel.getTrendingCharts()
    }

    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(size = 10)
        AsyncImage(
            model = R.drawable.trending_songs,
            contentDescription = "Heading",
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        VerticalSpacer(size = 30)
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
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(result.data) {
                        RankingItem(it, modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }

        }

    }
}


@Composable
fun RankingItem(
    chartsEntity: ChartsEntity,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = scrollFadeImage(context, chartsEntity.poster),
            contentDescription = chartsEntity.trackName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
        )
        HorizontalSpacer(size = 10)
        Text(
            chartsEntity.rank.currentRank.toString(),
            color = Color.White,
            fontSize = 18.sp
        )
        HorizontalSpacer(size = 8)
        RankText(chartsEntity.rank)
        HorizontalSpacer(size = 15)
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                chartsEntity.trackName,
                color = Color.White,
                fontSize = 15.sp
            )
            Text(
                chartsEntity.artists,
                color = Color.White.fifty(),
                fontSize = 13.sp
            )
        }

    }
}

@Composable
fun RankText(
    rank: Rank,
    modifier: Modifier = Modifier
) {
    var color = BDBDBD

    var icon = Icons.Rounded.ArrowUpward

    when (rank.entryStatus) {
        RANK_MOVED_DOWN -> {
            color = Color(0xffDF7138)
            icon = Icons.Rounded.ArrowDownward
        }

        RANK_MOVED_UP -> {
            color = Color(0xff93F0D2)
            icon = Icons.Rounded.ArrowUpward
        }

        RANK_NO_CHANGE -> {
            icon = Icons.Rounded.Circle
        }

    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(0.1f)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalSpacer(size = 5)
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = color,
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
        )
        HorizontalSpacer(size = 2)
        Text(
            rank.previousRank.toString(),
            fontSize = 10.sp,
            color = color,
        )
        HorizontalSpacer(size = 5)
    }
}
