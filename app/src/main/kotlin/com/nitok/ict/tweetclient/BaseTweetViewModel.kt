package com.nitok.ict.tweetclient

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField


abstract class BaseTweetViewModel(context: Context) : BaseObservable() {
    
    var applicationContext: Context = context.applicationContext
    
    val tweetText = ObservableField<String>()
    
    var tweetable: Boolean = false
        get() {
            return tweetText.get()?.length in 0..140
        }
    
    open fun tweet() {
    }
    
    open fun start() {
    }
}