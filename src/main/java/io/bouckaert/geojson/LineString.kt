package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("LineString")
class LineString(
    val coordinates: List<DoubleArray>
) : Geometry() {
    val bbox: DoubleArray? = null
}
