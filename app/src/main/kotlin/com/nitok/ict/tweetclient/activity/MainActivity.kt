package com.nitok.ict.tweetclient.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
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
import twitter4j.Twitter
import twitter4j.TwitterException
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity(), TweetNavigator {
    private var tweetViewModel: TweetViewModel? = null
    private lateinit var twitter: Twitter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        twitter = TwitterUtils.getTwitterInstance(this)

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
            transaction.add(
                R.id.content_frame,
                tweetFragment
            )
            transaction.commit()
        }

        return tweetFragment
    }

    private fun setMediaIds(statusUpdate: StatusUpdate, mediaIds: LongArray?): StatusUpdate {
        if (mediaIds == null) {
            return statusUpdate
        }

        for (id in mediaIds) {
            statusUpdate.setMediaIds(id)
        }

        return statusUpdate
    }

    override fun onPostTweet(tweetText: String): Deferred<Unit> = async(CommonPool) {
        setResult(TWEET_RESULT_OK)

        try {
            twitter.updateStatus(tweetText)
        } catch (e: TwitterException) {
            e.printStackTrace()
        }
        return@async
    }

    override fun onSelectImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(
            intent,
            RESULT_PICK_IMAGEFILE
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == RESULT_PICK_IMAGEFILE &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            val uri = data.data
            val file = File(uri.toString())
            val media = twitter.uploadMedia(file)

            try {
                val bmp = getBitmapFromUri(uri)

                // ここMVVM的にだいぶ黒に近いグレー
                tweetViewModel?.let {
                    findViewById<ImageView>(imageId[it.selectImageNum]).setImageBitmap(bmp)
                    it.mediaIds[it.selectImageNum++] = media.mediaId
                    it.selectImageNum++
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(
            uri,
            "r"
        )
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor

        val image = try {
            BitmapFactory.decodeFileDescriptor(fileDescriptor)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
        parcelFileDescriptor.close()

        return image
    }

    override fun onDestroy() {
        tweetViewModel?.onActivityDestroyed()
        super.onDestroy()
    }

    companion object {

        const val RESULT_PICK_IMAGEFILE = Activity.RESULT_FIRST_USER + 10

        const val TWEET_VIEWMODEL_TAG = "TWEET_VIEWMODEL_TAG"

        const val TWEET_RESULT_OK = Activity.RESULT_FIRST_USER + 3

        val imageId = arrayListOf(
            R.id.image1,
            R.id.image2,
            R.id.image3,
            R.id.image4
        )
    }
}
