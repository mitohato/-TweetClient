package com.nitok.ict.tweetclient

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity(), TweetNavigator {
    private lateinit var tweetViewModel: TweetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tweetFragment = findOrCreateViewFragment()

        tweetViewModel = findOrCreateViewModel()
        tweetViewModel.setNavigator(this)
        tweetFragment.setViewModel(tweetViewModel)

        if (!TwitterUtils.hasAccessToken(this)) {
            val twitterOAuthFragment: Fragment = TwitterOAuthFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.content_frame, twitterOAuthFragment)
            transaction.commit()
        }
    }

    private fun findOrCreateViewModel(): TweetViewModel {

        val retainedViewModel =
            supportFragmentManager.findFragmentByTag(TWEET_VIEWMODEL_TAG) as ViewModelHolder<*>?

        return if (retainedViewModel?.viewmodel != null) {
            retainedViewModel.viewmodel as TweetViewModel
        } else {
            val viewModel = TweetViewModel(this)

            ActivityUtils.addFragmentToActivity(
                supportFragmentManager,
                ViewModelHolder.createContainer(viewModel),
                TWEET_VIEWMODEL_TAG
            )

            viewModel
        }
    }

    //
    private fun findOrCreateViewFragment(): TweetFragment {
        var tweetFragment: TweetFragment? =
            supportFragmentManager.findFragmentById(R.id.content_frame) as TweetFragment?

        if (tweetFragment == null) {
            tweetFragment = TweetFragment.newInstance()

            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.content_frame, tweetFragment)
            transaction.commit()
        }

        return tweetFragment
    }

    override fun onStartWriteTweet() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostTweet() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        tweetViewModel.onActivityDestroyed()
        super.onDestroy()
    }

    companion object {

        const val TWEET_VIEWMODEL_TAG = "TWEET_VIEWMODEL_TAG"

        val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3
    }
}
