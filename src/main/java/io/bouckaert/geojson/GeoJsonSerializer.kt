package io.bouckaert.geojson


//object GeoJsonSerializer: WrappedSerializer<GeoJSON>(
//    object : JsonContentPolymorphicSerializer<GeoJSON>(GeoJSON::class) {
//        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<GeoJSON> {
//            val type = element.jsonObject["type"] ?: throw UnsupportedOperationException()
//            if (type !is JsonPrimitive) throw UnsupportedOperationException()
//            return when (type.content.lowercase()) {
//                "feature" -> Feature.serializer()
//                "featurecollection" -> FeatureCollection.serializer()
//                "geometrycollection" -> GeometryCollection.serializer()
//                "linestring" -> LineString.serializer()
//                "multilinestring" -> MultiLineString.serializer()
//                "multipoint" -> MultiPoint.serializer()
//                "multipolygon" -> MultiPolygon.serializer()
//                "point" -> Point.serializer()
//                "polygon" -> Polygon.serializer()
//                else -> throw UnsupportedOperationException()
//            }
//        }
//    }
//)