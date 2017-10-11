package cn.org.awcp.metadesigner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.metadesigner.application.MetaModelChartService;
import cn.org.awcp.metadesigner.core.domain.MetaModelChart;
import cn.org.awcp.metadesigner.vo.MetaModelChartVO;

@Service(value="metaModelChartServiceImpl")
public class MetaModelChartServiceImpl implements MetaModelChartService{

	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	public long save(MetaModelChartVO vo) {
		try {
			MetaModelChart mmc = BeanUtils.getNewInstance(vo, MetaModelChart.class);
			mmc.save();
			vo.setId(mmc.getId());
			return mmc.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean delete(MetaModelChartVO vo) {
		try {
			MetaModelChart mmc = BeanUtils.getNewInstance(vo, MetaModelChart.class);
			mmc.remove();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean update(MetaModelChartVO vo) {
		try {
			MetaModelChart mmc = BeanUtils.getNewInstance(vo, MetaModelChart.class);
			mmc.save();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<MetaModelChartVO> findAll() {
		
		List<MetaModelChartVO> list = new ArrayList<MetaModelChartVO>();
		List<MetaModelChart> ls = MetaModelChart.findAll();
		for(MetaModelChart m:ls){
			list.add(BeanUtils.getNewInstance(m, MetaModelChartVO.class));
		}
		return list;
	}
	
	public List<MetaModelChartVO> queryByClassId(long projectId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("classId", projectId);
		List<MetaModelChart> ls = queryChannel.queryResult(MetaModelChart.class, "queryByClassId", map);
		List<MetaModelChartVO> list = new ArrayList<MetaModelChartVO>();
		for(MetaModelChart mm:ls){
			list.add(BeanUtils.getNewInstance(mm, MetaModelChartVO.class));
		}
		return list;
	}
	
}
