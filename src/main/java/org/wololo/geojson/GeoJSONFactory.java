package org.wololo.geojson;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeoJSONFactory {
	static final ObjectMapper mapper = new ObjectMapper();

	public static GeoJSON create(String json) {
		try {
			JsonNode node = mapper.readTree(json);
			String type = node.get("type").textValue();
			if (type.equals("FeatureCollection")) {
				return readFeatureCollection(node);
			} else if (type.equals("Feature")) {
				return readFeature(node);
			} else {
				return readGeometry(node, type);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static FeatureCollection readFeatureCollection(JsonNode node) throws JsonParseException, JsonMappingException, IOException {
		Feature[] features = mapper.readValue(node.get("features").traverse(), Feature[].class);
		return new FeatureCollection(features);
	}
	
	static Feature readFeature(JsonNode node) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		JsonNode geometryNode = node.get("geometry");
		Map<String, Object> properties = mapper.readValue(node.get("properties").traverse(), Map.class);
		String type = geometryNode.get("type").textValue();
		Geometry geometry = readGeometry(geometryNode, type);
		return new Feature(geometry, properties);
	}
	
	static Geometry readGeometry(JsonNode node, String type) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		Geometry geometry = (Geometry) mapper.readValue(node.traverse(),
				Class.forName("org.wololo.geojson." + type));
		return geometry;
	}
}