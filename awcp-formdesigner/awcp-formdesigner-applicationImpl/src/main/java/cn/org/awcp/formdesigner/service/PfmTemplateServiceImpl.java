package cn.org.awcp.formdesigner.service;

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
import cn.org.awcp.formdesigner.application.service.PfmTemplateService;
import cn.org.awcp.formdesigner.application.vo.PfmTemplateVO;
import cn.org.awcp.formdesigner.core.domain.PfmTemplate;

@Service(value="pfmTemplateServiceImpl")
public class PfmTemplateServiceImpl implements PfmTemplateService{

	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	public List<PfmTemplateVO> findAll() {
		List<PfmTemplateVO> ls=new ArrayList<PfmTemplateVO>();
		List<PfmTemplate> list=PfmTemplate.findAll(PfmTemplate.class);
		for(PfmTemplate mm:list){
			ls.add(BeanUtils.getNewInstance(mm, PfmTemplateVO.class));
		}
		return ls;
	}

	public void remove(PfmTemplateVO vo) {
		try {
			PfmTemplate mm=BeanUtils.getNewInstance(vo, PfmTemplate.class);
			PfmTemplate.getRepository().remove(mm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(PfmTemplateVO vo,String queryStr) {
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id",vo.getId());
			map.put("fileName",vo.getFileName());
			map.put("sysId",vo.getSysId());
			map.put("description",vo.getDescription());
			map.put("fileLocation",vo.getFileLocation());
			map.put("content",vo.getContent());
			PfmTemplate.getRepository().executeUpdate(queryStr, map, PfmTemplate.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void save(PfmTemplateVO vo) {
		try {
			PfmTemplate mmc=BeanUtils.getNewInstance(vo, PfmTemplate.class);
			PfmTemplate.getRepository().save(mmc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PfmTemplateVO> queryPagedResult(Map<String, Object> params, int currentPage, int pageSize,String sortString) {
		PageList<PfmTemplate> result = queryChannel.queryPagedResult(PfmTemplate.class,"queryList",params, currentPage, pageSize, sortString);
		PageList<PfmTemplateVO> resultVO = new PageList<PfmTemplateVO>(result.getPaginator());
		for (PfmTemplate dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PfmTemplateVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public PfmTemplateVO get(Long id) {
		try {
			PfmTemplate model =  PfmTemplate.get(PfmTemplate.class, id);
			return BeanUtils.getNewInstance(model, PfmTemplateVO.class);
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}
	
	public List<PfmTemplateVO> queryResult(String queryStr,Map<String, Object> params)
	{
		List<PfmTemplate> result = queryChannel.queryResult(PfmTemplate.class,
				queryStr, params);
		List<PfmTemplateVO> resultVO = new ArrayList<PfmTemplateVO>();
		for (PfmTemplate dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PfmTemplateVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public PageList<PfmTemplateVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize,String sortString)
	{
		PageList<PfmTemplate> result = queryChannel.selectPagedByExample(PfmTemplate.class,
				baseExample, currentPage, pageSize, sortString);
		PageList<PfmTemplateVO> resultVO = new PageList<PfmTemplateVO>(result.getPaginator());
		for (PfmTemplate dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PfmTemplateVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public List<PfmTemplateVO> selectByExample(BaseExample baseExample)
	{
		return null;
	}
	
	
}