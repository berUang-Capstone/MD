package com.example.subs_inter.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object Utils {
    fun extractDate(dateTime: String): String {
        return dateTime.substring(0, 10) // Extract the first 10 characters
    }

    fun getImageByteArrayFromUri(uri: Uri, context: Context): ByteArray? {
        val contentResolver: ContentResolver = context.contentResolver
        var inputStream: InputStream? = null
        try {
            inputStream = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val buffer = ByteArrayOutputStream()
                val byteArray = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(byteArray).also { bytesRead = it } != -1) {
                    buffer.write(byteArray, 0, bytesRead)
                }
                return buffer.toByteArray()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
}