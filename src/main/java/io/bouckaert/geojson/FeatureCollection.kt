package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("FeatureCollection")
class FeatureCollection (
    val features: List<Feature>
): GeoJSON() {
    @Serializable
    sealed class Feature: GeoJSON()

    @JvmName("getTypedFeatures")
    fun getFeatures(): List<io.bouckaert.geojson.Feature> = features.filterIsInstance<io.bouckaert.geojson.Feature>()
}