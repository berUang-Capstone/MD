package com.example.subs_inter.ui.dashboard.by_category

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subs_inter.R
import com.example.subs_inter.databinding.ActivityResultSearchByCategoryBinding
import com.example.subs_inter.databinding.ActivityResultSearchByTypeBinding
import com.example.subs_inter.databinding.ActivitySearchByCategoryBinding
import com.example.subs_inter.ui.adapter.dashboard.ByTypeAdapter
import com.example.subs_inter.ui.dashboard.by_type.ByTypeViewModel
import com.example.subs_inter.ui.dashboard.by_type.ResultSearchByType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultSearchByCategory : AppCompatActivity() {
    companion object {
        const val CATEGORY_SEARCH = "categorySearch"
    }
    private val viewModel: ByCategoryViewModel by viewModels()
    private lateinit var binding: ActivityResultSearchByCategoryBinding
    private lateinit var rvAdapter: ByTypeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = intent.getStringExtra(CATEGORY_SEARCH) ?: "Foods"
        viewModel.fetchByCategory(category)
        binding = ActivityResultSearchByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = ByTypeAdapter()

        binding.recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@ResultSearchByCategory)
        }

        viewModel.trxDatas.observe(this) {
            rvAdapter.setData(it.transaction)
        }
    }
}