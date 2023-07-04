package io.bouckaert.geojson

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
@SerialName("Feature")
class Feature @OptIn(ExperimentalSerializationApi::class) constructor(
    val id: JsonPrimitive? = null,
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    val geometry: Geometry? = null,
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    val properties: Map<String, JsonElement>? = null
) : FeatureCollection.Feature() {
    constructor(
        geometry: Geometry?,
        properties: Map<String, Any>? = null
    ) : this(null, geometry, mapProperties(properties))
    constructor(
        id: String,
        geometry: Geometry?,
        properties: Map<String, Any>? = null
    ) : this(JsonPrimitive(id), geometry, mapProperties(properties))
    constructor(
        id: Number,
        geometry: Geometry?,
        properties: Map<String, Any>? = null
    ) : this(JsonPrimitive(id), geometry, mapProperties(properties))

    companion object {
        @JvmStatic
        fun mapProperties(properties: Map<String, Any>?): Map<String, JsonElement>? = properties?.mapValues { entry ->
            when (val value = entry.value) {
                is Boolean -> JsonPrimitive(value)
                is Number -> JsonPrimitive(value)
                is String -> JsonPrimitive(value)
                is Map<*, *>, is Iterable<*> -> throw NotImplementedError()
                else -> throw UnsupportedOperationException()
            }
        }
    }
}
