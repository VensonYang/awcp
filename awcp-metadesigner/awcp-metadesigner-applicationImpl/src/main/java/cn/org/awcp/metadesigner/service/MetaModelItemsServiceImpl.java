package cn.org.awcp.metadesigner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.metadesigner.application.MetaModelItemService;
import cn.org.awcp.metadesigner.application.MetaModelService;
import cn.org.awcp.metadesigner.core.domain.MetaModelItems;
import cn.org.awcp.metadesigner.vo.MetaModelItemsVO;

@Service(value="metaModelItemsServiceImpl")
public class MetaModelItemsServiceImpl implements MetaModelItemService{
	
	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	@Autowired
	private MetaModelService metaModelServiceImpl;
	
	public Long save(MetaModelItemsVO vo) {
		try {
			MetaModelItems modelItems = BeanUtils.getNewInstance(vo, MetaModelItems.class);
			modelItems.save();
			vo.setId(modelItems.getId());
			return modelItems.getId();
		} catch (Exception e) {
			return 0L;
		}
	}

	public boolean remove(MetaModelItemsVO vo) {
		try {
			MetaModelItems model = BeanUtils.getNewInstance(vo, MetaModelItems.class);
			model.remove();	
			return true;
		} catch (Exception e) {
			return false;
		}	
	}

	public <T extends MetaModelItemsVO> void remove(Set<T> entities) {
		try {
			for (MetaModelItemsVO entityVo : entities) {
				MetaModelItems model = BeanUtils.getNewInstance(entityVo, MetaModelItems.class);
				model.save();
			}
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}

	public MetaModelItemsVO get(Long id) {
		try {
			MetaModelItems model = MetaModelItems.get(MetaModelItems.class, id);
			return BeanUtils.getNewInstance(model, MetaModelItemsVO.class);
		} catch (Exception e) {
			throw new RuntimeException("错误提示", e);
		}
	}

	public PageList<MetaModelItemsVO> queryResult(String queryStr,Map<String, Object> params, int currentPage, int pageSize,
			String sortString) {
		PageList<MetaModelItems> pl = queryChannel.queryPagedResult(MetaModelItems.class,queryStr, params,
				currentPage,pageSize,sortString);
		PageList<MetaModelItemsVO> list = new PageList<MetaModelItemsVO>(pl.getPaginator());
		for(MetaModelItems m:pl){
			MetaModelItemsVO vo = BeanUtils.getNewInstance(m, MetaModelItemsVO.class);
			list.add(vo);
		}
		return list;
	}

	public List<MetaModelItemsVO> queryResult(String queryStr, long modelId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("modelId", modelId);
		List<MetaModelItems> vo = queryChannel.queryResult(MetaModelItems.class, queryStr, map);
		List<MetaModelItemsVO> list = new ArrayList<MetaModelItemsVO>();
		for(MetaModelItems mmi:vo){
			list.add((BeanUtils.getNewInstance(mmi,MetaModelItemsVO.class)));
		}
		return list;
	}

	public boolean update(String queryStr, MetaModelItemsVO vo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());
		map.put("modelId", vo.getModelId());
		map.put("itemCode", vo.getItemCode());
		map.put("itemLength", vo.getItemLength());
		map.put("itemName", vo.getItemName());
		map.put("itemType", vo.getItemType());
		map.put("itemValid", vo.getItemValid());
		map.put("remark", vo.getRemark());
		map.put("useNull", vo.getUseNull());
		map.put("useIndex", vo.getUseIndex());
		map.put("usePrimaryKey", vo.getUsePrimaryKey());
		map.put("defaultValue", vo.getDefaultValue());
		try {
			MetaModelItems.getRepository().executeUpdate(queryStr, map,MetaModelItems.class);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	public boolean columnIsExist(String queryStr, String tableName,String itemName) {
		String sql= "select " + itemName + " from " + tableName;
		return metaModelServiceImpl.excuteSql(sql);
	}

	public List<MetaModelItemsVO> queryByState(String queryStr, long modelId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("modelId", modelId);
		List<MetaModelItems> vo = queryChannel.queryResult(MetaModelItems.class, queryStr, map);
		List<MetaModelItemsVO> list = new ArrayList<MetaModelItemsVO>();
		for(MetaModelItems mmi:vo){
			list.add((BeanUtils.getNewInstance(mmi,MetaModelItemsVO.class)));
		}
		return list;
	}

	public List<MetaModelItemsVO> queryTableName(String queryStr,String tableName) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<MetaModelItems> vo = queryChannel.queryResult(MetaModelItems.class, queryStr, map);
		List<MetaModelItemsVO> list = new ArrayList<MetaModelItemsVO>();
		for(MetaModelItems mmi:vo){
			list.add((BeanUtils.getNewInstance(mmi,MetaModelItemsVO.class)));
		}
		return list;
	}
	
	public boolean removeByFk(Long modelId){
		try {
			MetaModelItems.getRepository().removeByFK(MetaModelItems.class, "removeByFK", modelId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<MetaModelItemsVO> queryColumn(String queryStr, long modelId,String itemCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("modelId", modelId);
		map.put("itemCode", itemCode);
		List<MetaModelItems> list = queryChannel.queryResult(MetaModelItems.class, queryStr, map);
		List<MetaModelItemsVO> ls = new ArrayList<MetaModelItemsVO>();
		for(MetaModelItems mmi:list){
			ls.add(BeanUtils.getNewInstance(mmi, MetaModelItemsVO.class));
		}
		return ls;
	}

	public PageList<MetaModelItemsVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize, String sortString) {
		PageList<MetaModelItems> list = this.queryChannel.selectPagedByExample(MetaModelItems.class, baseExample, 
				currentPage, pageSize, sortString);
		List<MetaModelItemsVO> ls = new ArrayList<MetaModelItemsVO>();
		for(MetaModelItems mmi:list){
			ls.add(BeanUtils.getNewInstance(mmi, MetaModelItemsVO.class));
		}
		PageList<MetaModelItemsVO> pl = new PageList<MetaModelItemsVO>(ls,list.getPaginator());;
		return pl;
	}

}
