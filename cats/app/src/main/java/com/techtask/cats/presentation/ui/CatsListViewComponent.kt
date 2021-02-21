package com.techtask.cats.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.techtask.cats.R
import com.techtask.cats.domain.model.Cat
import com.techtask.cats.presentation.viewmodel.CatsListViewState

class CatsListViewComponent(private val onReloadClick: () -> Unit) {

    private val layoutId = R.layout.screen_cats_list

    private lateinit var rootView: View

    private lateinit var catsListAdapter: CatsListAdapter

    fun inflate(inflater: LayoutInflater, container: ViewGroup?): View {
        rootView = inflater.inflate(layoutId, container, false)
        return rootView
    }

    fun onViewCreated() {
        setupRecyclerView()
        setupReloadButton()
        updateState(CatsListViewState.LOADING)
    }

    private fun setupRecyclerView() {
        contentRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(numberOfColumns,
                StaggeredGridLayoutManager.VERTICAL)
            catsListAdapter = CatsListAdapter()
            adapter = catsListAdapter
        }
    }

    private fun setupReloadButton() {
        reloadButton.setOnClickListener {
            onReloadClick()
        }
    }

    fun bindData(cats: List<Cat>) {
        catsListAdapter.submitList(cats)
        updateState(CatsListViewState.DATA_READY)
    }

    fun updateState(state: CatsListViewState) {
        contentRecyclerView.isVisible = state == CatsListViewState.DATA_READY
        progressBarView.isVisible = state == CatsListViewState.LOADING
        errorMessageTextView.isVisible = state == CatsListViewState.ERROR
        reloadButton.isVisible = state == CatsListViewState.ERROR
        nothingFoundMessageTextView.isVisible = state == CatsListViewState.NOTHING_FOUND
    }

    private val context by lazy { rootView.context }
    private val numberOfColumns by lazy { context.resources.getInteger(R.integer.number_of_columns) }

    private val contentRecyclerView by lazy { rootView.findViewById<RecyclerView>(R.id.rv_content) }
    private val progressBarView by lazy { rootView.findViewById<ProgressBar>(R.id.pb_progress_bar) }
    private val errorMessageTextView by lazy {
        rootView.findViewById<TextView>(R.id.tv_error_message) }
    private val reloadButton by lazy { rootView.findViewById<Button>(R.id.bt_reload) }
    private val nothingFoundMessageTextView by lazy {
        rootView.findViewById<TextView>(R.id.tv_nothing_was_found_message) }

    private inline fun <reified T : View> findViewById(@IdRes widgetResId: Int): T {
        return rootView.findViewById(widgetResId)
    }
}
