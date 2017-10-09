package BP.WF.XML;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.XML.*;

/** 
 数据源类型
 
*/
public class DBSrc extends XmlEnNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 数据源类型
	 
	*/
	public final String getSrcType()
	{
		return this.GetValStringByKey(DBSrcAttr.SrcType);
	}
	/** 
	 数据源类型URL
	 
	*/
	public final String getUrl()
	{
		return this.GetValStringByKey(DBSrcAttr.Url);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 数据源类型
	 
	*/
	public DBSrc()
	{
	}
	/** 
	 数据源类型s
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new DBSrcs();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}