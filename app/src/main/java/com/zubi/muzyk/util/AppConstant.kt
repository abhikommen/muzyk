package com.zubi.muzyk.util

const val SPOTIFY_CLIENT_ID = "398077ba1bb14e3d83077235afb4e633"
const val SPOTIFY_URI_REDIRECT = "muzyk://callback"
const val USER_TOKEN = "@user-token"
const val SPOTIFY_REQUEST_CODE = 1337

val SPOTIFY_SCOPES = arrayOf(
    "playlist-modify-private",
    "playlist-modify-public",
    "user-read-private",
    "user-read-email",
    "user-top-read",
    "user-read-recently-played",
    "user-library-read",
    "user-library-modify",
    "user-follow-read",
    "playlist-read-collaborative",

)

const val RANK_NO_CHANGE = "NO_CHANGE"
const val RANK_MOVED_UP = "MOVED_UP"
const val RANK_MOVED_DOWN = "MOVED_DOWN"