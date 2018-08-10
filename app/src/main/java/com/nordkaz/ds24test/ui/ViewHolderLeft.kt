package com.nordkaz.ds24test.ui

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nordkaz.ds24test.R
import com.nordkaz.ds24test.model.Quote

class ViewHolderLeft(view: View): ViewHolder(view){
    companion object {
        fun create(parent: ViewGroup): com.nordkaz.ds24test.ui.ViewHolderLeft {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_item_left, parent, false)
            return ViewHolderLeft(view)
        }
    }
}