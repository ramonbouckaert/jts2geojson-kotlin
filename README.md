This is a fork from [bjornharrtell/jts2geojson](https://github.com/bjornharrtell/jts2geojson) that ports the repo to Kotlin and uses [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) rather than jackson.

This fork also provides direct support for encoding and decoding Features and FeatureCollections, by storing feature IDs and Properties in the JTS userData. This allows for reading and writing all types in the GeoJSON spec to and from JTS objects.

This Kotlin library can convert JTS geometries to GeoJSON and back. Its API is similar to other io.* classes in JTS.

## Dependency
Maven:
```xml
<dependencies>
    <dependency>
        <groupId>io.bouckaert</groupId>
        <artifactId>jts2geojson-kotlin</artifactId>
        <version>0.20.0</version>
    </dependency>
</dependencies>

<profiles>
    <profile>
        <repositories>
            <repository>
                <id>github</id>
                <url>https://maven.pkg.github.com/ramonbouckaert/jts2geojson-kotlin</url>
            </repository>
        </repositories>
    </profile>
</profiles>

<servers>
    <server>
        <id>github</id>
        <username>USERNAME</username>
        <password>TOKEN</password>
    </server>
</servers>
```
Gradle KTS:
```kotlin
dependencies {
    implementation("io.bouckaert:jts2geojson-kotlin:0.20.0")
}

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/ramonbouckaert/jts2geojson-kotlin")
        credentials {
            username = project.findProperty("github.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("github.key") as String? ?: System.getenv("TOKEN")
        }
    }
}
```

## Usage

```kotlin
  val writer = GeoJSONWriter
  val json: GeoJSON = writer.write(geometry)
  val jsonstring: String = json.toString()

  val reader = GeoJSONReader
  val geometry: Geometry = reader.read(json)
```

## Features and FeatureCollections

JTS does not have anything like GeoJSON Feature or FeatureCollection. This library makes use of the JTS userData property to store GeoJSON Feature IDs and Properties, and delineate between Feature and Geometry.

A JTS Geometry object whose userData is an instance of JtsFeatureMetadata will be encoded as a GeoJSON Feature, rather than a GeoJSON Geometry. A JTS GeometryCollection which contains at least one Geometry whose userData is an instance of JtsFeatureMetadata will be encoded as a GeoJSON FeatureCollection, rather than a GeoJSON GeometryCollection.

```kotlin
  // parse Feature or FeatureCollection from String
  val feature: Feature = GeoJSON.create(json) as Feature
  val featureCollection: FeatureCollection = GeoJSON.create(json) as FeatureCollection

  // parse JTS Geometry from GeoJSON Feature
  val reader = GeoJSONReader
  var geometry: Geometry = reader.read(feature)
  val metadata = geometry.userData as JtsFeatureMetadata
  geometry = reader.read(featureCollection)

  // parse Feature or FeatureCollection from JTS Geometry with appropriate userData
  val writer = GeoJSONWriter
  geometry.userData = JtsFeatureMetadata("123", mapOf("a" to "1", "b" to "2"))
  val geometryCollection = jtsFactory.createGeometryCollection(arrayOf(geometry))
  val feature: GeoJSON = writer.write(geometry)
  val featureCollection: GeoJSON = writer.write(geometryCollection)
```
