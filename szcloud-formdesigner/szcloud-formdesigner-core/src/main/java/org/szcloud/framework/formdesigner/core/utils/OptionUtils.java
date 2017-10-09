package org.szcloud.framework.formdesigner.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.Option;

public class OptionUtils {
	
	/**
	 * 根据文本格式创建选项列表；
	 * @param text ：文本
	 * @param lineSpliter：行分隔符
	 * @param keyValueSpliter：键值分隔符
	 * @return
	 */
	public static List<Option> createOptionListByPlainText(String text, String lineSpliter, String keyValueSpliter){
		List<Option> optionList = new ArrayList<Option>();
		String[] lines = text.split(lineSpliter);
		String[] valuePairs = null;
		boolean hasDefaultValue = false;
		for(int i = 0; i < lines.length; i++){
			valuePairs = lines[i].split(keyValueSpliter);
			Option option = new Option();
			option.setValue(valuePairs[0]);
			if(valuePairs.length > 1){
				option.setText(valuePairs[1]);
			}
			if(valuePairs.length > 2 && !hasDefaultValue){
				option.setDef(true);
				hasDefaultValue = true; //只允许有一个默认选项；
			}
			option.setOrder(i);
			optionList.add(option);
		}
		return optionList;
		
		
	}

}
