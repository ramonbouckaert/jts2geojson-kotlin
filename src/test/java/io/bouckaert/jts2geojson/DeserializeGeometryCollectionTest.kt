package io.bouckaert.jts2geojson

import io.bouckaert.geojson.GeoJSON
import io.bouckaert.geojson.GeometryCollection
import io.bouckaert.geojson.Point
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeserializeGeometryCollectionTest {
    @Test
    fun `deserialize GeometryCollection successfully`() {
        val geoJSON = """{"type": "GeometryCollection", "geometries": [{"type": "Point",  "coordinates": [1.1, 2.2] }]}"""
        val json = GeoJSON.create(geoJSON)
        assertTrue(json is GeometryCollection)
        assertTrue(json.geometries.isNotEmpty())
        val geometry = json.geometries.first()
        assertTrue(geometry is Point)
        assertEquals(geometry.coordinates.toList(), listOf(1.1, 2.2))
    }
}