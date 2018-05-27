package com.nitok.ict.tweetclient.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.nitok.ict.tweetclient.R
import com.nitok.ict.tweetclient.databinding.FragmentTweetBinding
import com.nitok.ict.tweetclient.viewmodel.TweetViewModel

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

        setTweetButton()

        return view
    }

    private fun setTweetButton() {
        val tweetButton: Button = activity.findViewById(R.id.tweetButton)
        tweetButton.setOnClickListener {
            viewModel.tweet()
        }
    }

    companion object {

        fun newInstance(): TweetFragment {
            return TweetFragment()
        }
    }
}