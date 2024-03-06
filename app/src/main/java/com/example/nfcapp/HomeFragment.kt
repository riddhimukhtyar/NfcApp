package com.example.nfcapp

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), NfcReadCallback {

    private val nfcReadScanBottomTag = "NfcReadScanBottomTag"

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val readNfcCard: MaterialCardView = view.findViewById(R.id.readNfc)
        readNfcCard.setOnClickListener {
            showNfcReadBottomSheet()
        }

        val writeNfcCard: MaterialCardView = view.findViewById(R.id.writeNfc)
        writeNfcCard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WriteNfcTag.newInstance("param1", "param2"))
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun showNfcReadBottomSheet() {
        val nfcReadScanBottomFragment = NfcReadScanBottom.newInstance().apply {
            nfcReadCallback = this@HomeFragment
        }
        nfcReadScanBottomFragment.show(parentFragmentManager, nfcReadScanBottomTag)
    }

    override fun onNfcReadSuccess(message: String) {
        navigateToScannedNfcTagWithMessage(message)
    }

    override fun onNfcReadError(error: String) {
        Snackbar.make(requireView(), "NFC Read Error: $error", Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToScannedNfcTagWithMessage(message: String) {
        val scannedNfcTagFragment = ScannedNfcTag().apply {
            arguments = Bundle().apply {
                putString("message", message)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, scannedNfcTagFragment, "ScannedNfcTag")
            .addToBackStack(null)
            .commit()
    }

    fun handleIntent(intent: Intent?) {
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        tag?.let {
            val ndef = Ndef.get(it)
            val nfcReadScanBottomFragment = parentFragmentManager.findFragmentByTag(nfcReadScanBottomTag) as? NfcReadScanBottom
            nfcReadScanBottomFragment?.readNfcTag(ndef)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
