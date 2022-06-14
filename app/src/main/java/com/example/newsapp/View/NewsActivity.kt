package com.example.newsapp.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.User.UserEntity
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.R
import com.example.newsapp.SubscribesFragment
import com.example.newsapp.View.Group.Comments.CommentsFragment
import com.example.newsapp.View.Group.GroupFragment
import com.example.newsapp.View.MainPage.MainNewsFragment
import com.example.newsapp.View.Profile.ProfileFragment
import com.example.newsapp.View.SaveNews.SaveNewsFragment
import com.example.newsapp.View.Search.SearchFragment

interface NewsActivityCallback {
    fun showGroupFragment(group: GroupEntity)
    fun showSubscribesFragment()
    fun showCommentFragment(postId: Int)
}

class NewsActivity : AppCompatActivity(), NewsActivityCallback {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = intent.extras
        val name = arguments?.get("USER_NAME").toString()
        val email = arguments?.get("USER_EMAIL").toString()
        val token = arguments?.get("USER_TOKEN").toString()
        user = UserEntity(name = name, email = email, token = token)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        supportFragmentManager.beginTransaction()
            .add(R.id.mainPageContainer, MainNewsFragment.newInstance(user))
            .addToBackStack("MainFragment")
            .commit()
        setContentView(binding.root)

        binding.navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainPageContainer, MainNewsFragment.newInstance(user))
                        .commitNow()
                }
                R.id.search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainPageContainer, SearchFragment())
                        .commitNow()
                }
                R.id.subscribe -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainPageContainer, SubscribesFragment.newInstance(user))
                        .commitNow()
                }
                R.id.save_news -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainPageContainer, SaveNewsFragment())
                        .commitNow()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainPageContainer, ProfileFragment.newInstance(user))
                        .commitNow()
                }
            }
            true
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

    override fun showCommentFragment(postId: Int){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainPageContainer, CommentsFragment.newInstance(postId, user))
            .commitNow()
    }

}