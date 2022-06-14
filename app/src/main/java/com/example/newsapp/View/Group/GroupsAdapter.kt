package com.example.newsapp.View.Group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Group.GroupEntity
import com.example.newsapp.LocalDataSource.Model.Group.ShortGroupEntity
import com.example.newsapp.R
import com.example.newsapp.View.MainPage.GroupClickListener
import com.example.newsapp.databinding.GroupItemBinding

class GroupsAdapter(private val groupClickListener: GroupClickListener)
    : RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>()  {

    private var groups = mutableListOf<ShortGroupEntity>()
    private var isSigned = mutableListOf<Boolean>()

    fun set(groups: List<ShortGroupEntity>, isSigned: List<Boolean>){
        this.groups = groups.toMutableList()
        this.isSigned = isSigned.toMutableList()
        notifyDataSetChanged()
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
            holder.binding.imageButton.setOnClickListener {
                if (!isSigned[position]) {
                    groupClickListener.subscribe(groupId = group.id)
                    holder.binding.imageButton.setImageResource(R.drawable.ic_check)
                    isSigned[position] = true
                }
                else{
                    groupClickListener.unsubscribe(groupId = group.id)
                    holder.binding.imageButton.setImageResource(R.drawable.ic_check2)
                    isSigned[position] = false
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    class GroupViewHolder(var binding: GroupItemBinding): RecyclerView.ViewHolder(binding.root)
}