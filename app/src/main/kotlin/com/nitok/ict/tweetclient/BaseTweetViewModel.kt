package com.nitok.ict.tweetclient

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField


abstract class BaseTweetViewModel(context: Context) : BaseObservable() {
    
    var applicationContext: Context = context.applicationContext
    
    val tweetText = ObservableField<String>()
    
    var tweetable: Boolean = false
        get() = tweetText.get()?.length in 1..140
    
    open fun tweet() {
    }
    
    open fun start() {
    }
}