package io.bouckaert.geojson

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class GeoJSON {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        private val JSON = Json {
            explicitNulls = true
            ignoreUnknownKeys = true
        }

        fun create(input: String): GeoJSON = JSON.decodeFromString(input)

        fun create(input: InputStream): GeoJSON = JSON.decodeFromStream(input)
    }
    override fun toString(): String = JSON.encodeToString<GeoJSON>(this)

    fun writeTo(outputStream: OutputStream) = JSON.encodeToStream<GeoJSON>(this, outputStream)
}
