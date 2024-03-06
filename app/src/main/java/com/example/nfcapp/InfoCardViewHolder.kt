package com.example.nfcapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var iconImage: ImageView
    var nextIcon: ImageView
    var titleText: TextView
    var descriptionText: TextView



    init {
        iconImage = itemView.findViewById(R.id.icon_image)
        titleText = itemView.findViewById(R.id.title_text)
        descriptionText = itemView.findViewById(R.id.description_text)
        nextIcon = itemView.findViewById(R.id.next_icon)



    }
}