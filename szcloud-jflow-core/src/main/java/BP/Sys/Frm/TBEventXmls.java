package BP.Sys.Frm;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/** 
 文本框事件s
 
*/
public class TBEventXmls extends XmlEns
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 文本框事件s
	 
	*/
	public TBEventXmls()
	{
	}
	/** 
	 文本框事件s
	 
	 @param dFor
	*/
	public TBEventXmls(String dFor)
	{
		this.Retrieve(TBEventXmlList.DFor, dFor);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 重写基类属性或方法。
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public XmlEn getGetNewEntity()
	{
		return new TBEventXml();
	}
	@Override
	public String getFile()
	{
		return SystemConfig.getPathOfXML() + "MapExt.xml";
	}
	/** 
	 物理表名
	 
	*/
	@Override
	public String getTableName()
	{
		return "TBEvent";
	}
	@Override
	public Entities getRefEns()
	{
		return null; //new BP.ZF1.AdminTools();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}