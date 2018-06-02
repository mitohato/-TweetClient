package com.nitok.ict.tweetclient.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nitok.ict.tweetclient.R
import com.nitok.ict.tweetclient.fragment.TweetFragment
import com.nitok.ict.tweetclient.navigator.TweetNavigator
import com.nitok.ict.tweetclient.utils.ActivityUtils
import com.nitok.ict.tweetclient.utils.TwitterUtils
import com.nitok.ict.tweetclient.viewmodel.TweetViewModel
import com.nitok.ict.tweetclient.viewmodel.ViewModelHolder
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.startActivity
import twitter4j.TwitterException

class MainActivity : AppCompatActivity(), TweetNavigator {
    private var tweetViewModel: TweetViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!TwitterUtils.hasAccessToken(this)) {
            startActivity<TwitterOAuthActivity>()
            finish()
        } else {
            val tweetFragment = findOrCreateViewFragment()

            tweetViewModel = findOrCreateViewModel()
            tweetViewModel?.let {
                it.setNavigator(this)
                tweetFragment.setViewModel(it)
            }
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

    private fun findOrCreateViewFragment(): TweetFragment {
        var tweetFragment: TweetFragment? =
            supportFragmentManager.findFragmentById(R.id.content_frame) as TweetFragment?

        if (tweetFragment == null) {
            tweetFragment = TweetFragment.newInstance()

            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.content_frame, tweetFragment)
            transaction.commit()
        }

        return tweetFragment
    }

    override fun onPostTweet(tweetText: String): Deferred<Unit> = async(CommonPool) {
        setResult(TWEET_RESULT_OK)
        val twitter = TwitterUtils.getTwitterInstance(this@MainActivity)

        try {
            twitter.updateStatus(tweetText)
        } catch (e: TwitterException) {
            e.printStackTrace()
        }
        return@async
    }

    override fun onSelectImage() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        tweetViewModel?.onActivityDestroyed()
        super.onDestroy()
    }

    companion object {

        const val TWEET_VIEWMODEL_TAG = "TWEET_VIEWMODEL_TAG"

        const val TWEET_RESULT_OK = Activity.RESULT_FIRST_USER + 3
    }
}
