package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Point")
class Point(
    val coordinates: DoubleArray
): Geometry() {
    val bbox: DoubleArray? = null
}
