package BP.Sys.XML;

import BP.Port.WebUser;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/** 
 EnumInfoXml 的摘要说明，属性的配置。
 
*/
public class EnumInfoXml extends XmlEn
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final String getKey()
	{
		return this.GetValStringByKey("Key");
	}
	/** 
	 Vals
	 
	*/
	public final String getVals()
	{
		return this.GetValStringByKey(WebUser.getSysLang());
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	public EnumInfoXml()
	{
	}
	public EnumInfoXml(String key)
	{
		this.RetrieveByPK("Key", key);
	}

	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new EnumInfoXmls();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}