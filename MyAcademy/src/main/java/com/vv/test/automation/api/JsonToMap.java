package com.vv.test.automation.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.*;

public class JsonToMap {
	private JSONObject OBJECT;
	/**
	 * @param object pass Json object.
	 */
	public JsonToMap(JSONObject object)
	{
		setJsonObject(object);
	}

	/**
	 * @return Map of String and Object where Object may be a JsonObject or Json Array.
	 */
	public Map<String, Object> getMapFromJson(){
		JSONObject json=getJsonObject();
		Map<String, Object> retMap = new HashMap<String, Object>();

		if(json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	private Map<String, Object> toMap(JSONObject object){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator<String> keysItr = object.keys();
			while(keysItr.hasNext()) {
				String key = keysItr.next();
				Object value = object.get(key);

				if(value instanceof JSONArray) {
					value = toList((JSONArray) value);
				}

				else if(value instanceof JSONObject) {
					value = toMap((JSONObject) value);
				}
				map.put(key, value);
			}
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	private List<Object> toList(JSONArray array){
		try {
			List<Object> list = new ArrayList<Object>();
			for(int i = 0; i < array.length(); i++) {
				Object value = array.get(i);
				if(value instanceof JSONArray) {
					value = toList((JSONArray) value);
				}

				else if(value instanceof JSONObject) {
					value = toMap((JSONObject) value);
				}
				list.add(value);
			}
			return list;
		} catch (Exception e) {			
			return null;
		}
	}

	/**
	 * @return returns Json object
	 */

	public JSONObject getJsonObject()
	{
		return this.OBJECT;
	}

	/**
	 * @return sets Json object
	 */

	public void setJsonObject(JSONObject object)
	{
		this.OBJECT=object;
	}

}