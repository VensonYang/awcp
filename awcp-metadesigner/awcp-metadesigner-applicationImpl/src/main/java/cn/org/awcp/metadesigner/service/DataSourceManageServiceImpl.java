package cn.org.awcp.metadesigner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.metadesigner.application.DataSourceManageService;
import cn.org.awcp.metadesigner.core.domain.DataSourceManage;
import cn.org.awcp.metadesigner.vo.DataSourceManageVO;

@Service(value="dataSourceManageServiceImpl")
public class DataSourceManageServiceImpl implements DataSourceManageService{

	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	public long save(DataSourceManageVO vo) {
		DataSourceManage dsm = BeanUtils.getNewInstance(vo, DataSourceManage.class);
		dsm.save();
		vo.setId(dsm.getId());
		return dsm.getId();
	}

	public boolean delete(DataSourceManageVO vo) {
		DataSourceManage dsm = BeanUtils.getNewInstance(vo, DataSourceManage.class);
		try {
			dsm.remove();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean update(DataSourceManageVO vo) {
		DataSourceManage dsm = BeanUtils.getNewInstance(vo, DataSourceManage.class);
		try {
			dsm.save();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<DataSourceManageVO> findAll() {
		List<DataSourceManage> ls = DataSourceManage.findAll(DataSourceManage.class);
		List<DataSourceManageVO> list = new ArrayList<DataSourceManageVO>();
		for(DataSourceManage d:ls){
			list.add(BeanUtils.getNewInstance(d, DataSourceManageVO.class));
		}		
		return list;
	}

	public DataSourceManageVO get(long id) {
		DataSourceManage dsm = DataSourceManage.get(DataSourceManage.class, id);
		DataSourceManageVO dsmv = BeanUtils.getNewInstance(dsm, DataSourceManageVO.class);
		return dsmv;
	}

	public DataSourceManageVO queryDataSourceByName(String name) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", name);
		DataSourceManage dsm = queryChannel.querySingleResult(DataSourceManage.class, "queryResult", map);
		if(dsm==null){
			return null;
		}else{
			DataSourceManageVO dsmv = BeanUtils.getNewInstance(dsm, DataSourceManageVO.class);
			return dsmv;
		}		
	}
	
	public PageList<DataSourceManageVO> selectPagedByExample(BaseExample baseExample, 
			int currentPage, int pageSize,String sortString) {
		PageList<DataSourceManage> result = queryChannel.selectPagedByExample(DataSourceManage.class,baseExample, 
				currentPage, pageSize, sortString);
		List<DataSourceManageVO> resultVO = new ArrayList<DataSourceManageVO>();
		for (Object dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, DataSourceManageVO.class));			
		}
		PageList<DataSourceManageVO> rv = new PageList<DataSourceManageVO>(resultVO,result.getPaginator());
		result.clear();
		return rv;
	}
	
	public DataSourceManageVO queryDataSourceByNameAndSystemId(String name,Long systemId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("systemId", systemId);
		DataSourceManage dsm = queryChannel.querySingleResult(DataSourceManage.class, "queryDataSourceByNameAndSystemId", map);
		if(dsm==null){
			return null;
		}else{
			DataSourceManageVO dsmv = BeanUtils.getNewInstance(dsm, DataSourceManageVO.class);
			return dsmv;
		}	
	}
	
}
