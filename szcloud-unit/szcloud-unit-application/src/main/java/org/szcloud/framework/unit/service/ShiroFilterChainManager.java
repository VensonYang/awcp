package org.szcloud.framework.unit.service;

import java.util.List;

import org.szcloud.framework.unit.vo.PunUrlFilterVO;

public interface ShiroFilterChainManager {
	public void initFilterChains(List<PunUrlFilterVO> urlFilters);

}
