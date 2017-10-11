package cn.org.awcp.formdesigner.core.domain;

import java.util.Comparator;

import cn.org.awcp.core.domain.BaseEntity;

public class PageObject extends BaseEntity implements Comparator<PageObject> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标识
	 */
	protected String pageId;
	/**
	 * 版本
	 */
	protected String version;
	/**
	 * 名称，英文
	 */
	protected String name;
	/**
	 * 描述
	 */
	protected String description;
	/**
	 * 序号
	 */
	protected int order;
	/**
	 * 样式名称
	 */
	protected String css;
	/**
	 * 样式
	 */
	protected String style;
	/**
	 * 样式库id
	 */
	protected String styleId;
	/**
	 * 隐藏脚本
	 */
	protected String hiddenScript;
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	/**
	 * 禁用脚本
	 */
	protected String disabledScript;
	/**
	 * 只读脚本
	 */
	protected String readonlyScript;
	
	
	public String getPageId() {
		return pageId;
	}
	
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public String getCss() {
		return css;
	}
	
	public void setCss(String css) {
		this.css = css;
	}
	
	public String getStyle() {
		return style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getHiddenScript() {
		return hiddenScript;
	}
	
	public void setHiddenScript(String hiddenScript) {
		this.hiddenScript = hiddenScript;
	}
	
	public String getDisabledScript() {
		return disabledScript;
	}
	
	public void setDisabledScript(String disabledScript) {
		this.disabledScript = disabledScript;
	}
	
	public String getReadonlyScript() {
		return readonlyScript;
	}
	
	public void setReadonlyScript(String readonlyScript) {
		this.readonlyScript = readonlyScript;
	}
	
	@Override
	public int compare(PageObject o1, PageObject o2) {
		return o1.getOrder() - o2.getOrder();
	}
	
}
