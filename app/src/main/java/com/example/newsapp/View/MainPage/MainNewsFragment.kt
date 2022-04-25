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
    fun showGroup(groupId: Int)
    fun subscribe(groupId: Int)
    fun unsubscribe(groupId: Int)
}

class MainNewsFragment(private var user: UserEntity) : Fragment() {

    private lateinit var binding: FragmentMainNewsBinding
    private lateinit var adapter: CategoryAdapter
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

        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.layoutManager = layoutManager
        binding.postRecyclerView.layoutManager =  LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        adapter = CategoryAdapter()
        postAdapter = PostAdapter(object: GroupClickListener{
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
        binding.postRecyclerView.adapter = postAdapter
        binding.categoryRecyclerView.adapter = adapter

        loadCategories()
        loadGroups()

        return binding.root
    }

    private fun loadCategories(){
        val categoryLiveData = viewModel.getCategoryListLiveData()
        categoryLiveData.observe(requireActivity(), Observer {
            categoryLiveData.value?.let {
                adapter.set(it)
            }
        })
        viewModel.getAllCategories()
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