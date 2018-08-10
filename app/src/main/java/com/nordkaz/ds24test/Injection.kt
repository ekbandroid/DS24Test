package com.nordkaz.ds24test

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.nordkaz.ds24test.api.DetailService
import com.nordkaz.ds24test.api.QuotesService
import com.nordkaz.ds24test.data.QuoteRepository
import com.nordkaz.ds24test.db.DetailDatabase
import com.nordkaz.ds24test.db.LocalCache
import com.nordkaz.ds24test.db.QuoteDatabase
import com.nordkaz.ds24test.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection {

    private fun provideCache(context: Context): LocalCache {
        val quoteDatabase = QuoteDatabase.getInstance(context)
        val detailDatabase = DetailDatabase.getInstance(context)
        return LocalCache(quoteDatabase.quoteDao(), detailDatabase.detailDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideQuoteRepository(context: Context): QuoteRepository {
        return QuoteRepository(QuotesService.create(), DetailService.create(),provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideQuoteRepository(context))
    }

}