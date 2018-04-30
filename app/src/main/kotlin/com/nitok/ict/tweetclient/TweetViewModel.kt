package com.nitok.ict.tweetclient

import android.content.Context

class TweetViewModel(context: Context) : BaseTweetViewModel(context) {
    private var tweetNavigator: TweetNavigator? = null
    
    fun setNavigator(tweetNavigator: TweetNavigator) {
        this.tweetNavigator = tweetNavigator
    }
    
    fun onActivityDestroyed() {
        tweetNavigator = null
    }
    
    override fun tweet() {
        super.tweet()
        tweetNavigator?.onPostTweet()
    }
    
    override fun start() {
        tweetNavigator?.onStartWriteTweet()
    }
}