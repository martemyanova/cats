package com.techtask.cats.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techtask.cats.R
import com.techtask.cats.domain.model.Cat

class CatsListViewComponent() {

    private val layoutId = R.layout.screen_cats_list

    private lateinit var rootView: View

    private lateinit var catsListAdapter: CatsListAdapter

    fun inflate(inflater: LayoutInflater, container: ViewGroup?): View {
        rootView = inflater.inflate(layoutId, container, false)

        return rootView
    }

    fun onViewCreated() {
        contentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            catsListAdapter = CatsListAdapter()
            adapter = catsListAdapter
        }
    }

    fun bindData(cats: List<Cat>) {
        catsListAdapter.submitList(cats)
    }

    private val context by lazy { rootView.context }
    private val contentRecyclerView by lazy { rootView.findViewById<RecyclerView>(R.id.rv_content) }

    private inline fun <reified T : View> findViewById(@IdRes widgetResId: Int): T {
        return rootView.findViewById(widgetResId)
    }
}
