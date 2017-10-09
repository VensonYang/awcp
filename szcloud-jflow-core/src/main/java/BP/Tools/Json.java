package BP.Tools;

import BP.DA.DataColumn;
import BP.DA.DataRowCollection;
import BP.DA.DataSet;
import BP.DA.DataTable;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Json {
	/**
	 * 对象转换为Json字符串
	 */
	public static String ToJson(Object jsonObject) {
		// String jsonString = "{";
		// Field[] propertyInfo = jsonObject.getClass().getFields();
		// for (int i = 0; i < propertyInfo.length; i++)
		// {
		// Object objectValue =
		// propertyInfo[i].GetGetMethod().invoke(jsonObject, null);
		// String value = "";
		// if (objectValue instanceof java.util.Date || objectValue instanceof
		// Guid || objectValue instanceof TimeSpan)
		// {
		// value = "'" + objectValue.toString() + "'";
		// }
		// else if (objectValue instanceof String)
		// {
		// value = "'" + ToJson(objectValue.toString()) + "'";
		// }
		// else if (objectValue instanceof Iterable)
		// {
		// value = ToJson((Iterable)objectValue);
		// }
		// else
		// {
		// value = ToJson(objectValue.toString());
		// }
		// jsonString += "\"" + ToJson(propertyInfo[i].getName()) + "\":" +
		// value + ",";
		// }
		JSONObject object = JSONObject.fromObject(jsonObject);
		return object.toString();
	}

	/**
	 * 对象集合转换Json
	 */
	@SuppressWarnings("rawtypes")
	public static String ToJson(Iterable array) {
		// String jsonString = "[";
		// for (Object item : array)
		// {
		// jsonString += Json.ToJson(item) + ",";
		// }
		// return Json.DeleteLast(jsonString) + "]";
		JSONArray jsonArray = JSONArray.fromObject(array);
		return jsonArray.toString();
	}

	/**
	 * 普通集合转换Json
	 */
	@SuppressWarnings("rawtypes")
	public static String ToArrayString(Iterable array) {
		// String jsonString = "[";
		// for (Object item : array)
		// {
		// jsonString = ToJson(item.toString()) + ",";
		// }
		// return Json.DeleteLast(jsonString) + "]";
		JSONArray jsonArray = JSONArray.fromObject(array);
		return jsonArray.toString();
	}

	/**
	 * 删除结尾字符
	 * 
	 * @param str
	 *            需要删除的字符
	 */
	private static String DeleteLast(String str) {
		if (str.length() > 1) {
			return str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * Datatable转换为Json
	 * 
	 * @param Datatable对象
	 */
	public static String ToJson(DataTable table) {
		String jsonString = "[";
		DataRowCollection drc = table.Rows;
		for (int i = 0; i < drc.size(); i++) {
			jsonString += "{";
			for (DataColumn column : table.Columns) {
				jsonString += "\"" + ToJson(column.ColumnName) + "\":";
				Object obj = drc.get(i).getValue(column.ColumnName);
				if (column.DataType == java.util.Date.class || column.DataType == String.class) {
					if (null != obj) {
						jsonString += "\"" + ToJson(obj.toString()) + "\",";
					} else {
						jsonString += "\"\",";
					}
				} else {
					if (null != obj) {
						jsonString += ToJson(obj.toString()) + ",";
					} else {
						jsonString += ",";
					}
				}
			}
			jsonString = DeleteLast(jsonString) + "},";
		}
		return DeleteLast(jsonString) + "]";
	}

	/**
	 * DataSet转换为Json
	 * 
	 * @param DataSet对象
	 */
	public static String ToJson(DataSet dataSet) {
		String jsonString = "{";
		for (DataTable table : dataSet.Tables) {
			jsonString += "\"" + ToJson(table.TableName) + "\":" + ToJson(table) + ",";
		}
		return jsonString = DeleteLast(jsonString) + "}";
	}

	/**
	 * String转换为Json
	 * 
	 * @param value
	 *            String对象
	 * @return Json字符串
	 */
	public static String ToJson(String value) {
		if (StringHelper.isNullOrEmpty(value)) {
			return "";
		}

		String temstr;
		temstr = value;
		temstr = temstr.replace("{", "｛").replace("}", "｝").replace(":", "：").replace(",", "，").replace("[", "【")
				.replace("]", "】").replace(";", "；").replace("\n", "<br/>").replace("\r", "");

		temstr = temstr.replace("\t", "   ");
		temstr = temstr.replace("'", "\'");
		temstr = temstr.replace("\\", "\\\\");
		temstr = temstr.replace("\"", "\"\"");
		return temstr;
	}
}