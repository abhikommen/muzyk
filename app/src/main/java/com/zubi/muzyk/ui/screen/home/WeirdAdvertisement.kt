package com.zubi.muzyk.ui.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.Routes
import com.zubi.muzyk.util.VerticalSpacer
import com.zubi.muzyk.util.fifty
import com.zubi.muzyk.R

@Composable
fun WeirdAdvertisement(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    lazyListScope: LazyListScope,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp)
            .shadow(
                2.dp,
                RoundedCornerShape(20),
                spotColor = Color.White.fifty(),
                ambientColor = Color.White.fifty()
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(0.8f),
                        Color.Black
                    )
                )
            )
            .clickable {
                navHostController.navigate(Routes.WEIRD_INPUT)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append("Weird")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff3BDCB5)
                        )
                    ) {
                        append("\nSpotify \uD83E\uDD18\uD83C\uDFFB")
                    }
                },
                color = Color.White,
                fontSize = 18.sp,
            )
            VerticalSpacer(size = 5)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "enter a sentence.\n" +
                            "get a playlist",
                    color = Color.White,
                    fontSize = 18.sp,
                )
                Icon(
                    Icons.Rounded.ChevronRight,
                    tint = Color.White,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
            VerticalSpacer(size = 5)
        }

        AsyncImage(
            model = R.drawable.crown,
            contentDescription = "Premium",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
                .align(Alignment.TopEnd)
                .animateContentSize()
        )

    }

}