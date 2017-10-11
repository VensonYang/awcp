package cn.org.awcp.formdesigner.core.domain.design.context.component.valued;

public class Option {
	/**
	 * 真实值
	 */
	private String value;
	/**
	 * 显示值
	 */
	private String text;
	/**
	 * 是否默认选择此项
	 */
	private boolean def;
	/**
	 * 序号
	 */
	private int order;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isDef() {
		return def;
	}
	
	public void setDef(boolean def) {
		this.def = def;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
}
