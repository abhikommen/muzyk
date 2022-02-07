package com.zubi.muzyk

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.zubi.muzyk.data.local.entity.SearchEntity
import com.zubi.muzyk.data.local.entity.SimilarTrackEntity
import com.zubi.muzyk.data.local.entity.Track
import com.zubi.muzyk.data.repo.Repo
import com.zubi.muzyk.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repo: Repo
) : ViewModel() {


    private val _searchQueryList: MutableState<DataState<SearchEntity>> =
        mutableStateOf(value = DataState.Loading)

    var searchQueryList: State<DataState<SearchEntity>> = _searchQueryList

    fun getColorPaletteState(context: Context, url: String): State<Palette?> {
        val mutableStateOfColor = mutableStateOf<Palette?>(null)
        val loader = ImageLoader(context = context)
        val req = ImageRequest.Builder(context)
            .allowHardware(true)
            .allowConversionToBitmap(true)

            .data(url) // demo link
            .target { result ->
                val bitmap = (result as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
                Palette.from(bitmap).generate { palette ->
                    mutableStateOfColor.value = palette
                }
            }
            .build()
        val disposable = loader.enqueue(req)
        return mutableStateOfColor
    }

    fun getSimilarTracksState(trackId: String = "3YUMWmx8EJq0DurfuIwoGh"): State<DataState<SimilarTrackEntity>> {
        val mutableStateTracks =
            mutableStateOf<DataState<SimilarTrackEntity>>(value = DataState.Loading)
        viewModelScope.launch {
            repo.getSimilarTrack(trackId).collect {
                mutableStateTracks.value = it
            }
        }
        return mutableStateTracks
    }

    fun searchQuery(query: String) {
        viewModelScope.launch {
            repo.getSearchQuery(query).debounce(1000)
                .distinctUntilChanged()
                .collect {
                    _searchQueryList.value = it
                }
        }
    }

    fun insertIntoRecentSearch(track: Track) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.insertTrack(track)
            // update view history in server..
        }
    }

    val getRecentSearchLocal = repo.getRecentTracksLocal()

    val getTrendingCharts = repo.getTrendingChart()


    fun requestSpotifySignIn(activity: Activity) {
        val builder =
            AuthorizationRequest.Builder(
                SPOTIFY_CLIENT_ID,
                AuthorizationResponse.Type.TOKEN,
                SPOTIFY_URI_REDIRECT
            )
        builder.setScopes(SPOTIFY_SCOPES)
        val request = builder.build()
        AuthorizationClient.openLoginActivity(activity, SPOTIFY_REQUEST_CODE, request)
    }

    fun saveToken(token: String) {
        SharedPref.saveStringVal(USER_TOKEN, token)
    }

    fun getToken(): String? {
        return SharedPref.getStringVal(USER_TOKEN)
    }

    fun isLogin(): Boolean = getToken() != null
    

}