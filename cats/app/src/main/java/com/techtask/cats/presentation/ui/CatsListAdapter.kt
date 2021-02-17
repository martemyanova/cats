package com.techtask.cats.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techtask.cats.R
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
            idTextView.text = cat.name
        }
    }

    override fun getItemCount() = catsList?.size ?: 0

    fun submitList(newList: List<Cat>) {
        catsList = newList
        notifyDataSetChanged()
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView = itemView.findViewById<TextView>(R.id.tv_id)
    }
}
