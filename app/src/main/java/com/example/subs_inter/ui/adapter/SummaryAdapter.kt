package com.example.subs_inter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.subs_inter.databinding.ItemFoodBinding
import com.example.subs_inter.domain.model.NoteModel

class SummaryAdapter(private var items: List<NoteModel>, private val onRemoveClickListener: (NoteModel) -> Unit = {}) : RecyclerView.Adapter<SummaryAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvItemName.text = item.title

        // Calculate total amount
        val amount = item.amount?.replace(".", "")?.toDoubleOrNull() ?: 0.0
        val quantity = item.quantity?.toIntOrNull() ?: 0
        val totalAmount = amount

        holder.binding.tvItemTotalPrice.text = String.format("Rp %,.0f", amount)
        holder.binding.tvItemQuantity.text = "Jumlah: $quantity"
        holder.binding.removeButton.setOnClickListener{
            onRemoveClickListener(item)
        }
    }

    override fun getItemCount() = items.size

    fun updateNoteList(newNoteListEntity: List<NoteModel>) {
        val diffResult = DiffUtil.calculateDiff(NoteDiffCallback(items, newNoteListEntity))
        items = newNoteListEntity
        diffResult.dispatchUpdatesTo(this)
    }
}
