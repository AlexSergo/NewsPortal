package com.example.newsapp.View.Group

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.Post.PostEntity
import com.example.newsapp.R
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.View.MainPage.PostClickListener
import com.example.newsapp.databinding.GroupItemBinding
import com.example.newsapp.databinding.MainNewsItemBinding
import okhttp3.internal.http2.Header

const val HEADER = "HEADER_OF_GROUP"

interface LikeClickListener{
    fun changePost(likeAmount: Int, isLiked: Boolean)
}

interface SubscribeClickListener{
    fun changeSubscribeState(isSigned: Boolean)
}

class CurrentGroupAdapter(private val groupClickListener: GroupClickListener,
    private val postClickListener: PostClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var group: GroupEntity? = null
    private var isSigned: Boolean = false
    private var posts = mutableListOf<PostEntity>()

    fun set(group: GroupEntity, isSigned: Boolean){
        this.group = group
        this.isSigned = isSigned
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
                commentAmount = 0,
                isLiked = false))
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
            var changedPost: PostEntity
            var changedPostPosition = 0
            posts.getOrNull(position)?.let { post->
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
        else{
            val headerHolder = holder as HeaderViewHolder
            group?.let  { group->
                headerHolder.bind(group, groupClickListener, object: SubscribeClickListener{
                    override fun changeSubscribeState(isSigned: Boolean) {
                        this@CurrentGroupAdapter.isSigned = isSigned
                        notifyDataSetChanged()
                    }
                }, isSigned)
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

class PostViewHolder(var postBinding: MainNewsItemBinding): RecyclerView.ViewHolder(postBinding.root){

    private lateinit var postClickListener: PostClickListener
    private lateinit var likeClickListener: LikeClickListener

    fun bind(post: PostEntity, postClickListener: PostClickListener, likeClickListener: LikeClickListener) {
        postBinding.postText.text = post.description
        postBinding.postTitle.text = post.title
        postBinding.postLike.text = post.likeAmount.toString()
        postBinding.postComment.text = post.commentAmount.toString()
        postBinding.postViews.text = post.seeAmount.toString()
        postBinding.postCategory.text = post.groupName
        if (post.isLiked) {
            likeClickListener.changePost(post.likeAmount + 1, post.isLiked)
            postBinding.postLike.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_liked, 0, 0, 0)
        }

        this.postClickListener = postClickListener
        this.likeClickListener = likeClickListener

            setLikeClick(postBinding.postLike, post)
            setCommentClick(postBinding.postComment, post.id)

        postBinding.postCategory.setOnClickListener {
            postClickListener.showGroup(post.groupId)
        }
    }

    private fun setCommentClick(comment: TextView, postId: Int) {
        comment.setOnClickListener {
            postClickListener.showComments(postId)
        }
    }

    private fun setLikeClick(like: TextView, post: PostEntity) {
        like.setOnClickListener(null)
            like.setOnClickListener {
                if (!post.isLiked) {
                    postClickListener.likePost(post.id)
                    like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_liked, 0, 0, 0)
                    likeClickListener.changePost(post.likeAmount + 1, !post.isLiked)
                }
                else{
                    postClickListener.dislikePost(post.id)
                    like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0)
                    likeClickListener.changePost(post.likeAmount - 1, !post.isLiked)
                }
        }
    }
}

class HeaderViewHolder(var headerBinding: GroupItemBinding): RecyclerView.ViewHolder(headerBinding.root) {
    fun bind(
        group: GroupEntity,
        groupClickListener: GroupClickListener,
        subscribeClickListener: SubscribeClickListener,
        isSigned: Boolean
    ) {
        headerBinding.companyName.text = group.title
        headerBinding.companySubs.text = group.subscribersAmount.toString()
        headerBinding.imageButton.setImageResource(R.drawable.ic_check2)
        if (!isSigned)
            headerBinding.imageButton.setImageResource(R.drawable.ic_check2)
        else
            headerBinding.imageButton.setImageResource(R.drawable.ic_check)

            headerBinding.imageButton.setOnClickListener {
                if (!isSigned) {
                    groupClickListener.subscribe(groupId = group.id)
                    headerBinding.imageButton.setImageResource(R.drawable.ic_check)
                    subscribeClickListener.changeSubscribeState(!isSigned)
                } else {
                    groupClickListener.unsubscribe(groupId = group.id)
                    headerBinding.imageButton.setImageResource(R.drawable.ic_check2)
                    subscribeClickListener.changeSubscribeState(!isSigned)
                }
            }
        }
}