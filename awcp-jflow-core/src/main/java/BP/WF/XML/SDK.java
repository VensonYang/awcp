package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.XML.*;
import BP.Port.WebUser;
import BP.Sys.*;

/** 
 sdk
 
*/
public class SDK extends XmlEn
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final String getNo()
	{
		return this.GetValStringByKey("DoWhat");
	}
	public final String getName()
	{
		return this.GetValStringByKey(WebUser.getSysLang());
	}
	public final String getUrl()
	{
		return this.GetValStringByKey("Url");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 节点扩展信息
	 
	*/
	public SDK()
	{
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new SDKs();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}