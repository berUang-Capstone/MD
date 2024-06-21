package com.example.subs_inter.ui.dashboard.all_trx

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subs_inter.R
import com.example.subs_inter.databinding.ActivityGetallTransactionBinding
import com.example.subs_inter.ui.adapter.dashboard.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

import okhttp3.OkHttpClient


@AndroidEntryPoint
class GetAllTransactionActivity : AppCompatActivity() {
    private lateinit var rvAdapter: HistoryAdapter
    private val viewModel: AllTrxViewModel by viewModels()
    private lateinit var binding: ActivityGetallTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchHistory()

        binding = ActivityGetallTransactionBinding.inflate(layoutInflater)

        rvAdapter = HistoryAdapter()

        binding.recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@GetAllTransactionActivity)
        }

        viewModel.trxHistory.observe(this) {
            rvAdapter.setData(it.transactions)
        }

        setContentView(binding.root)
    }
}
