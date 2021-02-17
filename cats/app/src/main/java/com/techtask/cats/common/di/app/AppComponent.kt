package com.techtask.cats.common.di.app

import com.techtask.cats.common.di.activity.ActivityComponent
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun activityComponent(): ActivityComponent
}
