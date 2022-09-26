package com.zubi.muzyk.ui.screen.weird

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.statusBarsPadding
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.Routes
import com.zubi.muzyk.ui.theme.APP_BACKGROUND
import com.zubi.muzyk.ui.theme.BDBDBD
import com.zubi.muzyk.ui.theme.SPOTIFY_GREEN
import com.zubi.muzyk.util.*

@Composable
fun WeirdScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Surface(
        color = APP_BACKGROUND,
        modifier = Modifier
            .fillMaxSize()
    ) {
        WeirdScreenBodyTwo(mainViewModel = mainViewModel, navHostController = navHostController)
    }

}

@Composable
fun WeirdScreenBodyTwo(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val inputStringState = remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        Column {
            Text(
                buildAnnotatedString {
                    append("Weird")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff3BDCB5)
                        )
                    ) {
                        append("\nSpotify")
                    }
                },
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 30.sp,
            )

            VerticalSpacer(size = 30)

            Text(
                text = "enter any sentence and \na playlist will be generated.",
                fontSize = 18.sp,
                color = Color.White.sixty(),
            )

            VerticalSpacer(size = 30)
            InputString(inputStringState)
        }
        LetsGoButton(
            "i'm done",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
        ) {
            navHostController.navigate(Routes.WEIRD_RESULT + inputStringState.value)
        }
    }

}


@Composable
fun InputString(
    inputStringState: MutableState<String>,
    modifier: Modifier = Modifier
) {

    val showHint = remember {
        mutableStateOf(true)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(
                2.dp,
                RoundedCornerShape(8),
                spotColor = Color.White.fifty(),
                ambientColor = Color.White.fifty()
            )
            .background(
                blendColors(APP_BACKGROUND, Color.White, 0.9f)
            )
    ) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(
                    0.5.dp
                )
        ) {
            if (inputStringState.value.isEmpty()) {
                Text(
                    text = "Your attempt at being creative goes here...",
                    color = BDBDBD,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
            BasicTextField(
                value = inputStringState.value,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(12.dp)
                    .onFocusChanged {
                        showHint.value = !it.hasFocus
                    },
                onValueChange = {
                    inputStringState.value = it
                },
                cursorBrush = SolidColor(SPOTIFY_GREEN)
            )
        }
    }
}


@Composable
fun LetsGoButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                Color.Gray.fifty()
            )
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .offset(
                    x = (-5).dp,
                    y = (-5).dp
                )
                .background(SPOTIFY_GREEN)
        ) {
            Text(
                text, fontSize = 15.sp,
                color = APP_BACKGROUND,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(20.dp)
            )
        }
    }

}

