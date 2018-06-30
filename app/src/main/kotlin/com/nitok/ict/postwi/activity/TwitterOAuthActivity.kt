package com.nitok.ict.postwi.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nitok.ict.postwi.R
import com.nitok.ict.postwi.utils.TwitterUtils
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class TwitterOAuthActivity : AppCompatActivity() {
    private val callbackUrl: String by lazy { getString(R.string.twitter_callback_url) }
    private val twitter: Twitter by lazy { TwitterUtils.getTwitterInstance(this) }
    private val requestToken: RequestToken by lazy { twitter.getOAuthRequestToken(callbackUrl) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startAuthorize()
    }

    private fun startAuthorize() {
        val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg p0: Void?): String? = try {
                requestToken.authorizationURL
            } catch (e: TwitterException) {
                e.printStackTrace()
                null
            }

            override fun onPostExecute(result: String?) {
                if (result != null) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(result)
                    )
                    startActivity(intent)
                } else {
                    toast(R.string.error)
                }
            }
        }
        task.execute()
    }

    override fun onNewIntent(intent: Intent?) {
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
                    toast(R.string.error_auth)
                }
            }
        }
        task.execute(verifier)

        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun successOAuth(token: AccessToken) {
        TwitterUtils.storeAccessToken(
            this,
            token
        )

        startActivity<MainActivity>()
        finish()
    }
}
