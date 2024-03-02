package com.example.nfcapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nfcapp.databinding.ScanItemDataBinding

class ScanItemAdapter(private var items: MutableList<ScanItem>) : RecyclerView.Adapter<ScanItemAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ScanItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scanItem: ScanItem) {
            binding.itemImage.setImageResource(scanItem.imageResId)
            binding.itemText.text = scanItem.text
            binding.itemTextSize.text = scanItem.textSize
            binding.itemTitle.text = scanItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScanItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<ScanItem>) {
        val diffCallback = ScanItemDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

}
