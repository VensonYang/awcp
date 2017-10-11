package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.Port.WebUser;
import BP.XML.*;

/** 
 数据类型
 
*/
public class SysDataType extends XmlEn
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
	public final String getDesc()
	{
		return this.GetValStringByKey("Desc");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 节点扩展信息
	 
	*/
	public SysDataType()
	{
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new SysDataTypes();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}