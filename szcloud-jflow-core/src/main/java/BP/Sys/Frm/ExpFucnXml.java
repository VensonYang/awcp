package BP.Sys.Frm;

import BP.Port.WebUser;
import BP.XML.XmlEnNoName;
import BP.XML.XmlEns;

public class ExpFucnXml extends XmlEnNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final String getName()
	{
		return this.GetValStringByKey(WebUser.getSysLang());
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 节点扩展信息
	 
	*/
	public ExpFucnXml()
	{
	}
	public ExpFucnXml(String no)
	{

	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new ExpFucnXmls();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}