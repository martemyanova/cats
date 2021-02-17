package com.techtask.cats

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.techtask.cats.common.BaseActivity
import com.techtask.cats.domain.usecase.FetchCatsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var fetchCatsUseCase: FetchCatsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injector.inject(this)

        lifecycleScope.launch {
            val stringBuilder = StringBuilder("Resuls:/n")
            val result = fetchCatsUseCase.execute()
            if (result is FetchCatsUseCase.Result.Success) {
                result.cats.forEach {
                    stringBuilder.append(it.name)
                    stringBuilder.append("/n")
                }
            }

            findViewById<TextView>(R.id.content).text = stringBuilder.toString()
        }

    }
}