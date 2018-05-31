package com.nitok.ict.tweetclient.navigator

import kotlinx.coroutines.experimental.Deferred

interface TweetNavigator {

    fun onStartWriteTweet()

    fun onPostTweet(tweetText: String): Deferred<Unit>
}