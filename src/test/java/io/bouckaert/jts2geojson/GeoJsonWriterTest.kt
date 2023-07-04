package io.bouckaert.jts2geojson

import io.bouckaert.geojson.Feature
import io.bouckaert.geojson.FeatureCollection
import io.bouckaert.geojson.GeoJSON
import io.bouckaert.geojson.Geometry
import org.junit.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import kotlin.test.assertEquals

class GeoJsonWriterTest {
    @Test
    fun `writing GeoJSON from JTS object`() {
        val reader = GeoJSONReader
        val writer = GeoJSONWriter
        val factory = GeometryFactory()

        val point = factory.createPoint(Coordinate(1.0, 1.0))
        val lineString = factory.createLineString(
            arrayOf(
                Coordinate(1.0, 1.0),
                Coordinate(1.0, 2.0),
                Coordinate(2.0, 2.0),
                Coordinate(1.0, 1.0)
            )
        )

        val polygon = factory.createPolygon(lineString.coordinates)

        // Expected result for point
        val expectedPointJson: GeoJSON = writer.write(point)
        assertEquals("""{"type":"Point","coordinates":[1.0,1.0]}""", expectedPointJson.toString())

        val expectedReconstructedGeometry = reader.read(expectedPointJson)
        assertEquals("POINT (1 1)", expectedReconstructedGeometry.toString())

        // Expected result for LineString
        assertEquals(
            """{"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""",
            writer.write(lineString).toString()
        )

        // Expected result for LinearRing
        val ring = factory.createLinearRing(lineString.coordinates)
        assertEquals(
            """{"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""",
            writer.write(ring).toString()
        )

        // Expected result for Polygon
        assertEquals(
            """{"type":"Polygon","coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]}""",
            writer.write(polygon).toString()
        )

        // Expected result for MultiPoint
        val multiPoint = factory.createMultiPointFromCoords(lineString.coordinates)
        assertEquals(
            """{"type":"MultiPoint","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""",
            writer.write(multiPoint).toString()
        )

        // Expected result for MultiLineString
        val multiLineString = factory.createMultiLineString(arrayOf(lineString, lineString))
        assertEquals(
            """{"type":"MultiLineString","coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]],[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]}""",
            writer.write(multiLineString).toString()
        )

        // Expected result for MultiPolygon
        val multiPolygon = factory.createMultiPolygon(arrayOf(polygon, polygon))
        assertEquals(
            """{"type":"MultiPolygon","coordinates":[[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]],[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]]}""",
            writer.write(multiPolygon).toString()
        )

        // Expected result for FeatureCollection
        val featureCollection = FeatureCollection(listOf(Feature(expectedPointJson as Geometry), Feature(expectedPointJson)))
        assertEquals(
            """{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":null},{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":null}]}""",
            featureCollection.toString()
        )
    }

    @Test
    fun `writing XYZ GeoJSON from JTS object`() {
        val reader = GeoJSONReader
        val writer = GeoJSONWriter
        val factory = GeometryFactory()

        val point = factory.createPoint(Coordinate(1.0, 1.0, 1.0))
        val lineString = factory.createLineString(
            arrayOf(
                Coordinate(1.0, 1.0, 1.0),
                Coordinate(1.0, 2.0, 1.0),
                Coordinate(2.0, 2.0, 2.0),
                Coordinate(1.0, 1.0, 1.0)
            )
        )

        val polygon = factory.createPolygon(lineString.coordinates)

        // Expected result for point
        val expectedPointJson: GeoJSON = writer.write(point)
        assertEquals("""{"type":"Point","coordinates":[1.0,1.0,1.0]}""", expectedPointJson.toString())

        val expectedReconstructedGeometry = reader.read(expectedPointJson)
        assertEquals("POINT (1 1)", expectedReconstructedGeometry.toString())

        // Expected result for LineString
        assertEquals(
            """{"type":"LineString","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}""",
            writer.write(lineString).toString()
        )

        // Expected result for LinearRing
        val ring = factory.createLinearRing(lineString.coordinates)
        assertEquals(
            """{"type":"LineString","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}""",
            writer.write(ring).toString()
        )

        // Expected result for Polygon
        assertEquals(
            """{"type":"Polygon","coordinates":[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]]}""",
            writer.write(polygon).toString()
        )

        // Expected result for MultiPoint
        val multiPoint = factory.createMultiPointFromCoords(lineString.coordinates)
        assertEquals(
            """{"type":"MultiPoint","coordinates":[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]}""",
            writer.write(multiPoint).toString()
        )

        // Expected result for MultiLineString
        val multiLineString = factory.createMultiLineString(arrayOf(lineString, lineString))
        assertEquals(
            """{"type":"MultiLineString","coordinates":[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]],[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]]}""",
            writer.write(multiLineString).toString()
        )

        // Expected result for MultiPolygon
        val multiPolygon = factory.createMultiPolygon(arrayOf(polygon, polygon))
        assertEquals(
            """{"type":"MultiPolygon","coordinates":[[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]],[[[1.0,1.0,1.0],[1.0,2.0,1.0],[2.0,2.0,2.0],[1.0,1.0,1.0]]]]}""",
            writer.write(multiPolygon).toString()
        )

        // Expected result for FeatureCollection
        val featureCollection = FeatureCollection(listOf(Feature(expectedPointJson as Geometry), Feature(expectedPointJson)))
        assertEquals(
            """{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":null},{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0,1.0]},"properties":null}]}""",
            featureCollection.toString()
        )
    }
}