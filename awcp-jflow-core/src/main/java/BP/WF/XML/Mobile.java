package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.Sys.*;
import BP.XML.*;

/** 
 移动菜单
 
*/
public class Mobile extends XmlEn
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 编号
	 
	*/
	public final String getNo()
	{
		return this.GetValStringByKey("No");
	}
	/** 
	 名称
	 
	*/
	public final String getName()
	{
		return this.GetValStringByKey("Name");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 节点扩展信息
	 
	*/
	public Mobile()
	{
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new Mobiles();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}