package com.example.nfcapp

interface NfcReadCallback {
    fun onNfcReadSuccess(message: String)
    fun onNfcReadError(error: String)
}

