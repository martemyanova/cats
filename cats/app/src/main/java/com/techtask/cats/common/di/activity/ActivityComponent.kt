package com.techtask.cats.common.di.activity

import com.techtask.cats.common.di.presentation.PresentationComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun presentationComponent(): PresentationComponent
}
