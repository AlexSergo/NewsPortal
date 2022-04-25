package com.example.newsapp.View.MainPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Post.PostEntity
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
        val binding = PostItemBinding.inflate(inflater,parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        posts.getOrNull(position)?.let { posts->
            holder.binding.postText.text = posts.description
            holder.binding.postName.text = posts.title
            holder.binding.likeAmount.text = posts.likeAmount.toString()
            holder.binding.commentAmount.text = posts.commentAmount.toString()
            holder.binding.seeAmount.text = posts.seeAmount.toString()
            holder.binding.groupName.text = posts.groupName
            holder.binding.groupName.setOnClickListener{
                groupClickListener.showGroup(posts.groupId)
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.count()
    }


    class PostViewHolder(var binding: PostItemBinding): RecyclerView.ViewHolder(binding.root)
}
