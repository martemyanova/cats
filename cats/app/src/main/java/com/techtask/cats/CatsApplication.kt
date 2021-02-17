package com.techtask.cats

import android.app.Application
import com.techtask.cats.common.di.app.AppComponent
import com.techtask.cats.common.di.app.AppModule
import com.techtask.cats.common.di.app.DaggerAppComponent

class CatsApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
