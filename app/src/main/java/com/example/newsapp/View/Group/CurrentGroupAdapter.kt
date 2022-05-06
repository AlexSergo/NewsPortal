package com.example.newsapp.View.Group

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.Post.PostEntity
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.databinding.GroupItemBinding
import com.example.newsapp.databinding.MainNewsItemBinding
import okhttp3.internal.http2.Header

const val HEADER = "HEADER_OF_GROUP"

class CurrentGroupAdapter(private val groupClickListener: GroupClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var group: GroupEntity? = null
    private var posts = mutableListOf<PostEntity>()

    fun set(group: GroupEntity){
        this.group = group
        posts = group.posts.toMutableList()
        posts.add(0, PostEntity(
                groupName = group.title,
                groupId = group.id,
                userId = 1,
                title = HEADER,
                description = "",
                shortDesc = "",
                likeAmount = 0,
                seeAmount = 0,
                commentAmount = 0))
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        if (posts.get(position).title == HEADER)
            return 1
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = if (viewType == 0) {
            val binding = MainNewsItemBinding.inflate(inflater,parent, false)
            PostViewHolder(binding)
        } else{
            val binding = GroupItemBinding.inflate(inflater,parent, false)
            HeaderViewHolder(binding)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = this.getItemViewType(position)
        if (viewType == 0){
            val postHolder = holder as PostViewHolder
            posts.getOrNull(position)?.let { posts->
                postHolder.postBinding.postText.text = posts.description
                postHolder.postBinding.postTitle.text = posts.title
                postHolder.postBinding.postLike.text = posts.likeAmount.toString()
                postHolder.postBinding.postComment.text = posts.commentAmount.toString()
                postHolder.postBinding.postViews.text = posts.seeAmount.toString()
                postHolder.postBinding.postCategory.text = posts.groupName
            }
        }
        else{
            val headerHolder = holder as HeaderViewHolder
            group.let {
                headerHolder.headerBinding.companyName.text = it!!.title
                headerHolder.headerBinding.companySubs.text = it.subscribersAmount.toString()

                headerHolder.headerBinding.imageButton.setOnClickListener{
                    if (headerHolder.headerBinding.imageButton.isEnabled ) {
                        headerHolder.headerBinding.imageButton.isEnabled = false
                        groupClickListener.subscribe(groupId = posts[0].groupId)
                    }
                    else{
                        headerHolder.headerBinding.imageButton.isEnabled = true
                        groupClickListener.unsubscribe(groupId = posts[0].groupId)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostViewHolder(var postBinding: MainNewsItemBinding): RecyclerView.ViewHolder(postBinding.root)

    class HeaderViewHolder(var headerBinding: GroupItemBinding): RecyclerView.ViewHolder(headerBinding.root)
}