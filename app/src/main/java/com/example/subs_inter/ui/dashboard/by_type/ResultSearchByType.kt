package com.example.subs_inter.ui.dashboard.by_type

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subs_inter.R
import com.example.subs_inter.databinding.ActivityResultSearchByTypeBinding
import com.example.subs_inter.ui.adapter.dashboard.ByTypeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultSearchByType : AppCompatActivity() {
    companion object {
        const val TYPE_SEARCH = "typeSearch"
    }
    private val viewModel: ByTypeViewModel by viewModels()
    private lateinit var binding: ActivityResultSearchByTypeBinding
    private lateinit var rvAdapter: ByTypeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra(TYPE_SEARCH) ?: "Income"
        viewModel.fetchByType(type)
        binding = ActivityResultSearchByTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = ByTypeAdapter()

        binding.recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@ResultSearchByType)
        }

        viewModel.trxDatas.observe(this) {
            rvAdapter.setData(it.transaction)
        }

    }
}