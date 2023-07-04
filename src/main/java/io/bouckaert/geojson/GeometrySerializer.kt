package io.bouckaert.geojson

//object GeometrySerializer: WrappedSerializer<Geometry>(
//    object : JsonContentPolymorphicSerializer<Geometry>(Geometry::class) {
//        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Geometry> {
//            val type = element.jsonObject["type"] ?: throw UnsupportedOperationException()
//            if (type !is JsonPrimitive) throw UnsupportedOperationException()
//            return when (type.content.lowercase()) {
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