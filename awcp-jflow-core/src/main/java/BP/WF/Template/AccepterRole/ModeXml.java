package BP.WF.Template.AccepterRole;

import BP.DA.*;
import BP.En.*;
import BP.Sys.*;
import BP.XML.*;

/** 
 模式
 
*/
public class ModeXml extends XmlEnNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 设置描述
	 
	*/
	public final String getSetDesc()
	{
		return this.GetValStringByKey("SetDesc");
	}
	/** 
	 类别
	 
	*/
	public final String getFK_ModeSort()
	{
		return this.GetValStringByKey("FK_ModeSort");
	}
	/** 
	 类别
	 
	*/
	public final String getNote()
	{
		return this.GetValStringByKey("Note");
	}
	public final String getParaType()
	{
		return this.GetValStringByKey("ParaType");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 模式
	 
	*/
	public ModeXml()
	{
	}
	/** 
	 模式
	 
	*/
	public ModeXml(String no)
	{
		super(no);
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new ModeXmls();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}