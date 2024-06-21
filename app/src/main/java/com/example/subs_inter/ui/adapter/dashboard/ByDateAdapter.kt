package com.example.subs_inter.ui.adapter.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subs_inter.databinding.ItemTransactionForSarchbydateBinding
import com.example.subs_inter.domain.model.transaction.TransactionDataModel

class ByDateAdapter: RecyclerView.Adapter<ByDateAdapter.ByDateViewHolder>() {

    val list = mutableListOf<TransactionDataModel>()
    class ByDateViewHolder(val binding: ItemTransactionForSarchbydateBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ByDateViewHolder {
        val binding = ItemTransactionForSarchbydateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ByDateViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ByDateViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            textViewName.text = item.name
            category.text = item.category
            tanggal.text = item.createdAt
            Type.text = item.type
            Harga.text = item.amount.toString()
        }
    }

    fun setData(newList: List<TransactionDataModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}