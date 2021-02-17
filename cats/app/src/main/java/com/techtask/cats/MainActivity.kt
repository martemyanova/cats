package com.techtask.cats

import android.os.Bundle
import com.techtask.cats.common.BaseActivity
import com.techtask.cats.presentation.CatsFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag(CatsFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content,
                    CatsFragment.newInstance(),
                    CatsFragment.TAG)
                .commit()
        }
    }
}
