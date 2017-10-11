package cn.org.awcp.formdesigner.core.domain.design.context.component;

import java.util.Map;

public class SimpleComponent extends Component {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 所属页面Id
	 */
	protected Long dynamicPageId;
	
	/**
	 * 激活元素的快捷键
	 */
	protected String accessKey;
	/**
	 * 组件的tab键次序
	 */
	protected String tabIndex;
	
	/**
	 * 使用的语言--使用语言代码
	 */
	protected String lang;
	/**
	 * 文字方向
	 */
	protected String dir;
	/**
	 * 组件对应的数据字段
	 */
	protected String dataItemCode;
	
	/**
	 * 控件描述；
	 */
	protected String description;
	
	/**
	 * 布局组件id
	 */
	protected String layoutId;
	
	/**
	 * 其他属性 用于拓展
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map others; 
	
	public String getAccessKey() {
		return accessKey;
	}
	
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	public String getTabIndex() {
		return tabIndex;
	}
	
	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getDir() {
		return dir;
	}
	
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public String getDataItemCode() {
		return dataItemCode;
	}
	
	public void setDataItemCode(String dataItemCode) {
		this.dataItemCode = dataItemCode;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getLayoutId() {
		return layoutId;
	}
	
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}
	
	@SuppressWarnings("rawtypes")
	public Map getOthers() {
		return others;
	}
	
	@SuppressWarnings("rawtypes")
	public void setOthers(Map others) {
		this.others = others;
	}
	
	@Override
	public String getKeyString() {
		return null;
	}
	
	public Long getDynamicPageId() {
		return dynamicPageId;
	}
	
	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}
	
}
