package com.nitok.ict.tweetclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity(), TweetNavigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    override fun onStartWriteTweet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun onPostTweet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    companion object {
        
        val TWEET_VIEWMODEL_TAG = "TWEET_VIEWMODEL_TAG"
        
        val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
        
        val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3
    }
}
