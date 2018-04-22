package com.nitok.ict.tweetclient

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField


class TweetViewModel : BaseObservable() {
    
    val tweetText = ObservableField<String>()
    
    var tweetable: Boolean = false
        @Bindable
        get() {
            return true
        }
}