package com.ensibuuko.android_dev_coding_assigment.ui.postdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.Comments

class PostDetailsFragment : Fragment() {
    private val commentsList = arrayListOf<Comments>()
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)
        recyclerView = view.findViewById(R.id.rv_comments)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpDummyData()
    }

    private fun setUpRecyclerView() {
        commentsAdapter = CommentsAdapter(requireContext(), commentsList)
        recyclerView.adapter = commentsAdapter
        recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun setUpDummyData() {
        val comments = Comments()
        for (i in 1..5) {
            comments.name = "Jenni"
            comments.body = "More about beans"
            commentsList.add(comments)
        }
    }

}