package com.example.newsapp.View.MainPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Post.PostEntity
import com.example.newsapp.View.Group.LikeClickListener
import com.example.newsapp.View.Group.PostViewHolder
import com.example.newsapp.databinding.MainNewsItemBinding
import com.example.newsapp.databinding.PostItemBinding

class PostAdapter(private val postClickListener: PostClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var changedPost: PostEntity = posts[1]
        var changedPostPosition = 0
        posts.getOrNull(position)?.let { post->
            val postHolder = holder as PostViewHolder
            postHolder.bind(post, postClickListener, object: LikeClickListener {
                override fun changePost(likeAmount: Int,  isLiked: Boolean) {
                    changedPost = PostEntity(
                        id = post.id,
                        groupName = post.groupName,
                        groupId = post.groupId,
                        userId = post.userId,
                        title = post.title,
                        description = post.description,
                        shortDesc = post.shortDesc,
                        likeAmount = likeAmount,
                        seeAmount = post.seeAmount,
                        commentAmount = post.commentAmount,
                        isLiked = isLiked
                    )
                    changedPostPosition = holder.adapterPosition

                    if (posts.removeIf { it.id == changedPost.id && it.isLiked != changedPost.isLiked}) {
                        posts.add(changedPostPosition, changedPost)
                        notifyDataSetChanged()
                    }
                }
            })
            }
        }

    override fun getItemCount(): Int {
        return posts.count()
    }
}
