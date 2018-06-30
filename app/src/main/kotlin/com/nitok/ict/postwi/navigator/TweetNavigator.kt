package com.nitok.ict.postwi.navigator

import kotlinx.coroutines.experimental.Deferred

interface TweetNavigator {

    fun onPostTweet(tweetText: String): Deferred<Unit>

    fun onSelectImage()
}