package com.techtask.cats.common.di.presentation

import com.techtask.cats.MainActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent
interface PresentationComponent {
    fun inject(activity: MainActivity)
}
