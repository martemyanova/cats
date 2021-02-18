package com.techtask.cats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.techtask.cats.common.BaseFragment
import com.techtask.cats.domain.usecase.FetchCatsUseCase
import com.techtask.cats.presentation.ui.CatsListViewComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatsFragment : BaseFragment() {

    @Inject
    lateinit var fetchCatsUseCase: FetchCatsUseCase

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

        lifecycleScope.launch {
            val result = fetchCatsUseCase.execute()
            if (result is FetchCatsUseCase.Result.Success) {
                catsListViewComponent.bindData(result.cats)
            }
        }
    }

    companion object {
        const val TAG = "CatsFragment"

        fun newInstance(): Fragment = CatsFragment()
    }
}
