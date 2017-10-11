package cn.org.awcp.unit.service;

import java.util.List;

import cn.org.awcp.unit.vo.PunUrlFilterVO;

public interface ShiroFilterChainManager {
	public void initFilterChains(List<PunUrlFilterVO> urlFilters);

}
