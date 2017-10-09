package org.szcloud.framework.metadesigner.util;

import org.springframework.stereotype.Service;

@Service(value="checkIsInt")
public class CheckIsInt implements ICheckIsInt{
	public boolean isInt(String name){
		try {
			Integer.parseInt(name);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
