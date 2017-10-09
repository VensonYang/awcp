package org.szcloud.framework.formdesigner.core.domain.design.context.component;

import java.util.List;

@SuppressWarnings("rawtypes")
public class Layout implements Comparable{

	private String pageId;
	
	private String name;
	
	private Integer order;
	
	private Long dynamicPageId;
	
	private Integer top;
	
	private Integer left;
	
	private Integer layoutType;
	
	private Long proportion;
	
	private String parentId;

	private List<Layout> childLayouts;
	
	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(Integer layoutType) {
		this.layoutType = layoutType;
	}

	

	public Long getProportion() {
		return proportion;
	}

	public void setProportion(Long proportion) {
		this.proportion = proportion;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Layout> getChildLayouts() {
		return childLayouts;
	}

	public void setChildLayouts(List<Layout> childLayouts) {
		this.childLayouts = childLayouts;
	}
	
	@Override
	public int compareTo(Object o) {
		return this.order.compareTo(((Layout)o).getOrder());
	}
	
}
