package io.bouckaert.geojson

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("GeometryCollection")
class GeometryCollection(
    val geometries: List<Geometry>
): Geometry()
