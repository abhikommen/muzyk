package com.zubi.muzyk.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.util.DataState


@Composable
fun FavoriteArtist(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val search = remember {
        mainViewModel.getTrendingCharts()
    }

    when (val result = search.value) {
        is DataState.Error -> {

        }
        DataState.Loading -> {

        }
        is DataState.Success -> {
            Box(
                modifier = modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(0.9f)
                    .shadow(
                        2.dp,
                        RoundedCornerShape(20.dp),
                        ambientColor = Color.White
                    )
                    .background(Color.Black)
            ) {

                LazyHorizontalGrid(
                    GridCells.Fixed(2),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .scale(1.5f)
                        .rotate(40f),
                ) {

                    items(result.data) {
                        AsyncImage(
                            model = it.poster,
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(0.5f)
                        )
                    }

                }
            }
        }
    }
}