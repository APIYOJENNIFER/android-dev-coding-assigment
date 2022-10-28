package com.ensibuuko.android_dev_coding_assigment.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val postsViewModel: PostsViewModel by viewModels()

    private val postsLists = arrayListOf<Posts>()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        recyclerView = view.findViewById(R.id.rv_posts)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostsBinding.bind(view)
        val postsAdapter = PostsAdapter()

        binding.apply {
            rvPosts.apply {
                adapter = postsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        postsViewModel.posts.observe(viewLifecycleOwner){
            postsAdapter.submitList(it)
        }
    } 

}