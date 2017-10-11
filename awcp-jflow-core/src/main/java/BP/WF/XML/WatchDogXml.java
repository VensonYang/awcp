package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.XML.*;
import BP.Port.WebUser;
import BP.Sys.*;

/** 
 流程监控菜单
 
*/
public class WatchDogXml extends XmlEnNoName
{
	public final String getName()
	{
		return this.GetValStringByKey(WebUser.getSysLang());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 流程监控菜单
	 
	*/
	public WatchDogXml()
	{
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new WatchDogXmls();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}