package org.szcloud.framework.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.unit.core.domain.PunMenu;
import org.szcloud.framework.unit.core.domain.PunResource;
import org.szcloud.framework.unit.core.domain.PunRoleAccess;
import org.szcloud.framework.unit.utils.IdentifierUtils;
import org.szcloud.framework.unit.vo.PunMenuVO;
import org.szcloud.framework.unit.vo.PunResourceVO;
import org.szcloud.framework.unit.vo.PunRoleInfoVO;
import org.szcloud.framework.unit.vo.PunSystemVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
@Transactional
public class PunMenuServiceImpl implements PunMenuService{
	
	private final static int TYPE_OF_MENU = 1;
	
	@Autowired
	private QueryChannelService queryChannel;
	
	@Autowired
	@Qualifier("punResourceServiceImpl")
	private PunResourceService punResourceService;
	
	/**
	 * 
	 * @Title: addOrUpdate 
	 * @Description: 保存
	 * @author ljw 
	 * @param vo
	 * @param @throws MRTException    
	 * @return void
	 * @throws
	 */
	public void addOrUpdate(PunMenuVO vo) throws MRTException{
		PunMenu menu = BeanUtils.getNewInstance(vo, PunMenu.class);
		if(null != vo.getMenuId()){
			menu.setId(vo.getMenuId());
		}
		menu.save();
		vo.setMenuId(menu.getMenuId());		
		List<Long> ids = new ArrayList<Long>();
		ids.add(vo.getMenuId());
		List<PunResourceVO> resourceList = punResourceService.getResourceListByRelateIds(ids, TYPE_OF_MENU);
		if(!resourceList.isEmpty()){
			return;//已存在，不必增加resource.
		}
		//保持冗余的resource数据表；
		PunResourceVO resource = new PunResourceVO();
		resource.setSysId(vo.getSysId());
		resource.setResouType(String.valueOf(TYPE_OF_MENU));//菜单;
		resource.setRelateResoId(menu.getMenuId().toString());
		punResourceService.addOrUpdate(resource);
	}
	
	/**
	 * 
	 * @Title: findById 
	 * @Description: 根据ID查找
	 * @author ljw 
	 * @param id
	 * @param @throws MRTException    
	 * @return PunMenuVO
	 * @throws
	 */
	public PunMenuVO findById(Long id) throws MRTException{
		PunMenu user = PunMenu.get(PunMenu.class, id);
		return BeanUtils.getNewInstance(user, PunMenuVO.class);
	}
	
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 查询
	 * @author ljw 
	 * @param queryStr
	 * @param params 参数
	 * @param @throws MRTException    
	 * @return List<PunMenu>
	 * @throws
	 */
	public List<PunMenuVO> queryResult(String queryStr,Map<String, Object> params) throws MRTException {
		List<PunMenu> resources = queryChannel.queryResult(PunMenu.class, queryStr, params);
		List<PunMenuVO> vos = new ArrayList<PunMenuVO>();
		for (PunMenu resource : resources) {
			vos.add(BeanUtils.getNewInstance(resource, PunMenuVO.class));
		}
		resources.clear();
		return vos;
	}
 
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 分页查询
	 * @author ljw 
	 * @param queryStr
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @param @return    
	 * @return PageList<T>
	 * @throws
	 */
	public PageList<PunMenuVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		PageList<PunMenu> resources = queryChannel.queryPagedResult(PunMenu.class, queryStr, params, 
				currentPage, pageSize,sortString);
		List<PunMenuVO> tmp = new ArrayList<PunMenuVO>();
		for (PunMenu resource : resources) {
			tmp.add(BeanUtils.getNewInstance(resource, PunMenuVO.class));
		}
		PageList<PunMenuVO> vos = new PageList<PunMenuVO>(tmp,resources.getPaginator());
		resources.clear();
		return vos;
	}
	
	/**
	 * 
	 * @Title: findAll 
	 * @Description: 查询全部
	 * @author ljw 
	 * @param @throws MRTException    
	 * @return List<PunMenuVO>
	 * @throws
	 */
	public List<PunMenuVO> findAll() throws MRTException{
		List<PunMenu> result = PunMenu.findAll();
		List<PunMenuVO> resultVo = new ArrayList<PunMenuVO>();
		for(PunMenu mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunMenuVO.class));
		}
		result.clear();
		return resultVo; 
	}
 
	/**
	 * 
	 * @Title: delete 
	 * @Description: 删除
	 * @author ljw 
	 * @param id
	 * @param @throws MRTException    
	 * @return String
	 * @throws
	 */
	public String delete(Long id) throws MRTException{
		PunMenu resource = PunMenu.get(PunMenu.class, id);
		resource.remove();
		removeRelations(resource);
		return null;
	}
	
	/**
	 * 删除菜单与其它表的关联关系；
	 * auth:huangqr
	 * @param menu
	 * @throws MRTException
	 */
	private void removeRelations(PunMenu menu) throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("relateResoId", menu.getMenuId());
		params.put("resourceType", TYPE_OF_MENU);
		queryChannel.excuteMethod(PunMenu.class, "removeRoleAccessByResource", params);
		queryChannel.excuteMethod(PunMenu.class, "removeResource", params);
		String pid = menu.getPid();
		if(pid == null){
			pid = "";
		}
		pid = pid + menu.getMenuId().toString() + ",";
		List<PunMenuVO> children = getChildrenPunMenuByPid(pid);
		//删除子结点；
		for(int i = 0; i < children.size(); i++){
			delete(children.get(i).getMenuId());
		}
	}

	public List<PunMenuVO> getPunMenuUserRoleAndSys(Long userId, Long roleId, Long sysId) throws MRTException{
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("roleId", roleId);
		params.put("sysId", sysId);
		List<PunMenuVO> vos = this.queryResult("getPunMenuUserRoleAndSys", params);
		return vos;
	}
	
	public List<PunMenuVO> getByRoleAndSys(PunRoleInfoVO vo, PunSystemVO svo) throws MRTException{
		Map<String,Object> params = new HashMap<String, Object>();
		if(vo != null){			
			if(vo.getRoleId() != null){
				params.put("roleId", vo.getRoleId());
			}
		}
		if(svo != null && svo.getSysId() != null){
			params.put("sysId", svo.getSysId());
		}
		List<PunMenuVO> vos = this.queryResult("getMenuListByRoleAndSys", params);
		return vos;
	}
	
	public List<PunMenuVO> getPunMenuVOByMenuIds(List<Long> menuIds) throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		String ids = IdentifierUtils.getLongIdStringForSql(menuIds);
		params.put("ids", ids);
		return this.queryResult("getMenuListByIds", params);
	}
	
	public List<PunMenuVO> selectByExample(BaseExample example) throws MRTException{
		List<PunMenu> result = PunMenu.selectByExample(PunMenu.class, example);
		List<PunMenuVO> resultVo = new ArrayList<PunMenuVO>();
		for(PunMenu mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunMenuVO.class));
		}
		result.clear();
		return resultVo;
		
	}
	
	/**
	 * 级联删除
	 * @param id
	 * @return
	 * @throws MRTException
	 */
	public String deleteCascade(Long id) throws MRTException{
		PunMenu  mToDelete= PunMenu.get(PunMenu.class, id);
		//删除子菜单及与子节点关联的资源和权限
		BaseExample example = new BaseExample();
		example.createCriteria().andLike("PID", mToDelete.getPid()+",%");
		List<PunMenu> menus = PunMenu.selectByExample(PunMenu.class, example);
		for (PunMenu menu : menus) {
			Map<String, Object> params = new HashMap<String, Object>();
			if (null != menu.getMenuId()) {
				params.put("relateResoId", menu.getMenuId());
				List<PunResource> ress = queryChannel.queryResult(
						PunResource.class, "eqQueryList", params);
				for (PunResource res : ress) {
					Map<String, Object> resParams = new HashMap<String, Object>();
					if (null != res.getResourceId()) {
						resParams.put("resourceId", res.getResourceId());
						List<PunRoleAccess> ras = queryChannel.queryResult(
								PunRoleAccess.class, "eqQueryList", resParams);
						for (PunRoleAccess ra : ras) {
							ra.remove();// 删除权限
						}
					}
					res.remove();// 删除资源
				}
			}
			menu.remove();// 删除菜单
		}
		String resName = null;
		mToDelete.remove();//删除该菜单
		return resName;
	}

	@Override
	public List<PunMenuVO> getPunMenuBySys(PunSystemVO svo) {
		Map<String,Object> params = new HashMap<String, Object>();
		if(svo != null && svo.getSysId() != null){
			params.put("sysId", svo.getSysId());
		}
		List<PunMenuVO> vos = this.queryResult("getMenuListBySystem", params);
		return vos;
	}

	@Override
	public List<PunMenuVO> getPunMenuByEnd(Map<String, Object> params) {
		List<PunMenuVO> vos = this.queryResult("getMenuListByEnd", params);
		return vos;
	}
	
	public List<PunMenuVO> getPosterityPunMenuByPid(String pid) {
		return getPosterityPunMenuByPid(pid, false);
	}
	
	public List<PunMenuVO> getChildrenPunMenuByPid(String pid) {
		return getPosterityPunMenuByPid(pid, true);
	}
	
	private List<PunMenuVO> getPosterityPunMenuByPid(String pid, boolean onlyChildren){
		Map<String,Object> params = new HashMap<String, Object>();
		if(!pid.endsWith(",")){
			pid = pid + ",";
		}
		if(!onlyChildren){
			pid = pid + "%";//包含子孙菜单；
		}
		params.put("pid", pid);
		return this.queryResult("queryByPID", params);
	}

	@Override
	public PageList<PunMenuVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize, String sortString) {
		PageList<PunMenu> list = queryChannel.selectPagedByExample(PunMenu.class, baseExample, currentPage, pageSize, sortString);
		PageList<PunMenuVO> rtn = new PageList<PunMenuVO>(list.getPaginator());
		for(PunMenu p : list){
			rtn.add(BeanUtils.getNewInstance(p, PunMenuVO.class));
		}
		list.clear();
		return rtn;
	}

}
