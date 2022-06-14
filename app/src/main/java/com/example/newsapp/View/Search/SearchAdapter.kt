package com.example.newsapp.View.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.Group.ShortGroupEntity
import com.example.newsapp.View.Group.HeaderViewHolder
import com.example.newsapp.View.Group.PostViewHolder
import com.example.newsapp.View.Group.SubscribeClickListener
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.databinding.GroupItemBinding
import com.example.newsapp.databinding.MainNewsItemBinding

class SearchAdapter(var groupClickListener: GroupClickListener, var subscribeClickListener: SubscribeClickListener)
    : RecyclerView.Adapter<HeaderViewHolder>() {

    private var groups = mutableListOf<ShortGroupEntity>()

    fun set(groups: List<ShortGroupEntity>){
        this.groups = groups.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
       var inflater = LayoutInflater.from(parent.context)
        val binding = GroupItemBinding.inflate(inflater,parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        groups.getOrNull(position)?.let { group ->
            holder.bind(group =
                GroupEntity(
                    id = group.id,
                    categoryId = group.categoryId,
                    title = group.title,
                    subscribersAmount = 0,
                    posts = listOf()),
                groupClickListener,
                subscribeClickListener,
            false)
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }
}