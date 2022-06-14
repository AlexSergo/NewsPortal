package com.example.newsapp.View.Registration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.R
import com.example.newsapp.View.NewsActivity
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newsapp.ViewModel.NewsViewModelFactory
import com.example.newsapp.ViewModel.RepositoryInitializer
import com.example.newsapp.databinding.ActivityMainBinding

interface RegistrationActivityCallback {
    fun showRegisterFragment()
    fun saveUserData(user: UserEntity)
    fun showNewsActivity()
}

const val EMAIL = "email"
const val NAME = "name"
const val TOKEN = "token"
const val PASSWORD = "password"

class MainActivity : AppCompatActivity(), RegistrationActivityCallback {

    private lateinit var binding: ActivityMainBinding
    private var user: UserEntity? = null
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = NewsViewModelFactory(RepositoryInitializer.getRepository(applicationContext))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        if (getUserFromLocalDataSource() == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commitNow()
        else
            showNewsActivity()
    }

    private fun getUserFromLocalDataSource(): UserEntity? {
        val pref = getSharedPreferences("USER", MODE_PRIVATE)
        val email = pref.getString(EMAIL, "")
        val password = pref.getString(PASSWORD, "")

        if (email != null && password != null) {
            return UserEntity(email = email, password = password, name = "")
        }
        return null
    }

    override fun showNewsActivity() {
        user = getUserFromLocalDataSource()
        if (user != null) {
            viewModel.login(user!!)
            viewModel.getUserLiveData().observe(
                this,
                Observer {
                    it?.let {
                        if (it.email == user!!.email) {
                            val intent = Intent(this, NewsActivity::class.java)
                            intent.putExtra("USER_NAME", it.name)
                            intent.putExtra("USER_EMAIL", it.email)
                            intent.putExtra("USER_TOKEN", it.token)
                            startActivity(intent)
                        } else
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container, SignInFragment.newInstance())
                                .commitNow()
                    }
                }
            )
        }
    }

    override fun showRegisterFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SignUpFragment.newInstance())
            .commitNow()
    }

    override fun saveUserData(user: UserEntity) {
        val save = getSharedPreferences("USER", MODE_PRIVATE)
        val ed: SharedPreferences.Editor = save.edit()
        ed.putString(EMAIL, user.email)
        ed.putString(PASSWORD, user.password)
        ed.commit()
    }
}
