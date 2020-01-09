package br.com.teste.presentation.ui.feature.repository.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.teste.R
import br.com.teste.data.model.Repository
import kotlinx.android.synthetic.main.list_item_repository.view.*

class RepositoryAdapter : PagedListAdapter<Repository, RepositoryAdapter.RepositoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int) = RepositoryViewHolder.create(view)

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Repository?) {
            itemView.title.text = item?.name
        }

        companion object {
            fun create(parent: ViewGroup) = RepositoryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_repository,
                    parent,
                    false
                )
            )
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(
                oldConcert: Repository,
                newConcert: Repository
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: Repository,
                newConcert: Repository
            ) = oldConcert.id == newConcert.id
        }
    }

}