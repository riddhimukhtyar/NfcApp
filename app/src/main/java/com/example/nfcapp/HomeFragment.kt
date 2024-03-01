package com.example.nfcapp

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

class HomeFragment : Fragment() {

    private val nfcReadScanBottomTag = "NfcReadScanBottomTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val readNfcCard: MaterialCardView = view.findViewById(R.id.addNotice)
        readNfcCard.setOnClickListener {
            // Show NFC read fragment here without passing NFC tag,
            // since tag passing will be handled in onNewIntent
            val nfcReadScanBottomFragment = NfcReadScanBottom.newInstance()
            nfcReadScanBottomFragment.show(parentFragmentManager, nfcReadScanBottomTag)
        }

        return view
    }

    fun handleIntent(intent: Intent?) {
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        tag?.let {
            val ndef = Ndef.get(it)
            // Find the NFCReadScanBottom if it's already shown
            val nfcReadScanBottomFragment = parentFragmentManager.findFragmentByTag(nfcReadScanBottomTag) as? NfcReadScanBottom
            nfcReadScanBottomFragment?.let { fragment ->
                // Pass the NFC tag to the fragment for processing
                fragment.readNfcTag(ndef)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
