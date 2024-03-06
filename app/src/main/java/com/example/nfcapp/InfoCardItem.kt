package com.example.nfcapp

class InfoCardItem(private val icon: Int, private val title: String, private val description: String) {
    fun getIcon(): Int {
        return icon
    }

    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
    }
}