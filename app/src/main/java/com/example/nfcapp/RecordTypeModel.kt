package com.example.nfcapp

class RecordTypeModel(private val title: String, private val records: MutableList<InfoCardItem>) {
    fun getTitle(): String {
        return title
    }

    fun getRecords(): MutableList<InfoCardItem> {
        return records
    }
}