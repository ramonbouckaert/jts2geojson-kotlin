package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("FeatureCollection")
class FeatureCollection (
    @SerialName("features")
    private val _features: List<Feature>
): GeoJSON() {
    @Serializable
    sealed class Feature: GeoJSON()

    @Suppress("UNCHECKED_CAST")
    val features get() = _features as List<io.bouckaert.geojson.Feature>
}