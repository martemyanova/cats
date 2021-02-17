package com.techtask.cats.common

import androidx.appcompat.app.AppCompatActivity
import com.techtask.cats.CatsApplication

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as CatsApplication).appComponent

    val activityComponent by lazy {
        appComponent.activityComponent()
    }

    private val presentationComponent by lazy {
        activityComponent.presentationComponent()
    }

    protected val injector get() = presentationComponent
}