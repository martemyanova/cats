package com.techtask.cats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.techtask.cats.common.BaseFragment
import com.techtask.cats.presentation.ui.CatsListViewComponent
import com.techtask.cats.presentation.viewmodel.CatsListViewModel
import javax.inject.Inject

class CatsFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: CatsListViewModel

    @Inject
    lateinit var catsListViewComponent: CatsListViewComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return catsListViewComponent.inflate(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catsListViewComponent.onViewCreated()

        with (viewModel) {
            cats.observe(this@CatsFragment) { data ->
                data?.let { catsListViewComponent.bindData(it) }
            }
            loadData()
        }
    }

    companion object {
        const val TAG = "CatsFragment"

        fun newInstance(): Fragment = CatsFragment()
    }
}
