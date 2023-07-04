package io.bouckaert.jts2geojson

import org.junit.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import kotlin.test.assertEquals

class GeoJsonReaderTest {
    @Test
    fun `reading JTS object from GeoJSON`() {
        val reader = GeoJSONReader
        val factory = GeometryFactory()
        val srid = 25832
        val factorySrid = GeometryFactory(PrecisionModel(), srid)

        val coordArray = arrayOf(
            Coordinate(1.0, 1.0),
            Coordinate(1.0, 2.0),
            Coordinate(2.0, 2.0),
            Coordinate(1.0, 1.0)
        )

        // Expected result for Point
        val expectedPoint = factory.createPoint(Coordinate(1.0, 1.0))
        val expectedPointSrid = factorySrid.createPoint(Coordinate(1.0, 1.0))
        val jsonPoint = """{"type":"Point","coordinates":[1.0,1.0]}"""

        var geometry = reader.read(jsonPoint)
        assertEquals(expectedPoint, geometry)
        assertEquals(0, geometry.srid)

        geometry = reader.read(jsonPoint, factorySrid)
        assertEquals(expectedPointSrid, geometry)
        assertEquals(srid, geometry.srid)

        // Expected result for MultiPoint
        val expectedMultiPoint = factory.createMultiPointFromCoords(coordArray)
        val expectedMultiPointSrid = factorySrid.createMultiPointFromCoords(coordArray)
        val jsonMultiPoint = """{"type":"MultiPoint","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}"""

        geometry = reader.read(jsonMultiPoint)
        assertEquals(expectedMultiPoint, geometry)
        assertEquals(0, geometry.srid)

        geometry = reader.read(jsonMultiPoint, factorySrid)
        assertEquals(expectedMultiPointSrid, geometry)
        assertEquals(srid, geometry.srid)
    }

    @Test
    fun `reading XYZ JTS object from GeoJSON`() {
        val reader = GeoJSONReader
        val factory = GeometryFactory()
        val srid = 25832
        val factorySrid = GeometryFactory(PrecisionModel(), srid)

        val coordArray = arrayOf(
            Coordinate(1.0, 1.0, 1.0),
            Coordinate(1.0, 2.0, 1.0),
            Coordinate(2.0, 2.0, 2.0),
            Coordinate(1.0, 1.0, 1.0)
        )

        // Expected result for Point
        val expectedPoint = factory.createPoint(Coordinate(1.0, 1.0, 1.0))
        val expectedPointSrid = factorySrid.createPoint(Coordinate(1.0, 1.0, 1.0))
        val jsonPoint = """{"type":"Point","coordinates":[1.0,1.0,1.0]}"""

        var geometry = reader.read(jsonPoint)
        assertEquals(expectedPoint, geometry)
        assertEquals(0, geometry.srid)

        geometry = reader.read(jsonPoint, factorySrid)
        assertEquals(expectedPointSrid, geometry)
        assertEquals(srid, geometry.srid)

        // Expected result for MultiPoint
        val expectedMultiPoint = factory.createMultiPointFromCoords(coordArray)
        val expectedMultiPointSrid = factorySrid.createMultiPointFromCoords(coordArray)
        val jsonMultiPoint = """{"type":"MultiPoint","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}"""

        geometry = reader.read(jsonMultiPoint)
        assertEquals(expectedMultiPoint, geometry)
        assertEquals(0, geometry.srid)

        geometry = reader.read(jsonMultiPoint, factorySrid)
        assertEquals(expectedMultiPointSrid, geometry)
        assertEquals(srid, geometry.srid)
    }
}