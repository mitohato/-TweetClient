package com.nitok.ict.tweetclient.viewmodel

import android.content.Context
import com.nitok.ict.tweetclient.navigator.TweetNavigator

class TweetViewModel(context: Context) : BaseTweetViewModel(context) {
    private var tweetNavigator: TweetNavigator? = null

    fun setNavigator(tweetNavigator: TweetNavigator) {
        this.tweetNavigator = tweetNavigator
    }

    fun onActivityDestroyed() {
        tweetNavigator = null
    }

    override fun tweet(tweetText: String) {
        super.tweet(tweetText)
        tweetNavigator?.onPostTweet(tweetText)
    }
}