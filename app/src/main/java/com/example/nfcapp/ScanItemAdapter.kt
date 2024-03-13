package com.example.nfcapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nfcapp.databinding.ScanItemDataBinding

class ScanItemAdapter(
    private var items: MutableList<ScanItem>,
    private val onItemNextClick: (ScanItem) -> Unit
) : RecyclerView.Adapter<ScanItemAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ScanItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemNext.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemNextClick(items[position])
                }
            }
        }

        fun bind(scanItem: ScanItem) {
            binding.itemImage.setImageResource(scanItem.imageResId)
            binding.itemText.text = scanItem.text
            binding.itemTextSize.text = scanItem.textSize

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
