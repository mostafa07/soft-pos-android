package com.mx3.softpos.nfc

import android.nfc.tech.IsoDep
import com.github.devnied.emvnfccard.exception.CommunicationException

import com.github.devnied.emvnfccard.parser.IProvider
import java.io.IOException

class Provider : IProvider {

    private var mTagCom: IsoDep? = null

    @Throws(CommunicationException::class)
    override fun transceive(pCommand: ByteArray): ByteArray {
        var response: ByteArray?

        response = try {
            // send command to emv card
            mTagCom!!.transceive(pCommand)
        } catch (e: IOException) {
            throw CommunicationException(e.message)
        }
        return response
    }

    override fun getAt(): ByteArray {
        TODO("Not yet implemented")
    }

    fun setTagCom(mTagCom: IsoDep?) {
        this.mTagCom = mTagCom
    }
}
