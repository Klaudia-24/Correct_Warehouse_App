package com.example.correct_warehouse_app.utils

import java.security.MessageDigest

object HashString {

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    private fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    @JvmStatic
    fun hash(input: String): String {
        return input.toMD5()
    }

}