This is a fork from [bjornharrtell/jts2geojson](https://github.com/bjornharrtell/jts2geojson) that ports the repo to Kotlin and uses [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) rather than jackson.

This Kotlin library can convert JTS geometries to GeoJSON and back. Its API is similar to other io.* classes in JTS.

## Usage

```kotlin
  val writer = GeoJSONWriter
  val json: GeoJSON = writer.write(geometry)
  val jsonstring: String = json.toString()

  val reader = GeoJSONReader
  val geometry: Geometry = reader.read(json)
```

## Features and FeatureCollections

JTS does not have anything like GeoJSON Feature or FeatureCollection but they can be parsed by this library. Example:

```kotlin
  // parse Feature or FeatureCollection
  val feature: Feature = GeoJSON.create(json) as Feature
  val featureCollection: FeatureCollection = GeoJSON.create(json) as FeatureCollection

  // parse Geometry from Feature
  val reader = GeoJSONReader
  var geometry: Geometry = reader.read(feature.getGeometry())
  geometry = reader.read(featureCollection.getFeatures().first().getGeometry())

  // create and serialize a FeatureCollection
  val features: MutableList<Features> = mutableListOf()
  val properties: MutableMap<String, Any> = mutableMapOf()
  features.add(Feature(geometry, properties))
  val writer = GeoJSONWriter
  val json: GeoJSON = writer.write(features)
```
