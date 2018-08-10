package com.nordkaz.ds24test.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nordkaz.ds24test.R
import com.nordkaz.ds24test.model.Quote

class ViewHolderRight(view: View) : ViewHolder(view){
    companion object {
        fun create(parent: ViewGroup): com.nordkaz.ds24test.ui.ViewHolderRight {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_item_right, parent, false)
            return ViewHolderRight(view)
        }
    }
}