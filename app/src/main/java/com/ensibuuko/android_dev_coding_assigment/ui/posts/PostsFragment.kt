package com.ensibuuko.android_dev_coding_assigment.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.Posts

class PostsFragment : Fragment() {

    private val postsLists = arrayListOf<Posts>()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_posts, container, false)
        recyclerView = view.findViewById(R.id.rv_posts)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setDummyData()
    }

    private fun setUpRecyclerView(){
        postsAdapter = PostsAdapter(requireContext(), postsLists)
        recyclerView.adapter = postsAdapter
        recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun setDummyData(){
        val posts = Posts()
        for (i in 1..5){
            posts.title = "Beans Prices"
            posts.body = "I cannot believe that beans has become..."

            postsLists.add(posts)
        }
    }

}