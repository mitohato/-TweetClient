package com.nitok.ict.tweetclient

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nitok.ict.tweetclient.databinding.FragmentTweetBinding

class TweetFragment : Fragment() {
    private lateinit var viewModel: TweetViewModel
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
                R.layout.activity_main,
                container, false
        )
        
        val activityMainBinding = DataBindingUtil.bind<FragmentTweetBinding>(view)
        activityMainBinding?.viewmodel = viewModel
        
        return view
    }
    
    companion object {
        
        fun newInstance(): TweetFragment {
            return TweetFragment()
        }
    }
    
}