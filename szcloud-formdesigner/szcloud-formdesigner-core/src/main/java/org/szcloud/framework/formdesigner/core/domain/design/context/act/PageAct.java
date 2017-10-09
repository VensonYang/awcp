package org.szcloud.framework.formdesigner.core.domain.design.context.act;

import java.util.HashMap;
import java.util.Map;

import org.szcloud.framework.formdesigner.core.domain.PageObject;

/**
 * 页面动作 和页面按钮不一样 所有页面动作调用同一个controller的同一个方法。document/excute.do
 * 提交时修改form的action后面的参数（在action后增加_actId为当前act的id），然后提交
 * 
 * 名字、图标、顺序等也需要设置
 *
 */
public class PageAct extends PageObject {

	private static final long serialVersionUID = 4845744389122582801L;
	private Integer actType;// 动作类型 PageObjectType 放入数据字典
	private String icon; // 图标
	/*
	 * 客户端执行脚本 示例： onclick(function(){ alert(1); var c =
	 * getClientComponent('componetName'); }); 解析为: $("#id").click(function(){
	 * alert(1); var c = $('#componetName_componetId');//需要解析还是提供固定的方法？ });
	 */
	private String clientScript;
	private String serverScript;// 按钮的执行脚本，上下文需要有Document，能够访问和操纵Document、Element以及对外的接口 在服务端解析执行
	private String paramScript;
	private boolean confirm; // 是否操作前提示
	private boolean chooseValidate; // 是否对选择项校验
	private String chooseScript;// 选择项校验脚本
	private String buttonGroup; // 按钮组
	private boolean hiddenStatus = false;
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
	private Map<String, String> extbute = new HashMap<String, String>();// 扩展按钮属性
	private Long systemId; // 系统ID
	private String color;
	private String enName;

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public String getButtonGroup() {
		return buttonGroup;
	}

	public void setButtonGroup(String buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public boolean isChooseValidate() {
		return chooseValidate;
	}

	public void setChooseValidate(boolean chooseValidate) {
		this.chooseValidate = chooseValidate;
	}

	public String getChooseScript() {
		return chooseScript;
	}

	public void setChooseScript(String chooseScript) {
		this.chooseScript = chooseScript;
	}

	@Override
	public String toString() {
		return "PageAct [actType=" + actType + ", icon=" + icon + ", clientScript=" + clientScript + ", serverScript="
				+ serverScript + ", paramScript=" + paramScript + ", confirm=" + confirm + ", chooseValidate="
				+ chooseValidate + ", chooseScript=" + chooseScript + ", buttonGroup=" + buttonGroup + ", hiddenStatus="
				+ hiddenStatus + ", contentType=" + contentType + ", content=" + content + ", tittle=" + tittle
				+ ", width=" + width + ", height=" + height + ", buttons=" + buttons + ", extbute=" + extbute
				+ ", systemId=" + systemId + ", color=" + color + "]";
	}

}
