package cn.org.awcp.unit.service;
//package org.szcloud.framework.unit.service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
//import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
//import org.apache.shiro.web.filter.mgt.NamedFilterList;
//import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
//import org.apache.shiro.web.servlet.AbstractShiroFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.szcloud.framework.core.utils.Springfactory;
//import org.szcloud.framework.unit.vo.PunUrlFilterVO;
//
//
//@Service
//@Transactional
//public class ShiroFilterChainManagerImpl implements ShiroFilterChainManager{
//	
//	@Autowired
//    private ShiroFilterFactoryBean shiroFilterFactoryBean;
//	
//	/**
//	 * 获取配置文件的静态filter chain，需考虑线程安全		
//	 * @author wsh
//	 * @return
//	 */
//    public synchronized DefaultFilterChainManager getFilterChainManager() {
//        
//        AbstractShiroFilter shiroFilter = null;
//        try{
//            //shiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();
//        	shiroFilter = Springfactory.getBean("shiroFilter");
//        } catch(Exception e) {
//            throw new RuntimeException("get ShiroFilter from ShiroFilter ID error!");
//        }
//        
//        PathMatchingFilterChainResolver filterChainResolver =(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
//        DefaultFilterChainManager defaultFilterChainManager =(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
//        return defaultFilterChainManager;
//    } 
//    
//    private DefaultFilterChainManager filterChainManager = getFilterChainManager();
//	private Map<String, NamedFilterList> defaultFilterChains;
//
//	/**
//	 * 加载servlet后，PostConstruct在构造函数之后，init()方法之前
//	 * 获取defaultFilterChains
//	 * @author wsh
//	 */
//    @PostConstruct
//    public void init() {
//        defaultFilterChains = new HashMap<String, NamedFilterList>(filterChainManager.getFilterChains());
//    }
//
//    /**
//     * 初始化过滤器链
//     */
//    @Override
//    public void initFilterChains(List<PunUrlFilterVO> urlFilters) {
//        //1、首先删除以前老的filter chain并注册默认的
//        filterChainManager.getFilterChains().clear();
//        if(defaultFilterChains != null) {
//            filterChainManager.getFilterChains().putAll(defaultFilterChains);
//        }
//
//        //2、循环URL Filter 注册filter chain
//        for (PunUrlFilterVO urlFilter : urlFilters) {
//            String url = urlFilter.getUrl();
//            //注册roles filter
//            if (!StringUtils.isEmpty(urlFilter.getRoles())) {
//                filterChainManager.addToChain(url, "roles", urlFilter.getRoles());
//            }
//            //注册perms filter
//            if (!StringUtils.isEmpty(urlFilter.getPermissions())) {
//                filterChainManager.addToChain(url, "perms", urlFilter.getPermissions());
//            }
//        }
//
//
//    }
//
//}
