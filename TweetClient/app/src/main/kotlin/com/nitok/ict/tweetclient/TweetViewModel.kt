package com.nitok.ict.tweetclient

import android.content.Context

class TweetViewModel(context: Context) : BaseTweetViewModel(context) {
    private var tweetNavigator: TweetNavigator? = null
    
    
    fun onActivityDestroyed() {
        tweetNavigator = null
    }
    
}