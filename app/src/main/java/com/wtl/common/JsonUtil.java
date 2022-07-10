//package com.wtl.common;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class JsonUtil {
//
//	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//	static {
//		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	}
//
//
//	public static String getJsonStr(Map<String,Object> map) throws JsonProcessingException{
//		final String jsonMessage = OBJECT_MAPPER.writeValueAsString(map);
//		return jsonMessage;
//	}
//
//
//	public static Map<String,Object> convertJsonToHashMap(String jsonMessage) throws JsonParseException, JsonMappingException, IOException {
//		@SuppressWarnings("unchecked")
//		Map<String,Object> map = OBJECT_MAPPER.readValue(jsonMessage, HashMap.class);
//		return map;
//	}
//}
