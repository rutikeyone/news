package com.newsapp.feature.news.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.feature.news.domain.entity.CategoryEntity
import com.squareup.picasso.Picasso

class CategoryRecyclerViewAdapter(
    private val categories: ArrayList<CategoryEntity>,
    private val listener: (category: CategoryEntity) -> Unit,
    ) : RecyclerView.Adapter<CategoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val tile = layoutInflater.inflate(R.layout.category_recycler_view_tile, parent, false)
        return CategoryViewHolder(tile)
    }

    override fun getItemCount(): Int {
        return categories.size;
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], listener)
    }
}

class CategoryViewHolder(private val view:View) : RecyclerView.ViewHolder(view) {
    fun bind(category: CategoryEntity, listener: (category: CategoryEntity) -> Unit) {
        val picasso = Picasso.get()
        picasso.isLoggingEnabled = true

        val cardView = view.findViewById<CardView>(R.id.idCVCategory)
        val categoryBackground = view.findViewById<ImageView>(R.id.idIVCategory)
        val categoryText = view.findViewById<TextView>(R.id.idTVCategory);


        picasso.load(category.url).into(categoryBackground)
        categoryText.text = category.name
        cardView.setOnClickListener {
            listener(category)
        }

    }
}