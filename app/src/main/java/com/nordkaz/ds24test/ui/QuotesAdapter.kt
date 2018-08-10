package com.nordkaz.ds24test.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.nordkaz.ds24test.model.Quote

class QuotesAdapter : ListAdapter<Quote, RecyclerView.ViewHolder>(QUOTE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0)
            return ViewHolderLeft.create(parent)
         return ViewHolderRight.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            when (getItemViewType(position)) {
                0 ->  (holder as ViewHolderLeft).bind(repoItem)
                1 ->  (holder as ViewHolderRight).bind(repoItem)
            }
        }
    }

    companion object {
        private val QUOTE_COMPARATOR = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean =
                    oldItem.quoteText == newItem.quoteText

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean =
                    oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).name.toInt()
      }
}