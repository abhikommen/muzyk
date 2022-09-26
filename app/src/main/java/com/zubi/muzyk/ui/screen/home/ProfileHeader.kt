package com.zubi.muzyk.ui.screen.home

import android.text.style.StyleSpan
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zubi.muzyk.MainViewModel
import com.zubi.muzyk.R
import com.zubi.muzyk.Routes
import com.zubi.muzyk.ui.theme.BDBDBD
import okhttp3.Route

@Composable
fun ProfileHeader(
    viewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = BDBDBD, fontWeight = FontWeight.Thin)) {
                append("Missed you,\n")
            }
            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.SemiBold)) {
                append("Abhi")
            }
        }, fontSize = 20.sp)
        AsyncImage(
            model = "https://i.pinimg.com/736x/fa/78/cf/fa78cf6835aac76fc2dce2d6f647fd3f.jpg",
            contentDescription = "Profile Picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(55.dp)
                .clickable {
                    navHostController.navigate(Routes.PROFILE)
                }
        )
    }
}
