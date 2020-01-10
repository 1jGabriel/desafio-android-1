package br.com.teste.presentation.ui.feature.pullrequests.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_pull_request.view.*

class PullRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title = itemView.title
    val description = itemView.description
    val username = itemView.username
    val avatar = itemView.avatar

}
