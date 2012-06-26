package com.tech.bll;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;
import com.tech.bll.models.JsonModel;

public class JsonService {
	public static String serialize(Object object) throws MapperException {
		JSONValue jvalue = JSONMapper.toJSON(object);
		return jvalue.render(false);
	}

	@SuppressWarnings("unchecked")
	public static Object deserialize(String json, Class type)
			throws TokenStreamException, RecognitionException, MapperException {
		JSONParser parser = new JSONParser(new StringReader(json));
		Object object = JSONMapper.toJava(parser.nextValue(), type);
		return object;
	}

	public static String serializeEx(Object object, String status, String error)
			throws MapperException {
		JsonModel model = new JsonModel();
		model.setErr(error);
		model.setStatus(status);
		model.setModel(object);
		JSONValue jvalue = JSONMapper.toJSON(model);
		String json = jvalue.render(false);
		return json;
	}
}
