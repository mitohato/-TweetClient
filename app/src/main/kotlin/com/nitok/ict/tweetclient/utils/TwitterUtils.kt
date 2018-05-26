package com.nitok.ict.tweetclient.utils

import android.annotation.SuppressLint
import android.content.Context
import com.nitok.ict.tweetclient.R
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

object TwitterUtils {
    private const val TOKEN = "token"
    private const val TOKEN_SECRET = "token_secret"
    private const val PREF_NAME = "twitter_access_token"

    fun getTwitterInstance(context: Context): Twitter {
        val consumerKey = context.getString(R.string.consumer_key)
        val consumerSecret = context.getString(R.string.consumer_secret_key)
        val twitter = TwitterFactory().instance

        twitter.setOAuthConsumer(
            consumerKey,
            consumerSecret
        )

        if (hasAccessToken(context)) {
            twitter.oAuthAccessToken = loadAccessToken(context)
        }
        return twitter
    }

    @SuppressLint("CommitPrefEdits")
    fun storeAccessToken(
        context: Context,
        accessToken: AccessToken
    ) {
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        ).edit().apply {

            putString(
                TOKEN,
                accessToken.token
            )

            putString(
                TOKEN_SECRET,
                accessToken.tokenSecret
            )
        }.apply()
    }

    fun loadAccessToken(context: Context): AccessToken? {
        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val token = preferences.getString(
            TOKEN,
            null
        )

        val tokenSecret = preferences.getString(
            TOKEN_SECRET,
            null
        )

        return if (token != null && tokenSecret != null) {
            AccessToken(
                token,
                tokenSecret
            )
        } else {
            null
        }
    }

    fun hasAccessToken(context: Context): Boolean = loadAccessToken(context) != null
}
