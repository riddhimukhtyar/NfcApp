package com.example.nfcapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nfcapp.databinding.FragmentScannedNfcTagBinding
import com.google.android.material.snackbar.Snackbar

class ScannedNfcTag : Fragment() {
    private var _binding: FragmentScannedNfcTagBinding? = null
    private val binding get() = _binding!!
    private lateinit var scanItemAdapter: ScanItemAdapter
    private var nfcMessage: String? = null
    private lateinit var databaseHelper: DatabaseHelper // Declare the DatabaseHelper

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): ScannedNfcTag = ScannedNfcTag().apply {
            arguments = Bundle().apply {
                putString(ARG_MESSAGE, message)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScannedNfcTagBinding.inflate(inflater, container, false)
        databaseHelper = DatabaseHelper(requireContext()) // Initialize the DatabaseHelper
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        arguments?.getString(ARG_MESSAGE)?.let { message ->
            nfcMessage = message
            onNfcReadSuccess(message)
        }

        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val saveButton = view.findViewById<TextView>(R.id.saveButton)
        saveButton?.setOnClickListener {
            // Save NFC message to the SQLite database
            nfcMessage?.let { message ->
                databaseHelper.saveMessage(message)
                Toast.makeText(requireContext(), "NFC message saved to database", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(requireContext(), "Error: No NFC message to save", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupRecyclerView() {
        scanItemAdapter = ScanItemAdapter(mutableListOf()) { scanItem ->
            nfcMessage?.let {
                val tagDetailsFragment = TagDetails.newInstance(it)
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_container, tagDetailsFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.messageRecyclerView.adapter = scanItemAdapter
    }

    fun onNfcReadSuccess(message: String) {
        val readMessage = ScanItem(R.drawable.text_format, "Text :", "${message.length} bytes")
        scanItemAdapter.updateData(listOf(readMessage))
    }

    fun onNfcReadError(error: String) {
        Snackbar.make(binding.root, "NFC Read Error: $error", Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

