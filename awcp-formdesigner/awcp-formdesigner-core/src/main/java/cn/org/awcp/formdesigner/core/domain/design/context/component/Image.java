package cn.org.awcp.formdesigner.core.domain.design.context.component;

public class Image extends SimpleComponent{

	private static final long serialVersionUID = -6510161535453142050L;
	/**
	 * 图像url
	 */
	private String src;
	/**
	 * 图像的替代文本
	 */
	private String alt;
	/**
	 * 高度
	 */
	private String height;
	/**
	 * 宽度
	 */
	private String width;
	/**
	 * 指向包含长的图像描述文档的 URL
	 */
	private String longDesc;
	
	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	public String getAlt() {
		return alt;
	}
	
	public void setAlt(String alt) {
		this.alt = alt;
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
	
	public String getLongDesc() {
		return longDesc;
	}
	
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	
	@Override
	public String getKeyString() {
		return null;
	}
	
}
