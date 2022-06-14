package com.example.newsapp.View.Group.Comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Comment.CommentEntity
import com.example.newsapp.View.Group.CurrentGroupAdapter
import com.example.newsapp.databinding.CommentHeaderBinding
import com.example.newsapp.databinding.CommentItemBinding
import com.example.newsapp.databinding.GroupItemBinding
import com.example.newsapp.databinding.MainNewsItemBinding

const val HEADER = "HEADER_OF_COMMENTS"

class CommentsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var comments = mutableListOf<CommentEntity>()

    fun set(comments: List<CommentEntity>){
        this.comments = comments.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            val binding = CommentItemBinding.inflate(inflater,parent, false)
            return CommentsAdapter.CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val commentHolder = holder as CommentViewHolder
            comments.getOrNull(position)?.let { comment ->
                commentHolder.binding.text.text = comment.text
                commentHolder.binding.commentDate.text = comment.date
            }
    }

    override fun getItemCount(): Int {
       return comments.count()
    }

    class CommentViewHolder(var binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root)
}