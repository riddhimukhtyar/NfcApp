package com.example.nfcapp

import android.nfc.NdefMessage
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.IOException

class NfcReadScanBottom : BottomSheetDialogFragment() {

    private lateinit var messageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nfc_read_scan_bottom, container, false)
        val cancelButton: Button = view.findViewById(R.id.idBtncancel)
        messageTextView = view.findViewById(R.id.tv_message)

        cancelButton.setOnClickListener {
            dismiss() // This dismisses the bottom sheet
        }

        return view
    }

    fun readNfcTag(ndef: Ndef) {
        if (!ndef.isConnected) {
            try {
                ndef.connect()
                val ndefMessage: NdefMessage = ndef.ndefMessage ?: throw Exception("NDEF message is null")
                val message = String(ndefMessage.records[0].payload)
                messageTextView.text = message
                // Display success bottom sheet
                NfcReadSuccessBottomSheet().show(parentFragmentManager, "NfcReadSuccessBottomSheet")
            } catch (e: Exception) { // Catching all exceptions to show error bottom sheet
                Log.e("NFCRead", "Error when reading NFC tag", e)
                // Display error bottom sheet
                NfcReadErrorBottomSheet().show(parentFragmentManager, "NfcReadErrorBottomSheet")
            } finally {
                if (ndef.isConnected) {
                    try {
                        ndef.close()
                    } catch (e: IOException) {
                        Log.e("NFCRead", "IOException when closing NFC connection", e)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NfcReadScanBottom()
    }
}
