package com.zubi.muzyk.ui.screen.home

import com.zubi.muzyk.R



data class MoodEntity(
    val name : String,
    val icon : Int
)

val moodList = listOf<MoodEntity>(
    MoodEntity("Happy", R.drawable.happy),
    MoodEntity("Sad", R.drawable.sad),
    MoodEntity("Focus", R.drawable.focus),
    MoodEntity("Party", R.drawable.party),
    MoodEntity("Romance", R.drawable.romance),
    MoodEntity("Sleeping", R.drawable.sleeping),
)