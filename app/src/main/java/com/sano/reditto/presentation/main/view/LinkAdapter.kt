package com.sano.reditto.presentation.main.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sano.reditto.R
import com.sano.reditto.presentation.model.LinkModel
import com.sano.reditto.util.inflate

class LinkAdapter(itemClickListener: (LinkModel) -> Unit) : RecyclerView.Adapter<LinkViewHolder>() {

    private val items: MutableList<LinkModel> = arrayListOf()
    private val localItemClickListener: (Int) -> Unit = { itemClickListener.invoke(items[it]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LinkViewHolder(parent.inflate(R.layout.item_link), localItemClickListener)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) = holder.bind(items[position])

    fun addItems(addItems: List<LinkModel>) {
        items.addAll(addItems)
        notifyDataSetChanged()
    }

    fun setItems(models: List<LinkModel>) {
        items.clear()
        addItems(models)
    }
}