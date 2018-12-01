package com.sano.reditto.presentation.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sano.reditto.R
import com.sano.reditto.presentation.base.PaginationScrollListener
import com.sano.reditto.presentation.login.view.LoginActivity
import com.sano.reditto.presentation.main.MainPresenter
import com.sano.reditto.presentation.model.LinkModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback

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

    override fun showError(message: String?) =
        Toast.makeText(this, "Error $message", Toast.LENGTH_LONG).show()

    override fun notify(s: String) =
        Toast.makeText(this, "Notify $s", Toast.LENGTH_LONG).show()

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun openTab(url: String) =
        CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
            .setShowTitle(true)
            .build()
            .let {
                CustomTabsHelper.addKeepAliveExtra(this@MainActivity, it.intent)
                CustomTabsHelper
                    .openCustomTab(this@MainActivity, it, Uri.parse(url), WebViewFallback())
            }
}
