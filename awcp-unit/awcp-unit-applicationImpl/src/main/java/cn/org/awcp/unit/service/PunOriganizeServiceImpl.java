package cn.org.awcp.unit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.EntityRepository;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.unit.core.domain.PunOriganize;
import cn.org.awcp.unit.service.PunOriganizeService;
import cn.org.awcp.unit.vo.PunOriganizeVO;

@Transactional
@Service("punOriganizeServiceImpl")
public class PunOriganizeServiceImpl implements PunOriganizeService {

	@Resource(name = "queryChannel")
	private QueryChannelService queryChannel;

	@Override
	public List<PunOriganizeVO> findAll() {
		List<PunOriganize> list = PunOriganize.findAll(PunOriganize.class);
		List<PunOriganizeVO> ls = new ArrayList<PunOriganizeVO>();
		for (PunOriganize mm : list) {
			ls.add(BeanUtils.getNewInstance(mm, PunOriganizeVO.class));
		}
		return ls;
	}

	@Override
	public void remove(PunOriganizeVO vo) {
		PunOriganize mm = BeanUtils.getNewInstance(vo, PunOriganize.class);
		PunOriganize.getRepository().remove(mm);
	}

	@Override
	public void update(PunOriganizeVO vo) {
		PunOriganize mm = BeanUtils.getNewInstance(vo, PunOriganize.class);
		mm.setUpdateDate(new Date());
		PunOriganize.getRepository().save(mm);
	}

	@Override
	public void save(PunOriganizeVO vo) {
		PunOriganize mm = BeanUtils.getNewInstance(vo, PunOriganize.class);
		mm.setCreateDate(new Date());
		mm.setUpdateDate(new Date());
		PunOriganize.getRepository().save(mm);
	}

	@Override
	public PageList<PunOriganize> query(Map<String, Object> params, int currentPage, int pageSize, String sortString) {
		return queryChannel.queryPagedResult(PunOriganize.class, "queryList", params, currentPage, pageSize,
				sortString);
	}

	@Override
	public PunOriganizeVO get(Long id) {
		try {
			PunOriganize model = PunOriganize.get(PunOriganize.class, id);
			return BeanUtils.getNewInstance(model, PunOriganizeVO.class);
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}

	@Override
	public void addOrgUsers(long orgId, long[] userIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userIds", userIds);
		EntityRepository repository = PunOriganize.getRepository();
		repository.executeUpdate("deleteOrgUsers", params, PunOriganize.class);
		if(userIds!=null&&userIds.length>0){
			repository.executeUpdate("addOrgUsers", params, PunOriganize.class);
		}
	}

	@Override
	public List<Map<String, Object>> getOrgUsers(long orgId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		return queryChannel.queryPagedResult(PunOriganize.class, "getOrgUsers", params);
	}

}
