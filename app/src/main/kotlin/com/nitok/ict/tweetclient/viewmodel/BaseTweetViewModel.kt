package com.nitok.ict.tweetclient.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

abstract class BaseTweetViewModel(context: Context) : BaseObservable() {

    private var applicationContext: Context = context.applicationContext

    @Bindable
    var tweetText: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @Bindable
    var tweetable: Boolean = false
        get() = tweetText.length in 1..140

    @Bindable
    var tweetLength: String = ""
        get() = tweetText.length.toString()

    var icon: Drawable? = null

    open fun tweet(tweetText: String) {
    }
}