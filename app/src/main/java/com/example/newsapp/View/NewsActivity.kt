package com.example.newsapp.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.Model.User.UserEntity
import com.example.newsapp.databinding.ActivityNewsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.View.Registration.SignInFragment


class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = intent.extras
        val name = arguments?.get("USER_NAME").toString()
        val email =  arguments?.get("USER_EMAIL").toString()
        val token = arguments?.get("USER_TOKEN").toString()
        user = UserEntity(name = name, email = email, token = token)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainPageContainer, MainNewsFragment.newInstance(user))
            .commitNow()
    }
}