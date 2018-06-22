package com.nitok.ict.tweetclient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setImageButton()
        setTweetButton()
    }

    private fun setImageButton() {
        val imageButton: ImageButton? = activity?.findViewById(R.id.select_image_button)
        imageButton?.setOnClickListener {
            viewModel.selectImage()
        }
    }

    private fun setTweetButton() {
        val tweetButton: Button? = activity?.findViewById(R.id.tweetButton)
        tweetButton?.setOnClickListener {
            viewModel.apply {
                tweet()
                tweetText = ""
            }
        }
    }

    companion object {

        fun newInstance(): TweetFragment {
            return TweetFragment()
        }
    }
}