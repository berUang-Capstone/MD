package com.example.subs_inter.ui.adapter.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subs_inter.databinding.ItemTransactionForTypeBinding
import com.example.subs_inter.domain.model.transaction.TransactionDataModel

class ByTypeAdapter: RecyclerView.Adapter<ByTypeAdapter.ByTypeViewHolder>() {

    val list = mutableListOf<TransactionDataModel>()
    class ByTypeViewHolder(val binding: ItemTransactionForTypeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ByTypeViewHolder {
        val binding = ItemTransactionForTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ByTypeViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ByTypeViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            textViewName.text = item.name
            category.text = item.category
            tanggal.text = item.createdAt
            amount.text = item.amount.toString()
        }
    }

    fun setData(newList: List<TransactionDataModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}