package com.nitok.ict.tweetclient

import android.annotation.SuppressLint
import android.content.Context
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

object TwitterUtils {
    private const val TOKEN = "token"
    private const val TOKEN_SECRET = "token_secret"
    private const val PREF_NAME = "twitter_access_token"

    /**
     * Twitterインスタンスを取得します。アクセストークンが保存されていれば自動的にセットします。
     *
     * @param context
     * *
     * @return
     */
    fun getTwitterInstance(context: Context?): Twitter? {
        if (context == null) {
            return null
        }
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
        /**
         * アクセストークンをプリファレンスに保存します。
         *
         * @param context
         * *
         * @param accessToken
         */
    fun storeAccessToken(
        context: Context?,
        accessToken: AccessToken
    ) {
        context?.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )?.edit()?.apply {

            putString(
                TOKEN,
                accessToken.token
            )

            putString(
                TOKEN_SECRET,
                accessToken.tokenSecret
            )
        }?.apply()
    }

    /**
     * アクセストークンをプリファレンスから読み込みます。

     * @param context
     * *
     * @return
     */
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

    /**
     * アクセストークンが存在する場合はtrueを返します。
     * @return
     */
    fun hasAccessToken(context: Context): Boolean = loadAccessToken(context) != null
}
