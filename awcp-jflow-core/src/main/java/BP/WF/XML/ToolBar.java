package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.XML.*;
import BP.Port.WebUser;
import BP.Sys.*;

/** 
 工具按钮
 
*/
public class ToolBar extends XmlEn
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final String getNo()
	{
		return this.GetValStringByKey("No");
	}
	public final String getName()
	{
		return this.GetValStringByKey(WebUser.getSysLang());
	}
	/** 
	 图片
	 
	*/
	public final String getImg()
	{
		return this.GetValStringByKey("Img");
	}
	public final String getTitle()
	{
		return this.GetValStringByKey("Title");
	}
	public final String getUrl()
	{
		 String url=this.GetValStringByKey("Url");
		 if (url.equals(""))
		 {
			 url = "javascript:" + this.GetValStringByKey("OnClick");
		 }
		 return url;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 节点扩展信息
	 
	*/
	public ToolBar()
	{
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new ToolBars();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}