package cn.org.awcp.formdesigner.application.vo;

public class ComponentVO {
		
	private Integer componentType;	//组件类型
	private String dynamicPageId;	// 所属页面
	private String tittle;		//组件的额外信息
	private String accessKey;	//激活元素的快捷键
	private String tabIndex;	//组件的tab键次序
	private String lang;		//使用的语言--使用语言代码
	private String dir;			//文字方向
	private String dataItemCode;//文字方向
	private String valueType;	//值类型
	private String formatString;//值格式
	private String defaultValueScript;	//默认值的值脚本
	private String formValidator;	//校验
	private String optionScript;	//多选控件的text选项；

	public Integer getComponentType() {
		return componentType;
	}

	public void setComponentType(Integer componentType) {
		this.componentType = componentType;
	}

	public String getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(String dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	/**
	 * @return the accessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * @param accessKey the accessKey to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the tabIndex
	 */
	public String getTabIndex() {
		return tabIndex;
	}

	/**
	 * @param tabIndex the tabIndex to set
	 */
	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	/**
	 * @return the dataItemCode
	 */
	public String getDataItemCode() {
		return dataItemCode;
	}

	/**
	 * @param dataItemCode the dataItemCode to set
	 */
	public void setDataItemCode(String dataItemCode) {
		this.dataItemCode = dataItemCode;
	}

	/**
	 * @return the valueType
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * @param valueType the valueType to set
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	/**
	 * @return the formatString
	 */
	public String getFormatString() {
		return formatString;
	}

	/**
	 * @param formatString the formatString to set
	 */
	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	/**
	 * @return the defaultValueScript
	 */
	public String getDefaultValueScript() {
		return defaultValueScript;
	}

	/**
	 * @param defaultValueScript the defaultValueScript to set
	 */
	public void setDefaultValueScript(String defaultValueScript) {
		this.defaultValueScript = defaultValueScript;
	}

	/**
	 * @return the formValidator
	 */
	public String getFormValidator() {
		return formValidator;
	}

	/**
	 * @param formValidator the formValidator to set
	 */
	public void setFormValidator(String formValidator) {
		this.formValidator = formValidator;
	}

	/**
	 * @return the optionScript
	 */
	public String getOptionScript() {
		return optionScript;
	}

	/**
	 * @param optionContent the optionContent to set
	 */
	public void setOptionScript(String optionContent) {
		this.optionScript = optionContent;
	}
	
}
