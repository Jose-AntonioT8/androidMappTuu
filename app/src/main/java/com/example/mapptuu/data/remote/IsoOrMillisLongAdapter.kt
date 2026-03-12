package com.example.mapptuu.data.remote

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Gson TypeAdapter para Long: acepta en JSON un número (milisegundos) o un string ISO (ej. "2026-03-11T23:55:47.365Z").
 * Al escribir siempre se serializa como número.
 */
class IsoOrMillisLongAdapter : TypeAdapter<Long>() {
    override fun read(reader: JsonReader): Long {
        return when (reader.peek()) {
            JsonToken.NUMBER -> reader.nextLong()
            JsonToken.STRING -> parseIsoToMillis(reader.nextString())
            else -> 0L
        }
    }

    override fun write(writer: JsonWriter, value: Long?) {
        if (value != null) writer.value(value) else writer.nullValue()
    }

    private fun parseIsoToMillis(iso: String): Long {
        return try {
            val withMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val withoutMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            (withMillis.parse(iso) ?: withoutMillis.parse(iso))?.time ?: 0L
        } catch (_: Exception) {
            0L
        }
    }
}
