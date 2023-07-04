package io.bouckaert.geojson

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonClassDiscriminator

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
    }
    override fun toString(): String = JSON.encodeToString<GeoJSON>(this)
}
