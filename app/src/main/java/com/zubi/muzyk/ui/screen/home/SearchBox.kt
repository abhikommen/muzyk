package com.zubi.muzyk.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.Routes
import com.zubi.muzyk.data.local.entity.SearchEntity
import com.zubi.muzyk.data.local.entity.SearchTracks
import com.zubi.muzyk.ui.theme.GREY
import com.zubi.muzyk.util.DataState
import com.zubi.muzyk.util.HorizontalSpacer
import com.zubi.muzyk.util.VerticalSpacer
import com.zubi.muzyk.util.withAlpha

@Composable
fun SearchBox(
    navHostController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val inputText = remember {
        mutableStateOf("")
    }

    val resultState = remember {
        viewModel.searchQueryList
    }

    val boxHasFocus = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.background(
            color = GREY,
            shape = RoundedCornerShape(10)
        ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = inputText.value,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin
            ),
            onValueChange = {
                inputText.value = it
                viewModel.searchQuery(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    boxHasFocus.value = it.hasFocus
                }
                .padding(20.dp)
        )

        if (inputText.value.trim().isEmpty() && !boxHasFocus.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground.withAlpha(0.8f)
                )
                HorizontalSpacer(size = 8)
                Text(
                    "Song or artis..",
                    color = MaterialTheme.colors.onBackground.withAlpha(0.8f)
                )
            }
        }
    }
    if (inputText.value.trim().isNotEmpty() && boxHasFocus.value) {
        SearchResultBox(resultState, navHostController, viewModel)
    }
}

@Composable
fun SearchResultBox(
    resultState: State<DataState<SearchEntity>>,
    navHostController: NavHostController,
    viewModel: MainViewModel
) {

    when (val result = resultState.value) {
        is DataState.Error -> Text(result.exception.message ?: "No result found")
        DataState.Loading -> CircularProgressIndicator(modifier = Modifier.size(12.dp))
        is DataState.Success -> LazyColumn {
            items(result.data.searchTracks) {
                SearchItem(it, navHostController)
            }
        }
    }

}

@Composable
fun SearchItem(
    searchTracks: SearchTracks,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(vertical = 5.dp)
            .clickable {
                navHostController.navigate(Routes.DETAIL + searchTracks.id)
            }
    ) {
        Text(text = searchTracks.title)
        VerticalSpacer(size = 5)
        Text(text = searchTracks.artists.toString())
        VerticalSpacer(size = 5)
        Divider()
    }
}
