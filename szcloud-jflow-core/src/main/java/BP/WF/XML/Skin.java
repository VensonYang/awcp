package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.XML.*;
import BP.Port.WebUser;
import BP.Sys.*;

/** 
 皮肤
 
*/
public class Skin extends XmlEnNoName
{
	public final String getName()
	{
		return this.GetValStringByKey(WebUser.getSysLang());
	}
	public final String getCSS()
	{
		return this.GetValStringByKey("CSS");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 节点扩展信息
	 
	*/
	public Skin()
	{
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new Skins();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}