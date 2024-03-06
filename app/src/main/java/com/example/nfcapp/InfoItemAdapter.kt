package com.example.nfcapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoItemAdapter(private val context: Context, private val itemList: MutableList<RecordTypeModel>) :
    RecyclerView.Adapter<InfoItemAdapter.InfoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.info_type_item, parent, false)
        return InfoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvTitle.text = item.getTitle() // Use getter method
        val infoCardAdapter = InfoCardAdapter(context, item.getRecords())
        holder.rvRecords.adapter = infoCardAdapter
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class InfoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val rvRecords = itemView.findViewById<RecyclerView>(R.id.rv_records)
    }
}
