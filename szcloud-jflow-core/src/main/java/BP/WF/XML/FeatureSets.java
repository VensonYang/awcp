package BP.WF.XML;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.XML.*;
import BP.Sys.*;

/** 
 个性化设置s
 
*/
public class FeatureSets extends XmlEns
{
	public static ArrayList<FeatureSet> convertFeatureSets(Object obj) {
		return (ArrayList<FeatureSet>) obj;
	}
		///#region 构造
	/** 
	 考核率的数据元素
	 
	*/
	public FeatureSets()
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
		return new FeatureSet();
	}
	@Override
	public String getFile()
	{
		return SystemConfig.getPathOfXML() + "FeatureSet.xml";
	}
	/** 
	 物理表名
	 
	*/
	@Override
	public String getTableName()
	{
		return "Item";
	}
	@Override
	public Entities getRefEns()
	{
		return null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}