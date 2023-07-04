package io.bouckaert.jts2geojson

import io.bouckaert.geojson.Feature
import io.bouckaert.geojson.FeatureCollection
import io.bouckaert.geojson.GeoJSON
import io.bouckaert.geojson.Point
import org.junit.Test
import kotlin.test.assertEquals

class GeoJsonSerializerTest {
    @Test
    fun `read missing geometry field as nulls`() {
        val json = """{"type":"Feature","properties":{"test":1}}"""
        val expected = """{"type":"Feature","geometry":null,"properties":{"test":1}}"""

        val geoJson = GeoJSON.create(json)
        assertEquals(expected, geoJson.toString())
    }

    @Test
    fun `parsing GeoJSON to object`() {
        val geometry = Point(doubleArrayOf(1.0, 1.0))
        val properties = mapOf("test" to 1 as Any)
        val feature = Feature(geometry, properties)

        // Be identical to programmatically created Feature
        val expected1 = """{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}}"""

        val json1 = feature.toString()
        assertEquals(expected1, json1)

        val geoJson1 = GeoJSON.create(json1)
        assertEquals(expected1, geoJson1.toString())

        // Be identical to programmatically created feature with id
        val featureWithId = Feature(1, geometry, properties)
        val expected2 = """{"type":"Feature","id":1,"geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}}"""

        val json2 = featureWithId.toString()
        assertEquals(expected2, json2)

        val geoJson2 = GeoJSON.create(json2)
        assertEquals(expected2, geoJson2.toString())

        // Be identical to programmatically created feature without geometry
        val featureWithoutGeometry = Feature(null, properties)
        val expected3 = """{"type":"Feature","geometry":null,"properties":{"test":1}}"""

        val json3 = featureWithoutGeometry.toString()
        assertEquals(expected3, json3)

        val geoJson3 = GeoJSON.create(json3)
        assertEquals(expected3, geoJson3.toString())

        // Be identical to programmatically created FeatureCollection
        val featureCollection = FeatureCollection(listOf(feature, feature))
        val expected4 = """{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}},{"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"test":1}}]}"""

        val json4 = featureCollection.toString()
        assertEquals(expected4, json4)

        val geoJson4 = GeoJSON.create(json4)
        assertEquals(expected4, geoJson4.toString())

        // Take care of bbox property
        val geoJSON = """{"type": "FeatureCollection", "features": [{"type": "Feature", "id": 1, "geometry": {"type": "Point", "coordinates": [-8.311419016226296, 53.894485921596285], "bbox": [-8.311419016226296, 53.894485921596285, -8.311419016226296, 53.894485921596285] }, "properties": {"FID": 335, "PLAN_REF": "151", "APP_TYPE": "RETENTION", "LOCATION": "Knockglass, Ballinameen, Co. Roscommon.", "REC_DATE": "05/01/2015", "DESCRIPT": "Of entrance to existing forest plantation for extraction of timber at various times at ", "APPSTATUS": "Application Finalised", "DEC_DATE": "20/02/2015", "DECISION": "Granted (Conditional)", "APPE_DEC": "n/a", "APPE_DAT": "n/a", "MOREINFO": "http://www.eplanning.ie/roscommoneplan/FileRefDetails.aspx?file_number=151", "WGS_LONG": "-8.31141", "WGS_LAT": "53.89448"} }] }"""
//        val json
    }
}