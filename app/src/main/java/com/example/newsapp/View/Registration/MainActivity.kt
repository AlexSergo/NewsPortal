package com.example.newsapp.View.Registration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.Model.User.UserEntity
import com.example.newsapp.R
import com.example.newsapp.View.NewsActivity
import com.example.newsapp.databinding.ActivityMainBinding

interface ActivityCallback{
    fun showRegisterFragment()
    fun saveUserData(user: UserEntity)
    fun showNewsActivity()
}

const val EMAIL = "email"
const val NAME = "name"
const val TOKEN = "token"

class MainActivity : AppCompatActivity(), ActivityCallback {

    private lateinit var binding: ActivityMainBinding
    private var user: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      user = getUserFromLocalDataSource()
        if (user!!.email == "")
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commitNow()
        else {
            showNewsActivity()
        }
    }

    private fun getUserFromLocalDataSource(): UserEntity? {
        val pref = getSharedPreferences("USER", MODE_PRIVATE)
        val email = pref.getString(EMAIL, "")
        val name = pref.getString(NAME, "")
        val token = pref.getString(TOKEN, "")
        if (email != null && name != null && token != null)
            return UserEntity(email = email, name = name, token = token)
        return null
    }

    override fun showNewsActivity(){
        user = getUserFromLocalDataSource()
        user?.let {
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra("USER_NAME", user!!.name)
            intent.putExtra("USER_EMAIL", user!!.email)
            intent.putExtra("USER_TOKEN", user!!.token)
            startActivity(intent)
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
        ed.putString(NAME, user.name)
        ed.putString(TOKEN, user.token)
        ed.commit()
    }
}
