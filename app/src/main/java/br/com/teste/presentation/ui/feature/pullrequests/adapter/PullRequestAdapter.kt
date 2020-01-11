package br.com.teste.presentation.ui.feature.pullrequests.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.teste.R
import br.com.teste.common.extensions.applyFormat
import br.com.teste.common.extensions.loadImage
import br.com.teste.data.model.PullRequest
import br.com.teste.presentation.ui.feature.pullrequests.PullRequestClickItem
import setSafeOnClickListener

class PullRequestAdapter(val clickListener: PullRequestClickItem) : RecyclerView.Adapter<PullRequestViewHolder>() {
    var items = ArrayList<PullRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PullRequestViewHolder(inflater.inflate(R.layout.list_item_pull_request, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val item = items[position]

        with(holder) {
            title.text = item.title
            description.text = item.body
            username.text = item.user.login
            date.text = item.createdAt.applyFormat()

            avatar.loadImage(item.user.avatarUrl)

            itemView.setSafeOnClickListener {
                clickListener.onClickItem(
                    item
                )
            }
        }
    }
}