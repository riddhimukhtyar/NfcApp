package com.example.nfcapp

import androidx.recyclerview.widget.DiffUtil

class ScanItemDiffCallback(
    private val oldList: List<ScanItem>,
    private val newList: List<ScanItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare items based on their text property
        return oldList[oldItemPosition].text == newList[newItemPosition].text
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare the entire object to check if the contents are the same
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
