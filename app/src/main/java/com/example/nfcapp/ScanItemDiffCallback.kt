package com.example.nfcapp

import androidx.recyclerview.widget.DiffUtil

class ScanItemDiffCallback(
    private val oldList: List<ScanItem>,
    private val newList: List<ScanItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // You may need to adjust this if ScanItem has a unique ID or something similar
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Here you can compare the contents of each item to determine if they are the same
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
