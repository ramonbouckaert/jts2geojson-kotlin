package io.bouckaert.jts2geojson

import io.bouckaert.geojson.*
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.LinearRing

@Suppress("unused")
object GeoJSONWriter {
    fun write(geometry: org.locationtech.jts.geom.Geometry): GeoJSON {
        val result: GeoJSON = when (geometry.javaClass) {
            org.locationtech.jts.geom.Point::class.java -> convert(geometry as org.locationtech.jts.geom.Point)
            org.locationtech.jts.geom.LineString::class.java -> convert(geometry as org.locationtech.jts.geom.LineString)
            LinearRing::class.java -> convert(geometry as LinearRing)
            org.locationtech.jts.geom.Polygon::class.java -> convert(geometry as org.locationtech.jts.geom.Polygon)
            org.locationtech.jts.geom.MultiPoint::class.java -> convert(geometry as org.locationtech.jts.geom.MultiPoint)
            org.locationtech.jts.geom.MultiLineString::class.java -> convert(geometry as org.locationtech.jts.geom.MultiLineString)
            org.locationtech.jts.geom.MultiPolygon::class.java -> convert(geometry as org.locationtech.jts.geom.MultiPolygon)
            org.locationtech.jts.geom.GeometryCollection::class.java -> convert(geometry as org.locationtech.jts.geom.GeometryCollection)
            else -> throw UnsupportedOperationException()
        }
        return result
    }

    fun write(features: List<Feature>): FeatureCollection = FeatureCollection(features)

    private fun convert(point: org.locationtech.jts.geom.Point): GeoJSON = Point(convert(point.coordinate))

    private fun convert(multiPoint: org.locationtech.jts.geom.MultiPoint): GeoJSON = MultiPoint(convert(multiPoint.coordinates))

    private fun convert(lineString: org.locationtech.jts.geom.LineString): GeoJSON = LineString(convert(lineString.coordinates))

    private fun convert(ringString: LinearRing): GeoJSON = LineString(convert(ringString.coordinates))

    private fun convert(multiLineString: org.locationtech.jts.geom.MultiLineString): GeoJSON {
        val size = multiLineString.numGeometries
        val lineStrings: MutableList<List<DoubleArray>> = mutableListOf()
        for (i in 0 until size) lineStrings.add(convert(multiLineString.getGeometryN(i).coordinates))
        return MultiLineString(lineStrings)
    }

    private fun convert(polygon: org.locationtech.jts.geom.Polygon): GeoJSON {
        val size = polygon.numInteriorRing + 1
        val rings: MutableList<List<DoubleArray>> = mutableListOf()
        rings.add(convert(polygon.exteriorRing.coordinates))
        for (i in 0 until size - 1) rings.add(convert(polygon.getInteriorRingN(i).coordinates))
        return Polygon(rings)
    }

    private fun convert(multiPolygon: org.locationtech.jts.geom.MultiPolygon): GeoJSON {
        val size = multiPolygon.numGeometries
        val polygons: MutableList<List<List<DoubleArray>>> = mutableListOf()
        for (i in 0 until size) polygons.add(
            (convert(multiPolygon.getGeometryN(i) as org.locationtech.jts.geom.Polygon) as Polygon).coordinates
        )
        return MultiPolygon(polygons)
    }

    private fun convert(gc: org.locationtech.jts.geom.GeometryCollection): GeoJSON {
        val size = gc.numGeometries
        val geometries: MutableList<Geometry> = mutableListOf()
        for (i in 0 until size) geometries.add(write(gc.getGeometryN(i)) as Geometry)
        return GeometryCollection(geometries)
    }

    private fun convert(coordinate: Coordinate): DoubleArray {
        return if (java.lang.Double.isNaN(coordinate.getZ())) doubleArrayOf(
            coordinate.x,
            coordinate.y
        ) else doubleArrayOf(coordinate.x, coordinate.y, coordinate.getZ())
    }

    private fun convert(coordinates: Array<Coordinate>): List<DoubleArray> = coordinates.map { convert(it) }

    private val reader = GeoJSONReader
}
