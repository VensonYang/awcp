package org.szcloud.framework.formdesigner.core.parse.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.szcloud.framework.formdesigner.core.domain.design.context.data.DataDefine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class PageDataBeanWorker {

	/**
	 * 配置信息转化为bean
	 * 
	 * @param confContext
	 * @return
	 */
	public static DataDefine convertConfToDataDefine(String confContext) {
		DataDefine cd = JSON.parseObject(confContext, DataDefine.class);
		return cd;
	}

	/**
	 * 配置信息转化为bean
	 * 
	 * @param confContext
	 * @return
	 */
	public static Map<String, DataDefine> convertConfToDataDefines(String confContext) {
		LinkedHashMap<String, DataDefine> map = new LinkedHashMap<String, DataDefine>();
		if (StringUtils.isNotBlank(confContext)) {
			JSONArray array = JSON.parseArray(confContext);
			for (int i = 0; i < array.size(); i++) {
				DataDefine dd = convertConfToDataDefine(array.getString(i));
				if (dd != null) {
					map.put(dd.getName(), dd);
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		// DataDefine d = new DataDefine();
		// d.setName("sjkdjfk");
		// logger.debug(JSON.toJSONString(d));
		String s = "{\"isPage\":0,\"name\":\"sjkdjfk\"}";
		DataDefine d2 = JSON.parseObject(s, DataDefine.class);
		System.out.println(d2.getIsPage());

	}
}
