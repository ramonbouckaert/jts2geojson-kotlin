package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Polygon")
class Polygon(
    val coordinates: List<List<DoubleArray>>
): Geometry() {
    val bbox: DoubleArray? = null
}
