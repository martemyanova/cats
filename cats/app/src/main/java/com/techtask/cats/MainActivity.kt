package com.techtask.cats

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.techtask.cats.common.BaseActivity
import com.techtask.cats.common.viewmodel.ViewModelFactory
import com.techtask.cats.presentation.CatsFragment
import com.techtask.cats.presentation.viewmodel.CatsListViewModel
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: CatsListViewModel

    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CatsListViewModel::class.java)

        if (supportFragmentManager.findFragmentByTag(CatsFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content,
                    CatsFragment.newInstance(),
                    CatsFragment.TAG)
                .commit()
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            query?.let {
                viewModel.onSearchRequest(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        optionsMenu = menu
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName))
        searchView.isIconifiedByDefault = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                onSearchRequested()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onSearchRequested(): Boolean {
        optionsMenu?.findItem(R.id.menu_search)?.apply {
            if (isActionViewExpanded) {
                collapseActionView()
            } else {
                expandActionView()
            }
        }
        return super.onSearchRequested()
    }
}
