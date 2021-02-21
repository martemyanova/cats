package com.techtask.cats.common.di.presentation

import com.techtask.cats.MainActivity
import com.techtask.cats.presentation.CatsFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [ViewModelModule::class])
interface PresentationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: CatsFragment)
}
