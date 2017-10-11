package cn.org.awcp.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.unit.core.domain.PunRoleInfo;
import cn.org.awcp.unit.service.PunRoleInfoService;
import cn.org.awcp.unit.vo.PunRoleInfoVO;

@Transactional
@Service
public class PunRoleInfoServiceImpl implements PunRoleInfoService{
	
	@Autowired
	private QueryChannelService queryChannel;
	
	public void addOrUpdateRole(PunRoleInfoVO vo) throws MRTException{
		PunRoleInfo role = BeanUtils.getNewInstance(vo, PunRoleInfo.class);
		if(null != vo.getRoleId()){
			role.setId(vo.getRoleId());
		}
		role.save();
		vo.setRoleId(role.getRoleId());
	}
	
	public PunRoleInfoVO findById(Long id) throws MRTException{
		PunRoleInfo user = PunRoleInfo.get(PunRoleInfo.class, id);
		return BeanUtils.getNewInstance(user, PunRoleInfoVO.class);
	}
	
	public List<PunRoleInfoVO> findAll() throws MRTException{
		List<PunRoleInfo> result = PunRoleInfo.findAll();
		List<PunRoleInfoVO> resultVo = new ArrayList<PunRoleInfoVO>();
		for(PunRoleInfo mm : result)
		{
			resultVo.add(BeanUtils.getNewInstance(mm, PunRoleInfoVO.class));
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
	public PageList<PunRoleInfoVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		List<PunRoleInfoVO> tmp = new ArrayList<PunRoleInfoVO>();
		PageList<PunRoleInfo> roles = queryChannel.queryPagedResult(PunRoleInfo.class, queryStr, params, 
				currentPage, pageSize,sortString);
		for (PunRoleInfo role : roles) {
			tmp.add(BeanUtils.getNewInstance(role, PunRoleInfoVO.class));
		}
		PageList<PunRoleInfoVO> vos = new PageList<PunRoleInfoVO>(tmp,roles.getPaginator());
		roles.clear();
		return vos;
	}
 
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 查询
	 * @author ljw 
	 * @param  queryStr
	 * @param  params 参数
	 * @throws MRTException    
	 * @return List<PunRoleInfo>
	 * @throws
	 */
	public List<PunRoleInfoVO> queryResult(String queryStr,Map<String,Object> params) throws MRTException{
		  List<PunRoleInfo> members = queryChannel.queryResult(PunRoleInfo.class, queryStr, params);
		  List<PunRoleInfoVO> vos = new ArrayList<PunRoleInfoVO>();
		  for(PunRoleInfo member : members){
			  vos.add(BeanUtils.getNewInstance(member, PunRoleInfoVO.class));
		  }
		  members.clear();
		  return vos;
	}
	
	public String delete(Long id) throws MRTException{
		PunRoleInfo role = PunRoleInfo.get(PunRoleInfo.class, id);
		//删除User-Role mapping
		removeRelations(role);
		role.delete();		
		return null;
	}
	
	/**
	 * 删除角色与其它表的关联关系；
	 * auth:huangqr
	 * @param vo
	 * @throws MRTException
	 */
	private void removeRelations(PunRoleInfo role) throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", role.getRoleId());
		queryChannel.excuteMethod(PunRoleInfo.class, "removeUserRole", params);
		queryChannel.excuteMethod(PunRoleInfo.class, "removeRoleAccess", params);	
	}
	
	public List<PunRoleInfoVO> selectByExample(BaseExample example) throws MRTException{
		List<PunRoleInfo> result = PunRoleInfo.selectByExample(PunRoleInfo.class, example);
		List<PunRoleInfoVO> resultVo = new ArrayList<PunRoleInfoVO>();
		for(PunRoleInfo mm : result)
		{
			resultVo.add(BeanUtils.getNewInstance(mm, PunRoleInfoVO.class));
		}
		result.clear();
		return resultVo;
	}
	
	@Override
	public PageList<PunRoleInfoVO> selectPagedByExample(BaseExample example,
			int currentPage, int pageSize, String sortString) {
		PageList<PunRoleInfo> list = queryChannel.selectPagedByExample(PunRoleInfo.class, example, 
				currentPage, pageSize, sortString);
		PageList<PunRoleInfoVO> vos = new PageList<PunRoleInfoVO>(list.getPaginator());
		for (PunRoleInfo dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, PunRoleInfoVO.class));
		}
		list.clear();
		return vos;
	}

	@Override
	public List<String> queryByUser(Long userId) {
		String queryStr = "queryByUser";
		List<String> results = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<PunRoleInfo> roleInfos = queryChannel.queryResult(PunRoleInfo.class, queryStr, params);
		for (PunRoleInfo punRoleInfo : roleInfos) {
			results.add(punRoleInfo.getRoleId().toString());
		}
		return results;
	}

}
