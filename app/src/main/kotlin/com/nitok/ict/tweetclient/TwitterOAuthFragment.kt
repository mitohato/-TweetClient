package com.nitok.ict.tweetclient

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.support.v4.app.Fragment
import androidx.core.net.toUri
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class TwitterOAuthFragment : Fragment() {
    private val callbackUrl: String by lazy { getString(R.string.twitter_callback_url) }
    private val twitter: Twitter by lazy { TwitterUtils.getTwitterInstance(this.activity.applicationContext) }
    private val requestToken: RequestToken by lazy { twitter.getOAuthRequestToken(callbackUrl) }

    fun startAuthorize() {
        val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String? = try {
                requestToken.authorizationURL
            } catch (e: TwitterException) {
                e.printStackTrace()
                null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                if (result != null) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        result.toUri()
                    )
                    startActivity(intent)
                } else {
                    toast(getString(R.string.error))
                }
            }
        }

        task.execute()
    }

    fun onNewIntent(intent: Intent?) {
        val verifier = intent?.data?.getQueryParameter("oauth_verifier") ?: return

        val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, Void, AccessToken>() {
            override fun doInBackground(vararg p0: String?): AccessToken? {
                try {
                    return twitter.getOAuthAccessToken(
                        requestToken,
                        p0[0]
                    )
                } catch (e: TwitterException) {
                    e.printStackTrace()
                }
                return null
            }

            override fun onPostExecute(result: AccessToken?) {
                if (result != null) {
                    successOAuth(result)
                } else {
                    toast(getString(R.string.error_auth))
                }
            }
        }
        task.execute(verifier)
    }

    private fun successOAuth(token: AccessToken) {
        TwitterUtils.storeAccessToken(
            this.activity.applicationContext,
            token
        )

        startActivity<MainActivity>()
        fragmentManager.beginTransaction().remove(this).commit()
    }
}
