package com.example.newsapp.View.MainPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Post.PostEntity
import com.example.newsapp.databinding.MainNewsItemBinding
import com.example.newsapp.databinding.PostItemBinding

class PostAdapter(private val groupClickListener: GroupClickListener)
    : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts = mutableListOf<PostEntity>()

    fun set(posts: List<PostEntity>){

        this.posts = posts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainNewsItemBinding.inflate(inflater,parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        posts.getOrNull(position)?.let { posts->
            holder.binding.postText.text = posts.description
            holder.binding.postTitle.text = posts.title
            holder.binding.postLike.text = posts.likeAmount.toString()
            holder.binding.postComment.text = posts.commentAmount.toString()
            holder.binding.postViews.text = posts.seeAmount.toString()
            holder.binding.postCategory.text = posts.groupName
            holder.binding.postCategory.setOnClickListener{
                groupClickListener.showGroup(posts.groupId)
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.count()
    }


    class PostViewHolder(var binding: MainNewsItemBinding): RecyclerView.ViewHolder(binding.root)
}
