package com.example.newsapp.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.R
import com.example.newsapp.SubscribesFragment
import com.example.newsapp.View.Group.GroupFragment
import com.example.newsapp.View.Profile.ProfileFragment

interface NewsActivityCallback{
    fun showGroupFragment(group: GroupEntity)
    fun showSubscribesFragment()
}

class NewsActivity : AppCompatActivity(), NewsActivityCallback {

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

        binding.profile.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainPageContainer, ProfileFragment.newInstance(user))
                .commitNow()
        }
    }

    override fun showGroupFragment(group: GroupEntity) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainPageContainer, GroupFragment.newInstance(group, user))
            .commitNow()
    }

    override fun showSubscribesFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainPageContainer, SubscribesFragment.newInstance(user))
            .commitNow()
    }

}