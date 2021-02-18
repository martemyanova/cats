package com.techtask.cats.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.techtask.cats.R
import com.techtask.cats.domain.model.Cat
import javax.inject.Inject

class CatsListViewComponent @Inject constructor() {

    private val layoutId = R.layout.screen_cats_list

    private lateinit var rootView: View

    private lateinit var catsListAdapter: CatsListAdapter

    fun inflate(inflater: LayoutInflater, container: ViewGroup?): View {
        rootView = inflater.inflate(layoutId, container, false)

        return rootView
    }

    fun onViewCreated() {
        contentRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(numberOfColumns,
                StaggeredGridLayoutManager.VERTICAL)
            catsListAdapter = CatsListAdapter()
            adapter = catsListAdapter
        }
    }

    fun bindData(cats: List<Cat>) {
        catsListAdapter.submitList(cats)
    }

    private val context by lazy { rootView.context }
    private val numberOfColumns by lazy { context.resources.getInteger(R.integer.number_of_columns) }

    private val contentRecyclerView by lazy { rootView.findViewById<RecyclerView>(R.id.rv_content) }

    private inline fun <reified T : View> findViewById(@IdRes widgetResId: Int): T {
        return rootView.findViewById(widgetResId)
    }
}
