package cn.org.awcp.formdesigner.application.vo;

public class ArtDialogVO {
	/**
	 * 位置
	 */
	private String location;
	/**
	 * 高度
	 */
	private String height;
	/**
	 * 宽度
	 */
	private String width;
	/**
	 * 标题
	 */
	private String tittle;
	/**
	 */
	private String contentType;
	/**
	 * 提示内容
	 */
	private String confirmContent;
	/**
	 * 按钮设置
	 * 	1,1 = 确定按钮+取消按钮都要显示
	 */
	private String buttons;
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getConfirmContent() {
		return confirmContent;
	}

	public void setConfirmContent(String confirmContent) {
		this.confirmContent = confirmContent;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
}
