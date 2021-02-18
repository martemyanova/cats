package com.techtask.cats.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.techtask.cats.R
import com.techtask.cats.common.imageloader.load
import com.techtask.cats.domain.model.Cat

class CatsListAdapter : RecyclerView.Adapter<CatsListAdapter.CatViewHolder>() {

    private var catsList: List<Cat>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = catsList?.get(position) ?: return
        with (holder) {
            val context = imageView.context
            imageView.load(cat.imageUrl)

            if (cat.breed != null) {
                breedTextView.text = context.getString(R.string.cats_list_item_breed_title, cat.breed)
                breedTextView.isVisible = true
            } else {
                breedTextView.isVisible = false
            }
        }
    }

    override fun getItemCount() = catsList?.size ?: 0

    fun submitList(newList: List<Cat>) {
        catsList = newList
        notifyDataSetChanged()
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        val breedTextView: TextView = itemView.findViewById(R.id.tv_breed)
    }
}
