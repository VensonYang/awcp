package org.szcloud.framework.core.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.szcloud.framework.core.utils.jsontools.JSONArray;
import org.szcloud.framework.core.utils.jsontools.JSONObject;

public class ConverToJson {

	public ConverToJson() {
		super();
	}

	/**
	 * 将list转换成json数据
	 * 
	 * @param menuList
	 * @return
	 */
	public static String ConverListToJson(List<Object> menuList) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (Object ui : menuList) {
			jsonObject = new JSONObject(ui);
			jsonArray.put(jsonObject);
		}
		String json = jsonArray.toString();
		return json;
	}

	/**
	 * 将list转换成分页json数据
	 * 
	 * @param list
	 *            集合
	 * @param count
	 *            总记录的条数
	 * @return
	 */	
	public static String ConverListToPageJson(List<Object> list, int count) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (Object ui : list) {
			jsonObject = new JSONObject(ui);
			jsonArray.put(jsonObject);
		}
		String json = jsonArray.toString();
		String jsonString = "{\"success\":" + true + ",\"totalCount\":" + count + ",\"rows\":" + json + "}";
		return jsonString;
	}

	/**
	 * 将list转换成json数据
	 * 
	 * @param menuList
	 * @return
	 */
	public static String ConverTListToJson(List<Object> menuList) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (Object ui : menuList) {
			jsonObject = new JSONObject(ui);
			jsonArray.put(jsonObject);
		}
		String json = jsonArray.toString();
		return json;
	}

	/**
	 * 将list转换成分页json数据
	 * 
	 * @param list
	 *            集合
	 * @param count
	 *            总记录的条数
	 * @return
	 */
	public static String ConverExtListToPageJson(List<Object> list, int count) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (Object ui : list) {
			jsonObject = new JSONObject(ui);
			jsonArray.put(jsonObject);
		}
		String json = jsonArray.toString();
		String jsonString = "{\"success\":" + true + ",\"totalCount\":" + count + ",\"rows\":" + json + "}";
		return jsonString;
	}

	/**
	 * 将list中的map转换成分页json数据
	 * 
	 * @param list
	 *            集合
	 * @param count
	 *            总记录的条数
	 * @return
	 */
	public static String ConverToListPageJson(List<Map<String,String>> list, int count) {
		StringBuffer jsonBuffer = new StringBuffer();
		for(int i=0;i<list.size();i++){
			jsonBuffer.append("{");    
			String key = "";
			String value = "";
			Map<String, String> map=list.get(i);
			Set<String> keys = map.keySet();
			for(Iterator<String> it = keys.iterator();it.hasNext();){
				key =  String.valueOf(it.next());
				value = String.valueOf(map.get(key));
				jsonBuffer.append("\"" + key+"\":\""+value + "\"");
				if(it.hasNext()){
					jsonBuffer.append(",");
				}
			}
			jsonBuffer.append("},");
		}
		String jsonString = jsonBuffer.toString();
		jsonString = jsonString.substring(0, jsonString.lastIndexOf(","));
		jsonString = "{\"success\":" + true + ",\"totalCount\":" + count + ",\"rows\":[" + jsonString + "]}";
		return jsonString;
	}

	/**
	 * 将list中的map转换成分页json数据
	 * 
	 * @param list
	 *            集合
	 * @param count
	 *            总记录的条数
	 * @return
	 */
	public static String ConverToListMapObjectPageJson(List<Map<String,Object>> list, int count) {
		StringBuffer jsonBuffer = new StringBuffer();
		for(int i=0;i<list.size();i++){
			jsonBuffer.append("{");    
			String key = "";
			String value = "";
			Map<String, Object> map=list.get(i);
			Set<String> keys = map.keySet();
			for(Iterator<String> it = keys.iterator();it.hasNext();){
				key =  String.valueOf(it.next());
				value = String.valueOf(map.get(key));
				jsonBuffer.append("\"" + key+"\":\""+value + "\"");
				if(it.hasNext()){
					jsonBuffer.append(",");
				}
			}
			jsonBuffer.append("},");
		}
		String jsonString = jsonBuffer.toString();
		jsonString = jsonString.substring(0, jsonString.lastIndexOf(","));
		jsonString = "{\"success\":" + true + ",\"totalCount\":" + count + ",\"rows\":[" + jsonString+ "]}";
		return jsonString;
	}

	/**
	 * 将单个对象转换成json数据格式
	 * 
	 * @param object
	 * @return json数据
	 */
	public static String convertObjectToJson(Object object) {
		JSONObject jo = new JSONObject(object);
		String json = jo.toString();
		return json;
	}

	/**
	 * 将单个对象转换成json数据格式
	 * 
	 * @param object
	 * @return json数据
	 */
	public static String convertSingleObjectToJson(Object object) {
		JSONObject jo = new JSONObject(object);
		String json = "["+jo.toString()+"]";
		return json;
	}

	/**
	 * map对象转换成json数据
	 * @param map
	 * @return
	 */
	public static String toJson(Map<String,String> map,int count){
		Set<String> keys = map.keySet();
		String key = "";
		String value = "";
		String jsonString="";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");    
		for(Iterator<String> it = keys.iterator();it.hasNext();){
			key =  String.valueOf(it.next());
			value = String.valueOf(map.get(key));
			jsonBuffer.append("\"" + key+"\":\""+value + "\"");
			if(it.hasNext()){
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		jsonString = jsonBuffer.toString();
		jsonString = jsonString.substring(0, jsonString.lastIndexOf(","));
		jsonString = "{\"success\":" + true + ",\"totalCount\":" + count + ",\"rows\":[" +jsonString+ "}]}";
		return jsonString ;
	}

	/**
	 * 将list中的map转换成json数据
	 * 
	 * @param list 集合
	 * @return
	 */
	public static String ConverListMapToJson(List<Map<String,String>> list) {
		StringBuffer jsonBuffer = new StringBuffer();
		for(int i=0;i<list.size();i++){
			jsonBuffer.append("{");    
			String key = "";
			String value = "";
			Map<String, String> map=list.get(i);
			Set<String> keys = map.keySet();
			for(Iterator<String> it = keys.iterator();it.hasNext();){
				key =  String.valueOf(it.next());
				value = String.valueOf(map.get(key));
				jsonBuffer.append("\"" + key+"\":\""+value + "\"");
				if(it.hasNext()){
					jsonBuffer.append(",");
				}
			}
			jsonBuffer.append("},");
		}
		String jsonString = jsonBuffer.toString();
		jsonString = jsonString.substring(0, jsonString.lastIndexOf(","));
		jsonString = "{\"success\":" + true +",\"totalCount\":" + list.size() + ",\"rows\":[" + jsonString + "]}";
		return jsonString;
	}

	/**
	 * 将list中的map转换成json数据
	 * 
	 * @param list 集合
	 * @return
	 */
	public static String ConverListMapObjectToJson(List<Map<String,Object>> list) {
		StringBuffer jsonBuffer = new StringBuffer();
		for(int i=0;i<list.size();i++){
			jsonBuffer.append("{");    
			String key = "";
			String value = "";
			Map<String, Object> map=list.get(i);
			Set<String> keys = map.keySet();
			for(Iterator<String> it = keys.iterator();it.hasNext();){
				key =  String.valueOf(it.next());
				value = String.valueOf(map.get(key));
				jsonBuffer.append("\"" + key+"\":\""+value + "\"");
				if(it.hasNext()){
					jsonBuffer.append(",");
				}
			}
			jsonBuffer.append("},");
		}
		String jsonString = jsonBuffer.toString();
		jsonString = jsonString.substring(0, jsonString.lastIndexOf(","));
		jsonString = "{\"success\":" + true +",\"totalCount\":" + list.size() + ",\"rows\":[" + jsonString + "]}";
		return jsonString;
	}

}

