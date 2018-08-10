package com.nordkaz.ds24test.ui

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.nordkaz.ds24test.R
import com.nordkaz.ds24test.model.Quote

open class ViewHolder(view: View): RecyclerView.ViewHolder(view)   {
    private val name: TextView = view.findViewById(R.id.quote_text)

    private var quote: Quote? = null

    init {
        view.setOnClickListener {
            quote?.id?.let { id ->
                val intent = Intent(view.context,DetailActivity::class.java)
                intent.putExtra(MESSAGE_ID_EXTRA, id.toInt())
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(quote: Quote?) {
        if (quote == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        } else {
            showQuoteData(quote)
        }
    }

    private fun showQuoteData(quote: Quote) {
        this.quote = quote
        name.text = quote.quoteText

    }
}