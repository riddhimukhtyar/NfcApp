package com.example.nfcapp

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val splashDuration = 3000L // Splash screen duration in milliseconds (3 seconds)
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the NFC adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance(), "HomeFragmentTag")
                    .commitAllowingStateLoss()
            }
        }, splashDuration)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        val intentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            intentFilter.addDataType("*/*")    // Handle all MIME types
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("NFC setup failed", e)
        }
        val intentFiltersArray = arrayOf(intentFilter)
        val techListsArray = arrayOf(arrayOf(Ndef::class.java.name))
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Pass the intent to the HomeFragment for processing
        val homeFragment = supportFragmentManager.findFragmentByTag("HomeFragmentTag")
        if (homeFragment is HomeFragment) {
            homeFragment.handleIntent(intent)
        }
    }
}
