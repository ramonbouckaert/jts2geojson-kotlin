package io.bouckaert.geojson

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Serializable
@SerialName("Feature")
class Feature @OptIn(ExperimentalSerializationApi::class) constructor(
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val id: JsonPrimitive? = null,
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    val geometry: Geometry? = null,
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    @SerialName("properties")
    private val _properties: Map<String, JsonElement>? = null
) : FeatureCollection.Feature() {
    constructor(
        geometry: Geometry?,
        properties: Map<String, Any>? = null
    ) : this(null as JsonPrimitive?, geometry, mapPropertiesToPrimitives(properties))
    constructor(
        id: String?,
        geometry: Geometry?,
        properties: Map<String, Any>? = null
    ) : this(id?.let(::JsonPrimitive), geometry, mapPropertiesToPrimitives(properties))
    constructor(
        id: Number?,
        geometry: Geometry?,
        properties: Map<String, Any>? = null
    ) : this(id?.let(::JsonPrimitive), geometry, mapPropertiesToPrimitives(properties))

    val properties: Map<String, Any>? get() = mapPrimitivesToProperties(_properties)

    companion object {
        @JvmStatic
        private fun mapPropertiesToPrimitives(properties: Map<String, Any>?): Map<String, JsonElement>? = properties?.mapValues { entry ->
            when (val value = entry.value) {
                is Boolean -> JsonPrimitive(value)
                is Number -> JsonPrimitive(value)
                is String -> JsonPrimitive(value)
                is Map<*, *>, is Iterable<*> -> throw NotImplementedError()
                else -> throw UnsupportedOperationException()
            }
        }

        @JvmStatic
        private fun mapPrimitivesToProperties(properties: Map<String, JsonElement>?): Map<String, Any>? = properties?.mapValues { entry ->
            when (val value = entry.value) {
                is JsonPrimitive -> value.content
                is JsonArray, is JsonObject -> throw NotImplementedError()
            }
        }
    }
}
