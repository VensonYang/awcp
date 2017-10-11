package cn.org.awcp.formdesigner.application.vo;

import java.util.Date;

import cn.org.awcp.formdesigner.core.domain.Calendar;

public class CalendarVO {
	private String id;
	private String title;	//日程事件标题
	private String desc;	//日程事件内容
	private String calendar;//日程事件重要程度
	private boolean allDay;	//是否是整天，0否，1是
	private Date start;		//事件开始时间
	private Date end;		//事件结束时间
	private Long userId;	//日程的用户
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getCalendar() {
		return calendar;
	}
	
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	
	public boolean getAllDay() {
		return allDay;
	}
	
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public CalendarVO instanceByCalendar(Calendar calendar){
		this.id = calendar.getId();
		this.title = calendar.getTitle();
		this.desc = calendar.getDescription();
		this.calendar = calendar.getCalendar();
		this.setAllDay(calendar.getAllDay());
		this.start = calendar.getStart();
		this.end = calendar.getEnd();
		this.userId = calendar.getUserId();
		return this;
	}
	
	public Calendar getCalendarInstance(){ 
		Calendar ca = new Calendar();
		ca.setId(this.getId());
		ca.setTitle(this.getTitle());
		ca.setDescription(this.getDesc());
		ca.setAllDay(this.getAllDay());
		ca.setCalendar(this.getCalendar());
		ca.setStart(this.getStart());
		ca.setEnd(this.getEnd());
		ca.setUserId(this.getUserId());
		return ca;		
	}
	
}
