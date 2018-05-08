package com.nitok.ict.tweetclient

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable

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
    
    open fun tweet() {
    }
    
    open fun start() {
    }
}