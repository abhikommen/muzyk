package com.zubi.muzyk.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PunchClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.util.DataState

@Composable
fun RecentPlayBox(
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
                    .fillMaxWidth()
                    .aspectRatio(0.9f)
                    .shadow(
                        2.dp,
                        RoundedCornerShape(20.dp),
                        ambientColor = Color.White
                    )
                    .background(Color.Black)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            text = "Recent",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "234 songs",
                            fontSize = 13.sp,
                            color = BDBDBD
                        )
                    }

                    Icon(
                        Icons.Outlined.PunchClock,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.BottomStart),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(result.data) {
                        AsyncImage(
                            model = it.poster,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

