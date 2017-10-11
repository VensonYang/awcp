package BP.WF.XML.MapDef;

import BP.DA.*;
import BP.En.*;
import BP.Port.WebUser;
import BP.Sys.*;
import BP.XML.*;

/** 
 映射菜单
 
*/
public class MapMenu extends XmlEn
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
	public final String getJS()
	{
		return this.GetValStringByKey("JS");
	}
	/** 
	 图片
	 
	*/
	public final String getImg()
	{
		return this.GetValStringByKey("Img");
	}
	/** 
	 说明
	 
	*/
	public final String getNote()
	{
		return this.GetValStringByKey("Note");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 节点扩展信息
	 
	*/
	public MapMenu()
	{
	}
	/** 
	 获取一个实例s
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new MapMenus();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}