package com.nitok.ict.tweetclient

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nitok.ict.tweetclient.databinding.FragmentTweetBinding

class TweetFragment : Fragment() {
    private lateinit var viewModel: TweetViewModel

    fun setViewModel(viewModel: TweetViewModel) {
        this.viewModel = viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_tweet,
            container,
            false
        )

        val fragmentTweetBinding = FragmentTweetBinding.bind(view)
        fragmentTweetBinding.viewmodel = viewModel

        return view
    }

    companion object {

        fun newInstance(): TweetFragment {
            return TweetFragment()
        }
    }
}