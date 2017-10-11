package cn.org.awcp.formdesigner.core.domain.design.context.component.text;

import cn.org.awcp.formdesigner.core.domain.design.context.component.SimpleComponent;
import cn.org.awcp.formdesigner.core.domain.design.context.component.TextFormat;

public abstract class TextComponent extends SimpleComponent{

	private static final long serialVersionUID = 3767107055552344353L;
	protected String value;
	protected TextFormat format;
	
	public String getValue(){
		return this.value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public TextFormat getFormat(){
		return this.format;
	}
	
	public void setFormat(TextFormat format){
		this.format = format;
	}
}
