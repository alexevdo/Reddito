package com.sano.reditto.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sano.reditto.R
import com.sano.reditto.presentation.model.LinkModel
import com.sano.reditto.util.inflate

class LinkAdapter: RecyclerView.Adapter<LinkViewHolder>() {

    private val items: MutableList<LinkModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LinkViewHolder(parent.inflate(R.layout.item_link))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int)  = holder.bind(items[position])

    fun addItems(addItems: List<LinkModel>) {
        items.addAll(addItems)
        notifyDataSetChanged()
    }
}