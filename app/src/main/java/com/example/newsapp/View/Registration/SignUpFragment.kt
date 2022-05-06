package com.example.newsapp.View.Registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)


        binding.registerButton.setOnClickListener {
            tryRegister()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }

    private fun tryRegister(){
        val name = binding.personName.text.toString()
        val email = binding.editTextEmailAddress.toString()
        val password = binding.editTextTextPassword.text.toString()
        val user = UserEntity(name, email, password)

        if (!isValid(user))
            return

        val userLiveData = viewModel.getUserLiveData()
        userLiveData.observe(requireActivity(), Observer {
            userLiveData.value?.let {
                if (email == it.email) {

                    val RegistrationActivityCallback = requireActivity() as RegistrationActivityCallback
                    RegistrationActivityCallback.saveUserData(it)

                    RegistrationActivityCallback.showNewsActivity()
                }
            }
        })

        viewModel.register(user)
    }

    private fun isValid(user: UserEntity): Boolean{
        return true
    }
}