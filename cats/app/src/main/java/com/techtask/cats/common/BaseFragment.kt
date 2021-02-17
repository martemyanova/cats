package com.techtask.cats.common

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.presentationComponent()
    }

    protected val injector get() = presentationComponent
}
