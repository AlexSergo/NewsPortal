package com.example.newsapp.View.Group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.Group.ShortGroupEntity
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.databinding.GroupItemBinding

class GroupsAdapter(private val groupClickListener: GroupClickListener)
    : RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>()  {

    private var groups = mutableListOf<ShortGroupEntity>()

    fun set(groups: List<ShortGroupEntity>){
        this.groups = groups.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GroupItemBinding.inflate(inflater,parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        groups.getOrNull(position)?.let { group->
            holder.binding.companyName.text = group.title
            holder.binding.companyName.setOnClickListener{
                groupClickListener.showGroup(group.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    class GroupViewHolder(var binding: GroupItemBinding): RecyclerView.ViewHolder(binding.root)
}