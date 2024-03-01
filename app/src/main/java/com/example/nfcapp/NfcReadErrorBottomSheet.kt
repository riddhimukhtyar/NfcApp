package com.example.nfcapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NfcReadErrorBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_nfc_read_error_bottom_sheet, container, false)

        // Setup "Try Again" button action
        view.findViewById<Button>(R.id.idBtnTryAgain).setOnClickListener {
            // Dismiss this bottom sheet
            dismiss()

            // TODO: Add any additional actions for "Try Again", such as re-attempting to read an NFC tag
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = NfcReadErrorBottomSheet()
    }
}
