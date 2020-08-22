package com.mx3.softpos.ui.activity

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.devnied.emvnfccard.model.EmvCard
import com.github.devnied.emvnfccard.parser.EmvTemplate
import com.github.devnied.emvnfccard.parser.IProvider
import com.mx3.softpos.R
import com.mx3.softpos.nfc.Provider
import org.apache.commons.lang3.ClassUtils.getSimpleName


class MainActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private val TAG = MainActivity::class.simpleName

    private lateinit var mCard: EmvCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                // TODO Process the messages array.


                // Create provider
                val provider: IProvider = Provider()

                // Define config
                val config: EmvTemplate.Config = EmvTemplate.Config()
                    .setContactLess(true) // Enable contact less reading (default: true)
                    .setReadAllAids(true) // Read all aids in card (default: true)
                    .setReadTransactions(true) // Read all transactions (default: true)
                    .setReadCplc(false) // Read and extract CPCLC data (default: false)
                    .setRemoveDefaultParsers(false) // Remove default parsers for GeldKarte and EmvCard (default: false)
                    .setReadAt(true) // Read and extract ATR/ATS and description

                // Create Parser
                val parser = EmvTemplate.Builder()
                    .setProvider(provider) // Define provider
                    .setConfig(config) // Define config
                    //.setTerminal(terminal) (optional) you can define a custom terminal implementation to create APDU
                    .build()

                // Read card
                mCard = parser.readEmvCard()

                Log.d(TAG, "Card Details: $mCard")
            }
        }
    }

    override fun onTagDiscovered(p0: Tag?) {
        TODO("Not yet implemented")
    }
}