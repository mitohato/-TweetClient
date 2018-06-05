package com.nitok.ict.tweetclient.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

abstract class BaseTweetViewModel(context: Context) : BaseObservable() {

    private var applicationContext: Context = context.applicationContext

    var selectImageNum: Int = 0

    @Bindable
    var selectableImage: Boolean = true
        get() {
            notifyChange()
            return selectImageNum < 4
        }

    @Bindable
    var tweetText: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @Bindable
    var tweetable: Boolean = false
        get() =
            tweetText.length in 1..140 ||
                selectImageNum != 0

    @Bindable
    var tweetLength: String = ""
        get() = tweetText.length.toString()

    var icon: Drawable? = null

    open fun tweet(tweetText: String) {
    }

    open fun selectImage() {
    }
}