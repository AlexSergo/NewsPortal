package com.example.newsapp.View.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.View.Group.SubscribeClickListener
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.FragmentMainNewsBinding
import com.example.newsapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        val adapter = SearchAdapter(object : GroupClickListener{
            override fun subscribe(groupId: Int) {
                TODO("Not yet implemented")
            }

            override fun unsubscribe(groupId: Int) {
                TODO("Not yet implemented")
            }

            override fun showGroup(groupId: Int) {
                TODO("Not yet implemented")
            }

        },
        object : SubscribeClickListener {
            override fun changeSubscribeState(isSigned: Boolean) {
                TODO("Not yet implemented")
            }

        })

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = adapter
        adapter
        return binding.root
    }

}