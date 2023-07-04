package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MultiLineString")
class MultiLineString(
    val coordinates: List<List<DoubleArray>>
): Geometry() {
    val bbox: DoubleArray? = null
}
