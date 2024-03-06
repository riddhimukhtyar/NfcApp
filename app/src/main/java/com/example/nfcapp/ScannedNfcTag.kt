package com.example.nfcapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nfcapp.databinding.FragmentScannedNfcTagBinding
import com.google.android.material.snackbar.Snackbar

class ScannedNfcTag : Fragment() {
    private var _binding: FragmentScannedNfcTagBinding? = null
    private val binding get() = _binding!!
    private lateinit var scanItemAdapter: ScanItemAdapter

    // Use a companion object to handle the creation of new instances with arguments
    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): ScannedNfcTag = ScannedNfcTag().apply {
            arguments = Bundle().apply {
                putString(ARG_MESSAGE, message)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannedNfcTagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // Retrieve and use the NFC message passed as an argument
        arguments?.getString(ARG_MESSAGE)?.let { message ->
            onNfcReadSuccess(message)
        }
    }

    private fun setupRecyclerView() {
        scanItemAdapter = ScanItemAdapter(mutableListOf())
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.messageRecyclerView.adapter = scanItemAdapter
    }

    fun onNfcReadSuccess(message: String) {
        // Update your RecyclerView with the message here
        val readMessage = ScanItem(R.drawable.text_format, message, "${message.length} bytes", "NFC Read Success")
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