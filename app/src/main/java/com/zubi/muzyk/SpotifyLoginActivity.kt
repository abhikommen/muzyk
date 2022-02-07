package com.zubi.muzyk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spotify.sdk.android.auth.*
import com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE

import com.spotify.sdk.android.auth.AccountsQueryParameters.REDIRECT_URI

import com.spotify.sdk.android.auth.AccountsQueryParameters.CLIENT_ID
import com.spotify.sdk.android.auth.AuthorizationClient

import com.spotify.sdk.android.auth.AuthorizationResponse




private const val REQUEST_CODE = 1337
private const val URI_REDIRECT = "muzyk://callback"


class SpotifyLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify_login)

        val builder =
            AuthorizationRequest.Builder("398077ba1bb14e3d83077235afb4e633", AuthorizationResponse.Type.TOKEN, URI_REDIRECT)

        builder.setScopes(arrayOf("streaming"))
        val request = builder.build()

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {}
                AuthorizationResponse.Type.ERROR -> {}
                else -> {}
            }
        }
    }

}