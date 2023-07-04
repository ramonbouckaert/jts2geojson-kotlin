package io.bouckaert.geojson

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.*

open class WrappedSerializer<T : Any>(serialiser: KSerializer<T>): JsonTransformingSerializer<T>(serialiser) {
    override fun transformSerialize(element: JsonElement): JsonElement {
        return when (element) {
            is JsonObject -> JsonObject(element.jsonObject.entries.fold(mapOf()) { map, entry ->
                when (entry.key) {
                    // Replace empty properties object with explicit null, and don't transform children of properties
                    "properties" -> when (entry.value) {
                        is JsonObject -> if ((entry.value as JsonObject).entries.isEmpty()) {
                            map.plus(entry.key to JsonNull)
                        } else {
                            map.plus(entry.key to entry.value)
                        }
                        else -> map.plus(entry.key to JsonNull)
                    }
                    // Always remove "class" class discriminator key
                    "class" -> map.plus("type" to entry.value)
                    else -> map.plus(entry.key to transformSerialize(entry.value))
                }
            })
            is JsonArray -> JsonArray(element.jsonArray.map { transformSerialize(it) })
            else -> element
        }
    }

    override fun transformDeserialize(element: JsonElement): JsonElement {
        return when (element) {
            is JsonObject -> JsonObject(element.jsonObject.entries.fold(mapOf()) { map, entry ->
                when (entry.key) {
                    // Replace null properties object with empty map, and don't transform children of properties
                    "properties" -> when (entry.value) {
                        is JsonObject -> if ((entry.value as JsonObject).entries.isEmpty()) {
                            map.plus(entry.key to JsonObject(emptyMap()))
                        } else {
                            map.plus(entry.key to entry.value)
                        }
                        else -> map.plus(entry.key to JsonObject(emptyMap()))
                    }
                    // Duplicate "type" key to serve as "class" discriminator key
                    "type" -> map.plus(mapOf(
                        entry.key to entry.value,
                        "class" to entry.value
                    ))
                    else -> map.plus(entry.key to transformSerialize(entry.value))
                }
            })
            is JsonArray -> JsonArray(element.jsonArray.map { transformSerialize(it) })
            else -> element
        }
    }
}