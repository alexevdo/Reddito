package com.sano.reditto.presentation.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sano.reditto.R
import com.sano.reditto.presentation.base.PaginationScrollListener
import com.sano.reditto.presentation.login.view.LoginActivity
import com.sano.reditto.presentation.main.MainPresenter
import com.sano.reditto.presentation.model.LinkModel
import com.sano.reditto.util.openCustomTab
import com.sano.reditto.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by inject()

    private lateinit var adapter: LinkAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.init(this)

        adapter = LinkAdapter { presenter.onLinkClick(it) }

        layoutManager = LinearLayoutManager(this)

        rvTop.layoutManager = layoutManager
        rvTop.adapter = adapter

        rvTop.addOnScrollListener(
            PaginationScrollListener(presenter.pageSize, layoutManager,
                isLoading = { lRefresh.isRefreshing },
                loadMore = { presenter.loadMore(adapter.itemCount) })
        )

        lRefresh.setOnRefreshListener { presenter.load() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.revokeAccess -> {
                presenter.onRevokeAccessToken()
                return true
            }
            R.id.revokeRefresh -> {
                presenter.onRevokeRefreshToken()
                return true
            }
            R.id.logout -> {
                presenter.logout()
                return true
            }
        }

        return false
    }

    override fun addLinks(models: List<LinkModel>) = adapter.addItems(models)

    override fun setLinks(models: List<LinkModel>) = adapter.setItems(models)

    override fun setRefreshing(isRefreshing: Boolean) {
        lRefresh.isRefreshing = isRefreshing
    }

    override fun showError(message: String?) = toast("${getString(R.string.error)} $message")

    override fun notify(message: String) = toast("${getString(R.string.notify)} $message")

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun openTab(url: String) = openCustomTab(url)
}
