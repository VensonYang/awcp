package org.szcloud.framework.unit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.unit.core.domain.SysDataSource;
import org.szcloud.framework.unit.vo.SysDataSourceVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service("sysSourceRelationServiceImpl")
public class SysSourceRelationServiceImpl implements SysSourceRelationService {
	@Autowired
	private QueryChannelService queryChannel;

	@Override
	public SysDataSourceVO get(Long id) {
		SysDataSource system = SysDataSource.get(SysDataSource.class, id);
		return BeanUtils.getNewInstance(system, SysDataSourceVO.class);
	}

	@Override
	public List<SysDataSourceVO> findAll() {
		List<SysDataSource> list = SysDataSource.findAll(SysDataSource.class);
		List<SysDataSourceVO> ls = new ArrayList<SysDataSourceVO>();
		for (SysDataSource mm : list) {
			ls.add(BeanUtils.getNewInstance(mm, SysDataSourceVO.class));
		}
		return ls;
	}

	@Override
	public void remove(SysDataSourceVO vo) {
		try {
			SysDataSource mm = BeanUtils.getNewInstance(vo, SysDataSource.class);
			SysDataSource.getRepository().remove(mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SysDataSourceVO saveOrUpdate(SysDataSourceVO vo) {
		SysDataSource sys = BeanUtils.getNewInstance(vo, SysDataSource.class);
		sys.save();
		vo.setId(sys.getId());
		return vo;
	}

	public PageList<SysDataSourceVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize,String sortString) {
		PageList<SysDataSource> result = queryChannel.selectPagedByExample(SysDataSource.class, baseExample,
				currentPage, pageSize, sortString);
		List<SysDataSourceVO> resultVO = new ArrayList<SysDataSourceVO>();
		for (Object dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, SysDataSourceVO.class));
		}
		PageList<SysDataSourceVO> rv = new PageList<SysDataSourceVO>(resultVO, result.getPaginator());
		result.clear();
		return rv;
	}

}
