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
import cn.org.awcp.unit.core.domain.PunPosition;
import cn.org.awcp.unit.core.domain.PunSystem;
import cn.org.awcp.unit.core.domain.SysDataSource;
import cn.org.awcp.unit.service.PunSystemService;
import cn.org.awcp.unit.vo.PunSystemVO;
import cn.org.awcp.unit.vo.SysDataSourceVO;

@Transactional
@Service("punSystemServiceImpl")
public class PunSystemServiceImpl implements PunSystemService {

	@Autowired
	private QueryChannelService queryChannel;

	/**
	 * 
	 * @Title: addOrUpdate @Description: 新增或修改 @author ljw @param @param
	 * vo @param @throws MRTException @return void @throws
	 */
	public void addOrUpdate(PunSystemVO vo) throws MRTException {
		PunSystem sys = BeanUtils.getNewInstance(vo, PunSystem.class);
		if (null != vo.getSysId()) {
			sys.setId(vo.getSysId());
		}
		sys.save();
		vo.setSysId(sys.getSysId());
	}

	/**
	 * 
	 * @Title: findById @Description: 根据ID查找 @author ljw @param @param
	 * id @param @return @param @throws MRTException @return PunSystemVO @throws
	 */
	public PunSystemVO findById(Long id) throws MRTException {
		PunSystem system = PunSystem.get(PunSystem.class, id);
		return BeanUtils.getNewInstance(system, PunSystemVO.class);
	}

	/**
	 * 
	 * @Title: findAll @Description: 查询全部记录 @author
	 * ljw @param @return @param @throws MRTException @return
	 * List<PunSystemVO> @throws
	 */
	public List<PunSystemVO> findAll() throws MRTException {
		List<PunSystem> result = PunSystem.findAll();
		List<PunSystemVO> resultVo = new ArrayList<PunSystemVO>();
		for (PunSystem mm : result) {
			resultVo.add(BeanUtils.getNewInstance(mm, PunSystemVO.class));
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
	 */
	public PageList<PunSystemVO> queryPagedResult(String queryStr, Map<String, Object> params, int currentPage,
			int pageSize, String sortString) {
		PageList<PunSystem> systems = queryChannel.queryPagedResult(PunSystem.class, queryStr, params, currentPage,
				pageSize, sortString);
		List<PunSystemVO> tmp = new ArrayList<PunSystemVO>();
		for (PunSystem system : systems) {
			tmp.add(BeanUtils.getNewInstance(system, PunSystemVO.class));
		}
		PageList<PunSystemVO> vos = new PageList<PunSystemVO>(tmp, systems.getPaginator());
		systems.clear();
		return vos;
	}

	/**
	 * 
	 * @Title: queryResult @Description: 查询 @author ljw @param @param
	 * queryStr @param @param params 查询条件 @param @return @return
	 * List<PunSystemVO> @throws
	 */
	public List<PunSystemVO> queryResult(String queryStr, Map<String, Object> params) {
		List<PunSystem> systems = queryChannel.queryResult(PunSystem.class, queryStr, params);
		List<PunSystemVO> vos = new ArrayList<PunSystemVO>();
		for (PunSystem system : systems) {
			vos.add(BeanUtils.getNewInstance(system, PunSystemVO.class));
		}
		systems.clear();
		return vos;
	}

	/**
	 * 
	 * @Title: delete @Description: 删除，根据ID @author ljw @param @param
	 * id @param @return @param @throws MRTException @return String @throws
	 */
	public String delete(Long id) throws MRTException {
		PunSystem sys = PunSystem.get(PunSystem.class, id);
		if (sys == null) {
			return null;
		}
		removeRelations(sys);
		sys.delete();
		return null;
	}

	/**
	 * 删除应用系统与其它表的关联关系； auth:huangqr
	 * 
	 * @param user
	 * @throws MRTException
	 */
	private void removeRelations(PunSystem system) throws MRTException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sysId", system.getSysId());
		queryChannel.excuteMethod(PunPosition.class, "removeGroupSys", params);
		queryChannel.excuteMethod(PunPosition.class, "removeRoleAccessByRole", params);
		queryChannel.excuteMethod(PunPosition.class, "removeRoleAccessByResource", params);
		queryChannel.excuteMethod(PunPosition.class, "removeRoleInfo", params);
		queryChannel.excuteMethod(PunPosition.class, "removeResource", params);
	}

	/**
	 * @Description 根据example模糊查询数据
	 */
	@Override
	public PageList<PunSystemVO> selectPagedByExample(BaseExample example, 
			int currentPage, int pageSize,String sortString) {
		PageList<PunSystem> list = queryChannel.selectPagedByExample(PunSystem.class, example, 
				currentPage, pageSize,sortString);
		PageList<PunSystemVO> vos = new PageList<PunSystemVO>(list.getPaginator());
		for (PunSystem dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, PunSystemVO.class));
		}
		list.clear();
		return vos;
	}

	@Override
	public List<SysDataSourceVO> getSystemDataSource(Long systemId) {
		BaseExample example = new BaseExample();
		example.createCriteria().andEqualTo("SYSTEM_ID", systemId);
		List<SysDataSourceVO> vos = new ArrayList<SysDataSourceVO>();
		List<SysDataSource> systemDataSource = queryChannel.selectPagedByExample(SysDataSource.class, example, 1,
				Integer.MAX_VALUE, null);
		if (systemDataSource != null && systemDataSource.size() > 0) {
			for (SysDataSource data : systemDataSource) {
				vos.add(BeanUtils.getNewInstance(data, SysDataSourceVO.class));
			}
		}
		return vos;
	}

}
