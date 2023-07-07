package io.bouckaert.jts2geojson

import io.bouckaert.geojson.*
import kotlinx.serialization.json.contentOrNull
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import java.io.InputStream

object GeoJSONReader {
    @JvmOverloads
    fun read(json: String, geomFactory: GeometryFactory? = null): Geometry {
        return read(GeoJSON.create(json), geomFactory)
    }

    @JvmOverloads
    fun read(geoJSON: GeoJSON, geomFactory: GeometryFactory? = null): Geometry {
        val factory = geomFactory ?: FACTORY
        return when (geoJSON) {
            is Point -> convert(geoJSON, factory)
            is LineString -> convert(geoJSON, factory)
            is Polygon -> convert(geoJSON, factory)
            is MultiPoint -> convert(geoJSON, factory)
            is MultiLineString -> convert(geoJSON, factory)
            is MultiPolygon -> convert(geoJSON, factory)
            is GeometryCollection -> convert(geoJSON, factory)
            is Feature -> convert(geoJSON, factory)
            is FeatureCollection -> convert(geoJSON, factory)
        }
    }

    @JvmOverloads
    fun read(inputStream: InputStream, geomFactory: GeometryFactory? = null): Geometry {
        return read(GeoJSON.create(inputStream), geomFactory)
    }

    private fun convert(featureCollection: FeatureCollection, factory: GeometryFactory): Geometry =
        factory.createGeometryCollection(
            featureCollection.features.map { convert(it, factory) }.toTypedArray()
        )

    private fun convert(feature: Feature, factory: GeometryFactory): Geometry {
        val geom = feature.geometry
            ?.let { this.read(it, factory) } ?: factory.createEmpty(-1)
        geom.userData = JtsFeatureMetadata(feature.id?.contentOrNull, feature.properties)
        return geom
    }

    private fun convert(point: Point, factory: GeometryFactory): Geometry =
        factory.createPoint(convert(point.coordinates))

    private fun convert(multiPoint: MultiPoint, factory: GeometryFactory): Geometry =
        factory.createMultiPointFromCoords(convert(multiPoint.coordinates).toTypedArray())

    private fun convert(lineString: LineString, factory: GeometryFactory): Geometry =
        factory.createLineString(convert(lineString.coordinates).toTypedArray())

    private fun convert(multiLineString: MultiLineString, factory: GeometryFactory): Geometry =
        factory.createMultiLineString(multiLineString.coordinates.map { factory.createLineString(convert(it).toTypedArray()) }.toTypedArray())

    private fun convert(polygon: Polygon, factory: GeometryFactory): Geometry =
        convertToPolygon(polygon.coordinates, factory)

    private fun convertToPolygon(
        coordinates: List<List<DoubleArray>>,
        factory: GeometryFactory
    ): org.locationtech.jts.geom.Polygon {
        val linearRings = coordinates.map { factory.createLinearRing(convert(it).toTypedArray()) }

        return if (linearRings.size > 1) {
            factory.createPolygon(linearRings.first(), linearRings.take(linearRings.size - 1).toTypedArray())
        } else {
            factory.createPolygon(linearRings.first())
        }
    }

    private fun convert(multiPolygon: MultiPolygon, factory: GeometryFactory): Geometry =
        factory.createMultiPolygon(multiPolygon.coordinates.map { convertToPolygon(it, factory) }.toTypedArray())

    private fun convert(gc: GeometryCollection, factory: GeometryFactory): Geometry =
        factory.createGeometryCollection(gc.geometries.map { read(it, factory) }.toTypedArray())

    private fun convert(c: DoubleArray): Coordinate =
        when (c.size) {
            2 -> Coordinate(c[0], c[1])
            3 -> Coordinate(c[0], c[1], c[2])
            else -> throw UnsupportedOperationException()
        }

    private fun convert(ca: List<DoubleArray>): List<Coordinate> = ca.map { convert(it) }

    private val FACTORY = GeometryFactory(PrecisionModel(PrecisionModel.FLOATING))
}
