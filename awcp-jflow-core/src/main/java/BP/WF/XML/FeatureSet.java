package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.XML.*;
import BP.Port.WebUser;
import BP.Sys.*;

/** 
 个性化设置
 
*/
public class FeatureSet extends XmlEnNoName
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
	public FeatureSet()
	{
	}
	public FeatureSet(String no)
	{

	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new FeatureSets();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}