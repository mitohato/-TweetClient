package com.nitok.ict.tweetclient

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable

abstract class BaseTweetViewModel(context: Context) : BaseObservable() {
    
    private var applicationContext: Context = context.applicationContext
    
    @Bindable
    var tweetText: String = ""
    
    var tweetable: Boolean = false
        get() = tweetText.length in 1..140
    
    open fun tweet() {
    }
    
    open fun start() {
    }
}