package es.jab.utils.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.jab.utils.json.JsonTransformer;

public class JsonTransformerGson implements JsonTransformer{
	
	private Gson gson;
	
	public JsonTransformerGson() {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
	}

	@Override
	public String toJson(Object data) {
		return gson.toJson(data);
	}

	@Override
	public Object fromJson(String json, Class clazz) {
		return gson.fromJson(json, clazz);
	}

}
