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
import com.example.newsapp.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        binding.signInButton.setOnClickListener {
            tryLogin()
        }
        binding.signUpButton.setOnClickListener{
            val registrationActivityCallback = requireActivity() as RegistrationActivityCallback
            registrationActivityCallback.showRegisterFragment()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }

    private fun tryLogin(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!isValid(email, password))
            return

        val userLiveData = viewModel.getUserLiveData()
        userLiveData.observe(requireActivity(), Observer {
            val test = runCatching { it }
            test.onSuccess {
                it?.let {
                    if (email == it.email){
                        val registrationActivityCallback = requireActivity() as RegistrationActivityCallback
                        registrationActivityCallback.saveUserData(UserEntity(
                            email = email,
                        password = password,
                        name = it.name,
                        token = it.token))

                        registrationActivityCallback.showNewsActivity()
                    }
                }
            }
        })

        viewModel.login(UserEntity(
            email = binding.emailEditText.text.toString(),
            name = "",
            password = binding.passwordEditText.text.toString()))
    }

    private fun isValid(email: String, password: String): Boolean{
        return true
    }
}