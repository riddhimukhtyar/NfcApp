package com.example.nfcapp

import WriteNfcTag
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class Record_Type : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var infoItemAdapter: InfoItemAdapter
    private lateinit var itemList: MutableList<InfoCardItem>
    private lateinit var recordTypeModel: MutableList<RecordTypeModel>
    private lateinit var backButton: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_record__type)

        recyclerView = findViewById(R.id.recyclerview)
        backButton = findViewById(R.id.backButton)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recordTypeModel = generateItemList()
        infoItemAdapter = InfoItemAdapter(this, recordTypeModel)
        recyclerView.adapter = infoItemAdapter

        backButton.setOnClickListener {
            // Start WriteNfcTag activity
            val intent = Intent(this, WriteNfcTag::class.java)
            startActivity(intent)
        }
    }


    private fun generateItemList(): MutableList<RecordTypeModel> {
        val list = mutableListOf<RecordTypeModel>()
        var itemList: MutableList<InfoCardItem> = mutableListOf()
        itemList.add(InfoCardItem(R.drawable.email__1_, "Mail", "Add Mail Record"))
        itemList.add(InfoCardItem(R.drawable.contact_book, "Contact", "Add Contact Record"))
        itemList.add(
            InfoCardItem(
                R.drawable.phone_call__3_,
                "Phone Number",
                "Add Phone Number Record"
            )
        )
        itemList.add(InfoCardItem(R.drawable.message, "SMS", "Add Phone SMS Record"))
        itemList.add(InfoCardItem(R.drawable.video_call, "Facetime", "Add Facetime Record"))
        itemList.add(
            InfoCardItem(
                R.drawable.speaker,
                "Facetime Audio",
                "Add Facetime Audio Record"
            )
        )

        var itemList1: MutableList<InfoCardItem> = mutableListOf()
        itemList1.add(InfoCardItem(R.drawable.text_format, "Text", "Add text Record"))
        itemList1.add(InfoCardItem(R.drawable.link__1_, "URL", "Add URL Record"))
        itemList1.add(InfoCardItem(R.drawable.link__1_, "Custom URL", "Add URL Record"))
        itemList1.add(InfoCardItem(R.drawable.document, "Unit.link", "Add URL Record"))
        itemList1.add(InfoCardItem(R.drawable.database, "File", "Add File Record"))
        itemList1.add(InfoCardItem(R.drawable.bitcoin, "Data", "Add Data Record"))


        var itemList2: MutableList<InfoCardItem> = mutableListOf()
        itemList2.add(
            InfoCardItem(
                R.drawable.application__2_,
                "Application",
                "Add Application Record"
            )
        )
        itemList2.add(
            InfoCardItem(
                R.drawable.link__1_,
                "Social Network",
                "Add Social Network Record"
            )
        )

        var itemList3: MutableList<InfoCardItem> = mutableListOf()
        itemList3.add(InfoCardItem(R.drawable.location__3_, "Location", "Add Location Record"))
        itemList3.add(InfoCardItem(R.drawable.maps_and_flags__1_, "Address", "Add Addresss Record"))
        itemList3.add(InfoCardItem(R.drawable.bluetooth__1_, "Bluetooth", "Add Bluetooth Record"))
        itemList3.add(InfoCardItem(R.drawable.wifi, "Wi-Fi Network", "Add Wifi Network Record"))




        list.add(RecordTypeModel("Communication", itemList))
        list.add(RecordTypeModel("Information", itemList1))
        list.add(RecordTypeModel("Web & App", itemList2))
        list.add(RecordTypeModel("Map & Location", itemList3))
        return list
    }

}