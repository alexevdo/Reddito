package com.sano.reditto.presentation.main.view

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sano.reditto.R
import com.sano.reditto.presentation.model.LinkModel
import com.sano.reditto.util.numToK
import com.sano.reditto.util.visibleOrGone
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_link.*
import java.util.concurrent.TimeUnit

class LinkViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(model: LinkModel) {
        with(itemView.resources) {
            tvInfo.text = getString(R.string.post_info, model.author, timeAgo(model.postDate))
            tvScore.text = getString(R.string.post_score_comments, numToK(model.score), numToK(model.numComments))
            tvTitle.text = model.title

            ivPostImage.visibleOrGone(model.hasThumbnail())

            model.thumbnail?.let {
                Picasso.get().load(model.thumbnail).into(ivPostImage)
            }
        }
    }

    private fun timeAgo(postDate: Long) =
        DateUtils.getRelativeTimeSpanString(TimeUnit.SECONDS.toMillis(postDate), System.currentTimeMillis(), 0)

}