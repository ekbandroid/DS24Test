package com.nordkaz.ds24test.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.nordkaz.ds24test.Injection
import com.nordkaz.ds24test.R
import com.nordkaz.ds24test.api.DetailService
import com.nordkaz.ds24test.model.Detail
import com.nordkaz.ds24test.model.MyTag
import com.nordkaz.ds24test.model.Quote
import kotlinx.android.synthetic.main.activity_detail.*

const val  MESSAGE_ID_EXTRA = "meassage_id"
class DetailActivity : AppCompatActivity() {
    private val detailService: DetailService = DetailService.create()
    private lateinit var viewModel: SearchRepositoriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
                .get(SearchRepositoriesViewModel::class.java)

        viewModel.details.observe(this, Observer<Detail> {
            setTextDetail(it)
            showChips(it?.tagList)
            Log.d("DetailActivity", "get details ${it?.id}")

        })
        viewModel.networkErrorsDetails.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        })

        val id = intent.getIntExtra(MESSAGE_ID_EXTRA, 0)
        viewModel.searchDetails(id)
    }

    private fun setTextDetail(detail:Detail?) {
        this.textViewDetail.setText(detail?.text)
        this.textViewDate.setText(detail?.createdAt)
    }

    private fun showChips(tagList: List<MyTag>?) {
        chipGroup.removeAllViews()
        tagList?.iterator()?.forEach {
            val chip = Chip(this@DetailActivity)
            chip.chipText = it.label
            if (it.color != null)
                chip.chipDrawable.setColorFilter(Color.parseColor(it.color), PorterDuff.Mode.ADD)
            chip.isClickable = false
            chip.isCheckable = false
            chipGroup.addView(chip)
        }
    }
}