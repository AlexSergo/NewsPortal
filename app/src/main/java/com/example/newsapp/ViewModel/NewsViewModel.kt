package com.example.newsapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Model.Category.CategoryEntity
import com.example.newsapp.Model.Group.GroupEntity
import com.example.newsapp.Model.Group.ShortGroupEntity
import com.example.newsapp.Model.Post.PostEntity
import com.example.newsapp.Model.Repository
import com.example.newsapp.Model.User.UserEntity
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository): ViewModel() {

    private val userLiveData = MutableLiveData<UserEntity>()
    private val categoryLiveData = MutableLiveData<CategoryEntity>()
    private val categoryListLiveData = MutableLiveData<List<CategoryEntity>>()
    private val groupListLiveData = MutableLiveData<List<ShortGroupEntity>>()
    private val groupLiveData = MutableLiveData<GroupEntity>()

    fun getCategoryListLiveData(): LiveData<List<CategoryEntity>> {
        return categoryListLiveData
    }

    fun getCategoryLiveData(): LiveData<CategoryEntity>{
        return categoryLiveData
    }

    fun getGroupLiveData(): LiveData<GroupEntity>{
        return groupLiveData
    }

    fun getGroupListLiveData(): LiveData<List<ShortGroupEntity>>{
        return groupListLiveData
    }

    fun getUserLiveData(): LiveData<UserEntity>{
        return userLiveData
    }

    fun login(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        userLiveData.postValue(repository.login(user))
    }

    fun register(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        userLiveData.postValue(repository.register(user))
    }

    fun getAllCategories() = viewModelScope.launch(Dispatchers.IO) {
        categoryListLiveData.postValue(repository.getAllCategories())
    }

    fun getGroupsByCategoryId(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        groupListLiveData.postValue(repository.getGroupsForCategory(id))
    }

    fun getGroupById(id: Int, token: String) = viewModelScope.launch(Dispatchers.IO) {
        groupLiveData.postValue(repository.getGroupById(id, token))
    }

    fun getCategory(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        categoryLiveData.postValue(repository.getCategory(id))
    }

    fun insertPostsToLocalDataSource(posts: List<PostEntity>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePostsFromLocalDataSource()
        repository.insertPostsToLocalDataSource(posts)
    }

}