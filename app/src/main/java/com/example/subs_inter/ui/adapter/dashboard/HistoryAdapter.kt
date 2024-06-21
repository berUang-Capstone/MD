package com.example.subs_inter.ui.adapter.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subs_inter.databinding.ItemTransactionForGetallBinding
import com.example.subs_inter.domain.model.transaction.TransactionDataModel
import com.example.subs_inter.domain.model.transaction.TrxHistoryModel

class HistoryAdapter(
): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val list: MutableList<TransactionDataModel> = mutableListOf()

    class HistoryViewHolder(val binding: ItemTransactionForGetallBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemTransactionForGetallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
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