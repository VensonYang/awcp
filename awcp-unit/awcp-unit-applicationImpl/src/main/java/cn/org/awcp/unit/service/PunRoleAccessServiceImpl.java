package cn.org.awcp.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.unit.core.domain.PunRoleAccess;
import cn.org.awcp.unit.service.PunMenuService;
import cn.org.awcp.unit.service.PunResourceService;
import cn.org.awcp.unit.service.PunRoleAccessService;
import cn.org.awcp.unit.vo.PunMenuVO;
import cn.org.awcp.unit.vo.PunResourceVO;
import cn.org.awcp.unit.vo.PunRoleAccessVO;

@Transactional
@Service
public class PunRoleAccessServiceImpl implements PunRoleAccessService{
	
	@Autowired
	private QueryChannelService queryChannel;
	

	@Autowired
	@Qualifier("punMenuServiceImpl")
	private PunMenuService punMenuService;
	

	@Autowired
	@Qualifier("punResourceServiceImpl")
	private PunResourceService punResourceService;
	
	public void addOrUpdateRole(PunRoleAccessVO vo) throws MRTException{
		PunRoleAccess role = BeanUtils.getNewInstance(vo, PunRoleAccess.class);
		if(null != vo.getRoleAccId()){
			role.setId(vo.getRoleAccId());
		}
		role.save();
	}
	
	public PunRoleAccessVO findById(Long id) throws MRTException{
		PunRoleAccess user = PunRoleAccess.get(PunRoleAccess.class, id);
		return BeanUtils.getNewInstance(user, PunRoleAccessVO.class);
	}
	
	public List<PunRoleAccessVO> findAll() throws MRTException{
		List<PunRoleAccess> result = PunRoleAccess.findAll();
		List<PunRoleAccessVO> resultVo = new ArrayList<PunRoleAccessVO>();
		for(PunRoleAccess mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunRoleAccessVO.class));
		}
		result.clear();
		return resultVo; 
	}
	
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 分页查询
	 * @author ljw 
	 * @param  queryStr
	 * @param  params
	 * @param  currentPage
	 * @param  pageSize
	 * @param  sortString
	 * @return PageList<T>
	 * @throws
	 */
	public PageList<PunRoleAccessVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		PageList<PunRoleAccess> roles = queryChannel.queryPagedResult(PunRoleAccess.class, queryStr, params, 
				currentPage, pageSize,sortString);
		List<PunRoleAccessVO> tmp = new ArrayList<PunRoleAccessVO>();
		for (PunRoleAccess role : roles) {
			tmp.add(BeanUtils.getNewInstance(role, PunRoleAccessVO.class));
		}
		PageList<PunRoleAccessVO> vos = new PageList<PunRoleAccessVO>(tmp,roles.getPaginator());
		roles.clear();
		return vos;
	}
 
	public String delete(Long id) throws MRTException{
		PunRoleAccess role = PunRoleAccess.get(PunRoleAccess.class, id);
		if(role != null){
			role.remove();
		}
		return null;
	}

	/**
	 * 批量保存
	 */
	public String batchSave(List<PunRoleAccessVO> list) throws MRTException{		
		int count = 0; 
		if(list != null && list.size()>0){
			for(int i = 0; i < list.size();i++){
				addOrUpdateRole(list.get(i));
				count++;
			}
		}
		return "完成"+ count +"条记录的操作";
	}

	public List<PunRoleAccessVO> queryResult(String queryStr,Map<String, Object> params) throws MRTException{
		List<PunRoleAccess> members = queryChannel.queryResult(PunRoleAccess.class, queryStr, params);
		List<PunRoleAccessVO> vos = new ArrayList<PunRoleAccessVO>();
		for(PunRoleAccess member : members){
			vos.add(BeanUtils.getNewInstance(member, PunRoleAccessVO.class));
		}
		members.clear();
		return vos;
	}
	
	public List<PunRoleAccessVO> queryResultByExample(PunRoleAccessVO example) throws MRTException{
		Map<String, Object> params = getParams(example);			
		return queryResult("eqQueryList", params);
	}
	
	private Map<String, Object> getParams(PunRoleAccessVO example){
		Map<String, Object> params = new HashMap<String, Object>();		
		if(example.getRoleId() != null && example.getRoleId() > 0){
			params.put("roleId", example.getRoleId());
		}
		if(example.getResourceId() != null && example.getResourceId() > 0){
			params.put("resourceId", example.getResourceId());
		}
		if(example.getRoleAccId() != null && example.getRoleAccId() > 0){
			params.put("roleAccId", example.getRoleAccId());
		}
		if(example.getOperType() != null && example.getOperType() > 0){
			params.put("operType", example.getOperType());
		}
		return params;
	}
	
	public void assignMenuAccessRight(Long roleId, Long menuId, Long operType){	
		List<Long> menuIds = new ArrayList<Long>();
		menuIds.add(menuId);
		PunMenuVO currentMenu = punMenuService.findById(menuId);
		if(currentMenu != null){
			String pid = currentMenu.getPid();
			if(pid == null || pid.isEmpty()){
				pid = currentMenu.getMenuId().toString() + ",";// currentMenu是根结点；
			}
			else{
				pid = pid + currentMenu.getMenuId().toString() + ",";
			}
			//处理子孙结点
			List<PunMenuVO> menuVos =punMenuService.getPosterityPunMenuByPid(pid);
			for(int i = 0 ; i < menuVos.size(); i++){
				menuIds.add(menuVos.get(i).getMenuId());
			}
			String[] predecessorIds = pid.split(",");
			for(int i = 0; i < predecessorIds.length; i++){
				if(predecessorIds[i].length() > 0){
					Long preId = Long.parseLong(predecessorIds[i]);
					if(preId.longValue() != menuId.longValue()){ //过滤掉当前结点；
						menuIds.add(preId);
					}
				}
			}
		}
		List<PunRoleAccessVO> roleAccessVos = new ArrayList<PunRoleAccessVO>();
		List<PunResourceVO> resourceVos = punResourceService.getResourceListByRelateIds(menuIds, 1);
		for(int i = 0; i < resourceVos.size(); i++){
			PunRoleAccessVO temp = new PunRoleAccessVO();
			temp.setResourceId(resourceVos.get(i).getResourceId());
			temp.setOperType(operType);
			temp.setRoleId(roleId);
			List<PunRoleAccessVO> existVO =  queryResultByExample(temp);
			if(existVO != null && existVO.size() > 0){
				temp.setRoleAccId(existVO.get(0).getRoleAccId());
			}
			roleAccessVos.add(temp);
		}
		batchSave(roleAccessVos);		
	}
	
	public List<PunMenuVO> getRoleAccessMenuVOByRoleId(Long roleId) throws MRTException{
		PunRoleAccessVO praVO = new PunRoleAccessVO();
		praVO.setRoleId(roleId);
		List<PunRoleAccessVO> praList = this.queryResultByExample(praVO);		
		List<Long> resourceIds = new ArrayList<Long>();
		for(int i = 0; i <praList.size(); i++){
			resourceIds.add(praList.get(i).getResourceId());
		}
		List<PunResourceVO> resourceList = punResourceService.getPunResourceByIds(resourceIds);
		List<Long> menuIds = new ArrayList<Long>();
		for(int i= 0; i < resourceList.size(); i++){
			menuIds.add(Long.parseLong(resourceList.get(i).getRelateResoId()));
		}		
		List<PunMenuVO> menuList = punMenuService.getPunMenuVOByMenuIds(menuIds);		
		return menuList;
	}
	
	public void removeMenuAccessRight(Long roleId, Long menuId){
		List<Long> menuIds = new ArrayList<Long>();
		menuIds.add(menuId);
		PunMenuVO currentMenu = punMenuService.findById(menuId);
		if(currentMenu != null){
			String pid = currentMenu.getPid();
			if(pid == null || pid.isEmpty()){
				pid = currentMenu.getMenuId().toString() + ",";// currentMenu是根结点；
			}
			else{
				pid = pid + currentMenu.getMenuId().toString() + ",";
			}
			//处理子孙结点
			List<PunMenuVO> menuVos =punMenuService.getPosterityPunMenuByPid(pid);
			for(int i = 0 ; i < menuVos.size(); i++){
				menuIds.add(menuVos.get(i).getMenuId());
			}
		}
		List<PunRoleAccessVO> roleAccessVos = new ArrayList<PunRoleAccessVO>();
		List<PunResourceVO> resourceVos = punResourceService.getResourceListByRelateIds(menuIds, 1);
		for(int i = 0; i < resourceVos.size(); i++){
			PunRoleAccessVO temp = new PunRoleAccessVO();
			temp.setResourceId(resourceVos.get(i).getResourceId());
			temp.setRoleId(roleId);
			List<PunRoleAccessVO> existVO =  queryResultByExample(temp);
			if(existVO != null && existVO.size() > 0){
				temp.setRoleAccId(existVO.get(0).getRoleAccId());
			}
			roleAccessVos.add(temp);
		}
		for(int i = 0; i < roleAccessVos.size(); i++){
			delete(roleAccessVos.get(i).getRoleAccId());
		}
	}

	@Override
	public List<PunRoleAccessVO> selectByExample(BaseExample baseExample) {
		List<PunRoleAccess> result = PunRoleAccess.selectByExample(PunRoleAccess.class, baseExample);
		List<PunRoleAccessVO> resultVo = new ArrayList<PunRoleAccessVO>();
		for(PunRoleAccess mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunRoleAccessVO.class));
		}
		result.clear();
		return resultVo;
	}

	@Override
	public void resoAuthorDel(Map<String, Object> params) {
		queryChannel.excuteMethod(PunRoleAccess.class, "delByRoleIdAndResoId", params);
	}

}
