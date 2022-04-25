package com.example.newsapp.View.Group

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.Post.PostEntity
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.databinding.GroupHeaderItemBinding
import com.example.newsapp.databinding.PostItemBinding
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
            val binding = PostItemBinding.inflate(inflater,parent, false)
            PostViewHolder(binding)
        } else{
            val binding = GroupHeaderItemBinding.inflate(inflater,parent, false)
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
                postHolder.postBinding.postName.text = posts.title
                postHolder.postBinding.likeAmount.text = posts.likeAmount.toString()
                postHolder.postBinding.commentAmount.text = posts.commentAmount.toString()
                postHolder.postBinding.seeAmount.text = posts.seeAmount.toString()
                postHolder.postBinding.groupName.text = posts.groupName
            }
        }
        else{
            val headerHolder = holder as HeaderViewHolder
            group.let {
                headerHolder.headerBinding.groupName.text = it!!.title
                headerHolder.headerBinding.subsAmount.text = it.subscribersAmount.toString()

                headerHolder.headerBinding.subscribeButton.setOnClickListener{
                    if (headerHolder.headerBinding.subscribeButton.isEnabled ) {
                        headerHolder.headerBinding.subscribeButton.isEnabled = false
                        holder.headerBinding.subscribeButton.text = "Отписаться"
                        groupClickListener.subscribe(groupId = posts[0].groupId)
                    }
                    else{
                        headerHolder.headerBinding.subscribeButton.isEnabled = true
                        holder.headerBinding.subscribeButton.text = "Подписаться"
                        groupClickListener.unsubscribe(groupId = posts[0].groupId)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostViewHolder(var postBinding: PostItemBinding): RecyclerView.ViewHolder(postBinding.root)

    class HeaderViewHolder(var headerBinding: GroupHeaderItemBinding): RecyclerView.ViewHolder(headerBinding.root)
}