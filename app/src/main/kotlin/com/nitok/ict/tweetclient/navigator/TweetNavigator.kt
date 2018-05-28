package com.nitok.ict.tweetclient.navigator

interface TweetNavigator {

    fun onStartWriteTweet()

    fun onPostTweet(tweetText: String)
}