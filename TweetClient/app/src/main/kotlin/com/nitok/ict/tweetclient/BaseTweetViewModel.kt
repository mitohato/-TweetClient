package com.nitok.ict.tweetclient

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField


abstract class BaseTweetViewModel(context: Context) : BaseObservable() {
    
    var applicationContext: Context = context.applicationContext
    
    val tweetText = ObservableField<String>()
    
    var tweetable: Boolean = false
        @Bindable
        get() {
            return true
        }
    
    fun tweet() {
    }
    
    fun start() {
    }
}