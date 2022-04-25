package com.example.newsapp.View.MainPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LocalDataSource.Model.Category.CategoryEntity
import com.example.newsapp.databinding.CategoryItemBinding

class CategoryAdapter()
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categories = mutableListOf<CategoryEntity>()

    fun set(categories: List<CategoryEntity>){
        this.categories = categories.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater,parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        categories.getOrNull(position)?.let { category->
            holder.binding.categoryName.text = category.name
        }
    }

    override fun getItemCount(): Int {
        return categories.count()
    }


    class CategoryViewHolder(var binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)
}
