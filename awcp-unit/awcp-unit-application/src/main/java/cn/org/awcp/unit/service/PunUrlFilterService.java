package cn.org.awcp.unit.service;

import java.util.List;

import cn.org.awcp.unit.vo.PunUrlFilterVO;

/**
 * 动态url增删改查
 * @author wsh
 *
 */
public interface PunUrlFilterService {
	
	public PunUrlFilterVO createUrlFilter(PunUrlFilterVO urlFilter);  
	
    public PunUrlFilterVO updateUrlFilter(PunUrlFilterVO urlFilter);  
    
    public void deleteUrlFilter(Long urlFilterId);  
    
    public PunUrlFilterVO findById(Long urlFilterId);  
    
    public List<PunUrlFilterVO> findAll();  

}
