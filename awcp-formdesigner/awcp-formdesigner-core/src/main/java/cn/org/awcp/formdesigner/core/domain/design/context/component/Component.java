package cn.org.awcp.formdesigner.core.domain.design.context.component;

import cn.org.awcp.formdesigner.core.domain.DynamicPage;
import cn.org.awcp.formdesigner.core.domain.PageObject;

public abstract class Component extends PageObject{
	private static final long serialVersionUID = 1L;

	/**
	 * 组件类型
	 * 	PageObjectType
	 */
	protected int componentType;
	
	/**
	 * 所属页面
	 */
	protected DynamicPage holder;

	public int getComponentType() {
		return componentType;
	}

	public void setComponentType(int componentType) {
		this.componentType = componentType;
	}

	public DynamicPage getHolder() {
		return holder;
	}

	public void setHolder(DynamicPage holder) {
		this.holder = holder;
	}
	
	/**
	 * 转化成该组件独有的html字符串，并以此进行html的识别，此处和PageComponentWorker.convert函数不一样
	 * 
	 * @return
	 */
	public abstract String getKeyString();
}
