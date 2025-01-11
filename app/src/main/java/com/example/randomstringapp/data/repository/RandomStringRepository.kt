package com.example.randomstringapp.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.randomstringapp.data.model.RandomString
import org.json.JSONObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RandomStringRepository(private val context: Context) {

    private val contentUri = Uri.parse("content://com.iav.contestdataprovider/text")

    fun fetchRandomString(length: Int): Result<RandomString> {

        return try {
            val selection = "${ContentResolver.QUERY_ARG_LIMIT} = ?"
            val selectionArgs = arrayOf(
                length.toString()
            )

            val cursor = context.contentResolver.query(
                contentUri,
                null,
                selection,
                selectionArgs,
                null
            ) ?: throw IllegalStateException("Cursor is null")

            cursor.use {
                if (cursor.moveToFirst()) {
                    val jsonString = cursor.getString(cursor.getColumnIndexOrThrow("data"))
                    val jsonObject = JSONObject(jsonString).getJSONObject("randomText")
                    val value = jsonObject.getString("value").substring(0, length)
                    val created = jsonObject.getString("created")
                    val formattedTime = ZonedDateTime.parse(created).format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                    )

                    RandomString(value, length, formattedTime).let { Result.success(it) }
                } else {
                    Result.failure(Exception("No data found"))
                }
            }


        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}