package com.example.newsapp.View.MainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.View.NewsActivity
import com.example.newsapp.View.NewsActivityCallback
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.FragmentMainNewsBinding

interface GroupClickListener{
    fun subscribe(groupId: Int)
    fun unsubscribe(groupId: Int)
    fun showGroup(groupId: Int)
}

interface PostClickListener{
    fun likePost(postId: Int)
    fun dislikePost(likeId: Int)
    fun showComments(postId: Int)
    fun showGroup(groupId: Int)
}

class MainNewsFragment(private var user: UserEntity) : Fragment() {

    private lateinit var binding: FragmentMainNewsBinding
    private lateinit var postAdapter: PostAdapter
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainNewsBinding.inflate(layoutInflater)
        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        binding.postRecyclerView.layoutManager =  LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        postAdapter = PostAdapter(object: PostClickListener{
            override fun likePost(postId: Int) {
                viewModel.likePost(postId, user.token)
            }

            override fun dislikePost(likeId: Int) {
                viewModel.dislikePost(likeId, user.token)
            }

            override fun showComments(postId: Int) {
                val activityCallback = requireActivity() as NewsActivityCallback
                activityCallback.showCommentFragment(postId)
            }

            override fun showGroup(groupId: Int) {
                viewModel.getGroupLiveData().observe(requireActivity(), Observer {
                    it.let {
                        val activityCallback = requireActivity() as NewsActivityCallback
                        activityCallback.showGroupFragment(it)
                    }
                })
            }

        })
        binding.postRecyclerView.adapter = postAdapter

        loadGroups()

        return binding.root
    }

    private fun loadGroups(){
        val groupsLiveData = viewModel.getGroupListLiveData()
        val groupLiveData = viewModel.getGroupLiveData()
        println(user.token)

        groupsLiveData.observe(requireActivity(), Observer {
            groupsLiveData.value?.let {
                viewModel.getGroupById(it[0].id, user.token)
            }
        })

        groupLiveData.observe(requireActivity(), Observer {
            groupLiveData.value?.let {
                postAdapter.set(it.posts)
            }
        })
        viewModel.getGroupsByCategoryId(12)
    }

    companion object {
        @JvmStatic
        fun newInstance(user: UserEntity) = MainNewsFragment(user)
    }
}