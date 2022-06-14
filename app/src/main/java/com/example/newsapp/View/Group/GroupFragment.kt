package com.example.newsapp.View.Group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.R
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.View.MainPage.MainNewsFragment
import com.example.newsapp.View.MainPage.PostClickListener
import com.example.newsapp.View.NewsActivity
import com.example.newsapp.View.NewsActivityCallback
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.FragmentGroupBinding

class GroupFragment(private var group: GroupEntity, private var user: UserEntity) : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(group: GroupEntity, user: UserEntity) = GroupFragment(group, user)
    }

    private lateinit var binding: FragmentGroupBinding
    private lateinit var adapter: CurrentGroupAdapter
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGroupBinding.inflate(layoutInflater)
        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)
        binding.groupRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        val activityCallback = requireActivity() as NewsActivityCallback

        adapter = CurrentGroupAdapter(object: GroupClickListener {

            override fun showGroup(groupId: Int) {
                viewModel.getGroupLiveData().observe(requireActivity(), Observer {
                    it.let {
                        activityCallback.showGroupFragment(it)
                    }
                })
                viewModel.getGroupById(groupId, user.token)
            }

            override fun subscribe(groupId: Int) {
                viewModel.subscribe(groupId, user.token)
            }

            override fun unsubscribe(groupId: Int) {
                viewModel.unsubscribe(groupId, user.token)
            }

        }, object: PostClickListener{
            override fun likePost(postId: Int) {
                viewModel.likePost(postId, user.token)
            }

            override fun dislikePost(likeId: Int) {
                viewModel.dislikePost(likeId, user.token)
            }

            override fun showComments(postId: Int) {
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

        binding.groupRecyclerView.adapter = adapter
        viewModel.getSubscribes(user.token)
        viewModel.getSubscribesLiveData().observe(requireActivity(), Observer {
            it?.let { groups->
                var result = groups.filter { it.id == group.id }
                if (result.isNotEmpty())
                    adapter.set(group, true)
                else
                    adapter.set(group, false)
            }
        })

        return binding.root
    }

}