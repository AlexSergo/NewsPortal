package com.example.newsapp.View

import android.os.Binder
import android.os.Bundle
import android.os.UserHandle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Model.Category.CategoryEntity
import com.example.newsapp.Model.User.UserEntity
import com.example.newsapp.R
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.FragmentMainNewsBinding


class MainNewsFragment(user: UserEntity) : Fragment() {

    private lateinit var binding: FragmentMainNewsBinding
    private lateinit var adapter: CategoryAdapter
    private lateinit var postAdapter: PostAdapter
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel
    private var user: UserEntity = user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainNewsBinding.inflate(layoutInflater)

        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.layoutManager = layoutManager
        binding.postRecyclerView.layoutManager =  LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        adapter = CategoryAdapter()
        postAdapter = PostAdapter()
        binding.postRecyclerView.adapter = postAdapter
        binding.categoryRecyclerView.adapter = adapter

        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

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