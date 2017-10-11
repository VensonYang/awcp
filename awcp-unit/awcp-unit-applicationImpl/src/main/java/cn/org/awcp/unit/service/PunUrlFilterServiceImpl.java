package cn.org.awcp.unit.service;
//package org.szcloud.framework.unit.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.szcloud.framework.unit.vo.PunUrlFilterVO;
//
//@Service
//@Transactional
//public class PunUrlFilterServiceImpl implements PunUrlFilterService{
//
//    @Autowired
//    @Qualifier("shiroFilterChainManagerImpl")
//    private ShiroFilterChainManager shiroFilerChainManager;
//
//    @Override
//    public PunUrlFilterVO createUrlFilter(PunUrlFilterVO urlFilter) {
//        initFilterChain();
//        return urlFilter;
//    }
//
//    @Override
//    public PunUrlFilterVO updateUrlFilter(PunUrlFilterVO urlFilter) {
//        initFilterChain();
//        return urlFilter;
//    }
//
//    @Override
//    public void deleteUrlFilter(Long urlFilterId) {
//        initFilterChain();
//    }
//
//    @Override
//    public PunUrlFilterVO findById(Long urlFilterId) {
//        return null;
//    }
//
//    @Override
//    public List<PunUrlFilterVO> findAll() {
//    	List<PunUrlFilterVO> urlFilterVOList = new ArrayList<PunUrlFilterVO>();
//    	PunUrlFilterVO urlFilterVO = new PunUrlFilterVO();
//    	urlFilterVO.setId(1L);
//    	urlFilterVO.setName("testUrl");
//    	urlFilterVO.setPermissions("user:exec");
//    	urlFilterVO.setRoles("admin");
//    	urlFilterVO.setUrl("/manageAdmin/list.jsp");
//    	urlFilterVOList.add(urlFilterVO);
//        return urlFilterVOList;
//    }
//
//    @PostConstruct
//    public void initFilterChain() {
//        shiroFilerChainManager.initFilterChains(findAll());
//    }
//}
