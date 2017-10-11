package cn.org.awcp.formdesigner.application.vo;

import java.util.HashMap;
import java.util.Map;

public class PageActVO {
	private String pageId; // 动作id
	private Integer order; // 排序
	private String name; // 名称
	private Integer actType;// 动作类型
	private String icon; // 图标
	private String code; // 编码，表示类型
	private String description; // 描述
	private String clientScript;// 客户端脚本
	private String serverScript;// 服务器端脚本
	private String paramScript;
	private String chooseScript;// 选择项脚本
	private boolean hiddenStatus = false;
	private String hiddenScript;// 隐藏脚本
	private boolean confirm; // 是否操作前提示
	private boolean chooseValidate; // 是否对选择项进行校验
	private Long dynamicPageId; // 所属页面id
	private Integer buttonGroup;// 所属按钮组
	/*
	 * 提示内容类型 *1 = 自定义提示内容是文本，允许有html标签 *2 = 静态页面提示内容是完整的链接地址,如 http://www.baidu.com
	 * *3 = 动态页面提示内容是"文档的id,表单id",两者不可以都为空
	 */
	private Integer contentType;
	private String content; // 提示框内容
	private String tittle; // 提示框标题
	private Integer width; // 提示框宽度
	private Integer height; // 提示框高度
	private String buttons; // 提示框按钮 1,0= 确定按钮+取消按钮都要显示。
	private Map<String, String> extbute = new HashMap<String, String>(); // 扩展按钮属性
	private String authority; // 1--不允许增删流程节点 2--仅允许修改操作人 3--仅允许修改抄送人
	private String dynamicPageName;
	private String color;
	private String target;
	private Long systemId; // 系统Id
	private String enName;

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDynamicPageName() {
		return dynamicPageName;
	}

	public void setDynamicPageName(String dynamicPageName) {
		this.dynamicPageName = dynamicPageName;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public Integer getButtonGroup() {
		return buttonGroup;
	}

	public void setButtonGroup(Integer buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

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

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHiddenScript() {
		return hiddenScript;
	}

	public void setHiddenScript(String hiddenScript) {
		this.hiddenScript = hiddenScript;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public String getClientScript() {
		return clientScript;
	}

	public void setClientScript(String clientScript) {
		this.clientScript = clientScript;
	}

	public String getServerScript() {
		return serverScript;
	}

	public void setServerScript(String serverScript) {
		this.serverScript = serverScript;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public Map<String, String> getExtbute() {
		return extbute;
	}

	public void setExtbute(Map<String, String> extbute) {
		this.extbute = extbute;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getParamScript() {
		return paramScript;
	}

	public void setParamScript(String paramScript) {
		this.paramScript = paramScript;
	}

	public boolean isHiddenStatus() {
		return hiddenStatus;
	}

	public void setHiddenStatus(boolean hiddenStatus) {
		this.hiddenStatus = hiddenStatus;
	}

	public String getChooseScript() {
		return chooseScript;
	}

	public void setChooseScript(String chooseScript) {
		this.chooseScript = chooseScript;
	}

	public boolean isChooseValidate() {
		return chooseValidate;
	}

	public void setChooseValidate(boolean chooseValidate) {
		this.chooseValidate = chooseValidate;
	}

}
