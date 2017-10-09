package org.szcloud.framework.metadesigner.util;

import java.util.List;

import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;

public class CheckColumn {
	
	public boolean isPri(List<MetaModelItemsVO> ls){
		for(MetaModelItemsVO mmi:ls){
			if(mmi.getUsePrimaryKey()!=null&&mmi.getUsePrimaryKey()==1){
				return true;
			}
		}
		return false;
	}
	
}
