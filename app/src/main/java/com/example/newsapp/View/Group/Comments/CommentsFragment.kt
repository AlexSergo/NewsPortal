package com.example.newsapp.View.Group.Comments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.LocalDataSource.Model.Comment.CommentEntity
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.FragmentCommentsBinding
import kotlinx.coroutines.delay


class CommentsFragment(postId: Int, user: UserEntity) : Fragment() {

    private val postId = postId
    private val user = user
    private lateinit var binding: FragmentCommentsBinding
    private lateinit var adapter: CommentsAdapter
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCommentsBinding.inflate(layoutInflater)
        viewModelFactory =
            NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        binding.commentsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = CommentsAdapter()

        showComments(binding)

        binding.commentsRecyclerview.adapter = adapter

        binding.sendCommentButton.setOnClickListener {
            if (binding.commentEditText.text.toString() != ""){
                viewModel.createComment(CommentEntity(postId, binding.commentEditText.text.toString(), "2020-01-03"), user.token)
                binding.commentEditText.setText("")
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken , 0)
                viewModel.getCommentLiveData().observe(requireActivity(), Observer {
                    showComments(binding)
                })
            }
        }
        binding.backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        return binding.root
    }

    private fun showComments(binding: FragmentCommentsBinding) {
        viewModel.getCommentsForPost(postId, user.token)
        viewModel.getCommentsLiveData().observe(requireActivity(), Observer {
            it.let {
                binding.postComment.text = it.size.toString() + " комментариев"
                adapter.set(it.asReversed())
            }
        }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(postId: Int, user: UserEntity) = CommentsFragment(postId, user)
    }
}