package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MultiPolygon")
class MultiPolygon(
    val coordinates: List<List<List<DoubleArray>>>
): Geometry() {
    val bbox: DoubleArray? = null
}
