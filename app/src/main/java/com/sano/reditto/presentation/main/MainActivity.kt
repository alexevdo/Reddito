package com.sano.reditto.presentation.main

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sano.reditto.R
import com.sano.reditto.presentation.model.LinkModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainView {
    private val presenter: MainPresenter by inject()
    private val adapter = LinkAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.init(this)

        rvTop.layoutManager = LinearLayoutManager(this)
        rvTop.adapter = adapter
    }

    override fun addLinks(models: List<LinkModel>) {
        adapter.addItems(models)
    }

    override fun showError(message: String?) {
    }

}
