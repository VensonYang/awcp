package org.szcloud.framework.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.core.utils.Springfactory;
import org.szcloud.framework.unit.core.domain.PunUserRole;
import org.szcloud.framework.unit.vo.PunRoleInfoVO;
import org.szcloud.framework.unit.vo.PunUserRoleVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service(value="punUserRoleServiceImpl")
@Transactional
public class PunUserRoleServiceImpl implements PunUserRoleService{

	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	@Override
	public void addOrUpdate(PunUserRoleVO vo) {
		PunUserRole userRole = BeanUtils.getNewInstance(vo, PunUserRole.class);
		if (vo.getUserRoleId() != null) {
			userRole.setId(vo.getUserRoleId());			
		}
		userRole.save();
	}

	public List<PunUserRoleVO> findAll() {
		List<PunUserRole> list=PunUserRole.findAll(PunUserRole.class);
		List<PunUserRoleVO> ls=new ArrayList<PunUserRoleVO>();
		for(PunUserRole mm:list){
			ls.add(BeanUtils.getNewInstance(mm, PunUserRoleVO.class));
		}
		return ls;
	}

	public void remove(PunUserRoleVO vo) {
		try {
			PunUserRole mm=BeanUtils.getNewInstance(vo, PunUserRole.class);
			PunUserRole.getRepository().remove(mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(PunUserRoleVO vo,String queryStr) {
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userRoleId",vo.getUserRoleId());
			map.put("userId",vo.getUserId());
			map.put("roleId",vo.getRoleId());
			PunUserRole.getRepository().executeUpdate(queryStr, map, PunUserRole.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void save(PunUserRoleVO vo) {
		try {
			PunUserRole mmc=BeanUtils.getNewInstance(vo, PunUserRole.class);
			PunUserRole.getRepository().save(mmc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PunUserRoleVO> queryPagedResult(Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		PageList<PunUserRoleVO> resultVO = new PageList<PunUserRoleVO>();
		PageList<PunUserRole> result = queryChannel.queryPagedResult(PunUserRole.class,"queryList",params, 
				currentPage, pageSize, sortString);
		for (Object dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PunUserRoleVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public PunUserRoleVO get(Long id) {
		try {
			PunUserRole model =  PunUserRole.get(PunUserRole.class, id);
			return BeanUtils.getNewInstance(model, PunUserRoleVO.class);
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}
	
	public List<PunUserRoleVO> queryResult(String queryStr,Map<String, Object> params){
		List<PunUserRole> result = queryChannel.queryResult(PunUserRole.class,queryStr, params);
		List<PunUserRoleVO> resultVO = new ArrayList<PunUserRoleVO>();
		for (PunUserRole dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PunUserRoleVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public PageList<PunUserRoleVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize,String sortString){
		PageList<PunUserRoleVO> resultVO = new PageList<PunUserRoleVO>();
		PageList<PunUserRole> result = queryChannel.selectPagedByExample(PunUserRole.class,
				baseExample, currentPage, pageSize, sortString);
		for (PunUserRole dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PunUserRoleVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public List<PunUserRoleVO> selectByExample(PunUserRoleVO vo){	
		String queryStr = "queryList";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", vo.getUserId());
		params.put("roleId", vo.getRoleId());
		return queryResult(queryStr, params);
	}

	@Override
	public boolean deleteByRoleIdAndUserId(Long roleId, Long[] boxs) throws MRTException {
		Map<String, Object> params = null;
		String method = "deleteByRoleIdAndUserId";
		for(Long userId : boxs){
			params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("userId", userId);
			queryChannel.excuteMethod(PunUserRole.class, method, params);
		}
		return true;
	}
	
	/**
	 * 
	 * 角色与人员之间关联
	 *
	 * @方法名称：excuteRoleAndUser()
	 * @作者：huangmin
	 * @创建日期：2015年8月4日 上午10:40:55
	 *
	 * @param roleId 角色编号
	 * @param userIds 用户编号
	 * @return
	 * boolean
	 *
	 * @修改记录（修改时间、作者、原因）：
	 */
	public boolean excuteRoleAndUser(String roleId,String userIds){
		boolean flag=false;
		if(roleId!=null&&userIds!=null){
			Long roleIdLong=Long.valueOf(roleId);
			//保存时，先进行删除
			String method="deleteByRoleId";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleId", roleIdLong);
			boolean delflag=queryChannel.excuteMethod(PunUserRole.class, method, params);
			if(delflag&&StringUtils.isNoneBlank(userIds)){
				String [] userIdArr=userIds.split(",");
				//插入数据
				if(userIdArr!=null&&userIdArr.length>0){
					for(String userIdString:userIdArr){
						Long userId=Long.valueOf(userIdString);
						PunUserRoleVO userRole = new PunUserRoleVO();
						userRole.setRoleId(roleIdLong);
						userRole.setUserId(userId);
						this.save(userRole);
					}
				}
				flag=true;
			}
		}
		return flag;
	}
	
	@Override
	public void deletebyUserIdAndRoleIds(Long userId,List<PunRoleInfoVO> roles) throws MRTException {
		JdbcTemplate template = Springfactory.getBean("jdbcTemplate");
		for (PunRoleInfoVO punRoleInfoVO : roles) {
			String sql = "delete from p_un_user_role where role_id="
					+ punRoleInfoVO.getRoleId() + " and user_id=" + userId;
			template.execute(sql);
		}
	}

}