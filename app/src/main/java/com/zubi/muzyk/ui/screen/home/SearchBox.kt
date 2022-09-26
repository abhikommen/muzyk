package com.zubi.muzyk.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.Routes
import com.zubi.muzyk.data.local.entity.NewTrack
import com.zubi.muzyk.data.local.entity.SearchTracks
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.util.*


@Composable
fun SearchItem(
    track: NewTrack, navHostController: NavHostController, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = scrollFadeImage(context, track.poster),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        HorizontalSpacer(size = 8)
        Column(Modifier.clickable {
            navHostController.navigate(Routes.DETAIL + track.id)
        }) {
            Text(text = track.name, fontSize = 15.sp, color = Color.White)
            VerticalSpacer(size = 5)
            Text(text = track.artists.joinToString(","), color = BDBDBD, fontSize = 14.sp)
        }
    }

}


@Composable
fun SearchBoxItem(
    inputText: MutableState<String>,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val boxHasFocus = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.9f)
                .height(65.dp)
                .padding(2.dp)
                .shadow(2.dp, RoundedCornerShape(50))
                .background(Color(0xff1D1D21))
        ) {
            BasicTextField(value = inputText.value,
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                ),
                onValueChange = {
                    inputText.value = it
                    mainViewModel.searchQuery(it)
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .onFocusChanged {
                        boxHasFocus.value = it.hasFocus
                    })

            if (inputText.value.trim().isEmpty()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .size(22.dp)
                    )
                    HorizontalSpacer(size = 6)
                    Text(
                        "search", color = BDBDBD
                    )
                }
            }
        }
    }
}