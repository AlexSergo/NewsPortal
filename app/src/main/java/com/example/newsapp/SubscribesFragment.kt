package com.example.newsapp

import android.os.Bundle
import android.os.UserHandle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.View.Group.GroupsAdapter
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.View.NewsActivityCallback
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.FragmentSubscribesBinding


class SubscribesFragment(private var user: UserEntity) : Fragment() {

    private lateinit var binding: FragmentSubscribesBinding
    private lateinit var adapter: GroupsAdapter
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSubscribesBinding.inflate(layoutInflater)
        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        adapter = GroupsAdapter(object: GroupClickListener{
            override fun showGroup(groupId: Int) {
                viewModel.getGroupLiveData().observe(requireActivity(), Observer {
                    it.let {
                        val activityCallback = requireActivity() as NewsActivityCallback
                        activityCallback.showGroupFragment(it)
                    }
                })
                viewModel.getGroupById(groupId, user.token)
            }

            override fun subscribe(groupId: Int) {
            }

            override fun unsubscribe(groupId: Int) {
            }

        })

        binding.groupList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.groupList.adapter = adapter

        showSubscribes()

        return binding.root
    }

    private fun showSubscribes() {
        viewModel.getSubscribesLiveData().observe(requireActivity(), Observer {
            it?.let {
                adapter.set(it)
            }
        })
        viewModel.getSubscribes(user.token)
    }

    companion object {
        @JvmStatic
        fun newInstance(user: UserEntity) = SubscribesFragment(user)
    }
}