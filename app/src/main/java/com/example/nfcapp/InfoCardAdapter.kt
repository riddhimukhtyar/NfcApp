package com.example.nfcapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class InfoCardAdapter(private val context: Context, private val itemList: List<InfoCardItem>) :
    RecyclerView.Adapter<InfoCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.example_item, parent, false)
        return InfoCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoCardViewHolder, position: Int) {
        val item = itemList[position]
        holder.iconImage.setImageResource(item.getIcon()) // Use getter method
        holder.titleText.text = item.getTitle() // Use getter method
        holder.descriptionText.text = item.getDescription() // Use getter method

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
