package com.example.subs_inter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subs_inter.customView.FinanceCV
import com.example.subs_inter.databinding.ItemFinanceBinding

class FinanceAdapter(private val financeItems: List<FinanceItem>) : RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>() {

    data class FinanceItem(val title: String, val amount: Int, val backgroundColor: Int)

    class FinanceViewHolder(val binding: ItemFinanceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceViewHolder {
        val binding = ItemFinanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {
        val item = financeItems[position]
        holder.binding.financeView.title = item.title
        holder.binding.financeView.amount = item.amount
        holder.binding.financeView.customBackgroundColor = item.backgroundColor
    }

    override fun getItemCount() = financeItems.size
}
