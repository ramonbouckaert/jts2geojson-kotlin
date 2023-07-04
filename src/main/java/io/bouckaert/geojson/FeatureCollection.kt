package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("FeatureCollection")
class FeatureCollection (
    val features: List<Feature>
): GeoJSON()
