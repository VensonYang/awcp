package cn.org.awcp.unit.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.unit.core.domain.PunOriganize;
import cn.org.awcp.unit.vo.PunOriganizeVO;

public interface PunOriganizeService {

	public List<PunOriganizeVO> findAll();

	public void remove(PunOriganizeVO vo);

	public void update(PunOriganizeVO vo);

	public void save(PunOriganizeVO vo);

	// 参数： 1.根据条件来分页查询 2.当前页 3.取的记录他条数 4.根据字段排序("name.asc")列子
	public PageList<PunOriganize> query(Map<String, Object> params, int currentPage, int pageSize, String sortString);

	public PunOriganizeVO get(Long id);

	public void addOrgUsers(long orgId, long[] userIds);

	public List<Map<String, Object>> getOrgUsers(long orgId);

}