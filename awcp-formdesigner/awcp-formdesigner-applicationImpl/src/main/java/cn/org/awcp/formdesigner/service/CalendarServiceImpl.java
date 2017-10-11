package cn.org.awcp.formdesigner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.formdesigner.application.service.CalendarService;
import cn.org.awcp.formdesigner.application.vo.CalendarVO;
import cn.org.awcp.formdesigner.core.domain.Calendar;

@Service(value = "calendarServiceImpl")
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	@Qualifier("queryChannel")
	private QueryChannelService queryChannel;
	
	@Override
	public String save(CalendarVO vo) {
		Calendar calendar = vo.getCalendarInstance();
		String id = calendar.save();
		vo.setId(id);
		return id;
	}

	@Override
	public boolean delete(CalendarVO vo) {
		try {
			Calendar calendar = vo.getCalendarInstance();
			calendar.remove();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CalendarVO findById(String id) {
		CalendarVO vo = new CalendarVO();
		vo.instanceByCalendar(Calendar.get(id));
		return vo ;
	}

	@Override
	public List<CalendarVO> findByUserId(Long userId) {
		BaseExample example = new BaseExample();
		example.createCriteria().andEqualTo("user_id", userId);
		List<Calendar> calendarList = queryChannel.selectPagedByExample(Calendar.class, example, 1, Integer.MAX_VALUE, "start");
		List<CalendarVO> voList = new ArrayList<CalendarVO>();
		for(Calendar c:calendarList){
			CalendarVO vo = new CalendarVO();
			vo.instanceByCalendar(c);
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public List<CalendarVO> findByIds(String[] ids) {
		List<CalendarVO> voList = new ArrayList<CalendarVO>();
		for(String id:ids){
			voList.add(this.findById(id));
		}
		return voList;
	}

	@Override
	public PageList<CalendarVO> queryPagedResult(Map<String, Object> params,
			int currentPage, int pageSize, String sortString) {
		PageList<Calendar> result = queryChannel.selectMapByPage(Calendar.class, "eqQueryList", params, 
				currentPage, pageSize, sortString);
		List<CalendarVO> tem = new ArrayList<CalendarVO>();
		for(Calendar calendar:result){
			CalendarVO vo = new CalendarVO();
			tem.add(vo.instanceByCalendar(calendar));
		}
		PageList<CalendarVO> resultVO = new PageList<CalendarVO>(tem,result.getPaginator());
		result.clear();
		return resultVO;
	}

	@Override 
	public PageList<CalendarVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize, String sortString) {
		PageList<Calendar> result = queryChannel.selectPagedByExample(Calendar.class, baseExample, 
				currentPage, pageSize, sortString);
		List<CalendarVO> tem = new ArrayList<CalendarVO>();
		for(Calendar calendar:result){
			CalendarVO vo = new CalendarVO();
			tem.add(vo.instanceByCalendar(calendar));
		}
		PageList<CalendarVO> resultVO = new PageList<CalendarVO>(tem,result.getPaginator());
		result.clear();
		return resultVO;
	}

	@Override
	public boolean delete(String[] ids) {
		try {
			for(String id:ids){
				Calendar calendar = Calendar.get(id);
				calendar.remove();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<CalendarVO> findByIds(List<String> ids) {
		List<CalendarVO> voList = new ArrayList<CalendarVO>();
		for(String id:ids){
			voList.add(this.findById(id));
		}
		return voList;
	}

	@Override
	public boolean delete(List<String> ids) {
		try {
			for(String id:ids){
				Calendar calendar = Calendar.get(id);
				calendar.remove();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
