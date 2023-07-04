package io.bouckaert.geojson

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
sealed class GeoJSON {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        private val JSON = Json {
            explicitNulls = true
            ignoreUnknownKeys = true
        }

        fun create(input: String): GeoJSON = JSON.decodeFromString(input)
    }

    @Required
    val injectType: String = javaClass.simpleName
    override fun toString(): String = JSON.encodeToString(this)
}
