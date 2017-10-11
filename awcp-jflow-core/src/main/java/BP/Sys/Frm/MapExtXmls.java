package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/** 
 
 
*/
public class MapExtXmls extends XmlEns
{
	

	public static ArrayList<MapExtXml> convertMapExtXmls(Object obj) {
		return (ArrayList<MapExtXml>)obj;
	}
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 考核率的数据元素
	 
	*/
	public MapExtXmls()
	{
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
		return new MapExtXml();
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
		return "FieldExt";
	}
	@Override
	public Entities getRefEns()
	{
		return null; //new BP.ZF1.AdminTools();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}