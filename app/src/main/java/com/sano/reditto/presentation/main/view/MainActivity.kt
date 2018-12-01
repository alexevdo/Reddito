package com.sano.reditto.presentation.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sano.reditto.R
import com.sano.reditto.presentation.base.PaginationScrollListener
import com.sano.reditto.presentation.main.MainPresenter
import com.sano.reditto.presentation.model.LinkModel
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

        adapter = LinkAdapter()
        layoutManager = LinearLayoutManager(this)

        rvTop.layoutManager = layoutManager
        rvTop.adapter = adapter

        rvTop.addOnScrollListener(
            PaginationScrollListener(presenter.pageSize, layoutManager,
                isLoading = { lRefresh.isRefreshing },
                loadMore = { presenter.loadMore(adapter.itemCount) }))

        lRefresh.setOnRefreshListener { presenter.load() }
    }

    override fun addLinks(models: List<LinkModel>) {
        adapter.addItems(models)
    }

    override fun setLinks(models: List<LinkModel>) {
        adapter.setItems(models)
    }

    override fun setRefreshing(isRefreshing: Boolean) {
        lRefresh.isRefreshing = isRefreshing
    }

    override fun showError(message: String?) =
        Toast.makeText(this, "Error $message", Toast.LENGTH_LONG).show()
}
