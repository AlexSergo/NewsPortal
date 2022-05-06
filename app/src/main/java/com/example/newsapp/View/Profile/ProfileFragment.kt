package com.example.newsapp.View.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.R
import com.example.newsapp.View.MainPage.MainNewsFragment
import com.example.newsapp.View.NewsActivity
import com.example.newsapp.View.NewsActivityCallback
import com.example.newsapp.databinding.FragmentProfileBinding
import com.example.newsapp.databinding.PostItemBinding

class ProfileFragment(private var user: UserEntity) : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    companion object {
        @JvmStatic
        fun newInstance(user: UserEntity) = ProfileFragment(user)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        binding.userName.text = user.name
        return binding.root
    }
}