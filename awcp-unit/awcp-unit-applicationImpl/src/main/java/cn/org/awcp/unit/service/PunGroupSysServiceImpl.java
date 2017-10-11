package cn.org.awcp.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.unit.core.domain.PunGroupSys;
import cn.org.awcp.unit.service.PunGroupSysService;
import cn.org.awcp.unit.vo.PunGroupSysVO;

@Transactional
@Service
public class PunGroupSysServiceImpl implements PunGroupSysService{
	
	@Autowired
	private QueryChannelService queryChannel;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 
	 * @Title: addOrUpdate 
	 * @Description: 新增或修改
	 * @author ljw 
	 * @param @param vo
	 * @param @throws MRTException    
	 * @return void
	 * @throws
	 */
	public void addOrUpdate(PunGroupSysVO vo) throws MRTException{
		PunGroupSys sys = BeanUtils.getNewInstance(vo, PunGroupSys.class);
		if(null != vo.getSysId()){
			sys.setId(vo.getSysId());
		}
		sys.save();
	}
	
	/**
	 * 删除，根据系统ID和组Id
	 * @param sysId系统ID	
	 * @param groupId组ID
	 * @throws MRTException
	 */
	public void deleteBySysAndGroup(Long sysId,Long groupId) throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sysId", sysId);
		params.put("groupId", groupId);
		List<PunGroupSys> systems = queryChannel.queryResult(PunGroupSys.class, "eqQueryList", params);
		for(PunGroupSys sys: systems){
			sys.remove();
		}
	}
	
	/**
	 * 
	 * @Title: findById 
	 * @Description: 根据ID查找
	 * @author ljw 
	 * @param @param id
	 * @param @return
	 * @param @throws MRTException    
	 * @return PunGroupSysVO
	 * @throws
	 */
	public PunGroupSysVO findById(Long id) throws MRTException{
		PunGroupSys user = PunGroupSys.get(PunGroupSys.class, id);
		return BeanUtils.getNewInstance(user, PunGroupSysVO.class);
	}
	
	/**
	 * 
	 * @Title: findAll 
	 * @Description: 查询全部记录
	 * @author ljw 
	 * @param @return
	 * @param @throws MRTException    
	 * @return List<PunGroupSysVO>
	 * @throws
	 */
	public List<PunGroupSysVO> findAll() throws MRTException{
		List<PunGroupSys> result = PunGroupSys.findAll();
		List<PunGroupSysVO> resultVo = new ArrayList<PunGroupSysVO>();
		for(PunGroupSys mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunGroupSysVO.class));
		}
		result.clear();
		return resultVo; 
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
	 * @return PageList<T>
	 * @throws
	 */
	public PageList<PunGroupSysVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,String sortString) {
		PageList<PunGroupSys> systems = queryChannel.queryPagedResult(PunGroupSys.class, queryStr, 
				params, currentPage, pageSize,sortString);
		List<PunGroupSysVO> tmp = new ArrayList<PunGroupSysVO>();
		for (PunGroupSys system : systems) {
			tmp.add(BeanUtils.getNewInstance(system, PunGroupSysVO.class));
		}
		PageList<PunGroupSysVO> vos = new PageList<PunGroupSysVO>(tmp,systems.getPaginator());
		systems.clear();
		return vos;
	}
	
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 查询
	 * @author ljw 
	 * @param queryStr
	 * @param params 查询条件
	 * @return List<PunGroupSysVO>
	 * @throws
	 */
	public List<PunGroupSysVO> queryResult(String queryStr,Map<String, Object> params){
		List<PunGroupSys> systems = queryChannel.queryResult(PunGroupSys.class, queryStr, params);
		List<PunGroupSysVO> vos = new ArrayList<PunGroupSysVO>();
		for (PunGroupSys system : systems) {
			vos.add(BeanUtils.getNewInstance(system, PunGroupSysVO.class));
		}
		systems.clear();
		return vos;
	}
	
	/**
	 * 
	 * @Title: delete 
	 * @Description: 删除，根据ID
	 * @author ljw 
	 * @param id
	 * @param @throws MRTException    
	 * @return String
	 * @throws
	 */
	public void delete(Long id) throws MRTException {
		PunGroupSys sys = PunGroupSys.get(PunGroupSys.class, id);
		sys.remove();
	}

	/**
	 * 批量删除，根组ID
	 */
	public void batchDeleteByGroupId(Long groupId) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.delete(PunGroupSys.class.getName()+".batchDeleteByGroupId", groupId);
			session.commit();
		} finally{
			session.close();
		}
	}

}
