package io.bouckaert.geojson

import kotlinx.serialization.Serializable

@Serializable
sealed class Geometry: GeoJSON()