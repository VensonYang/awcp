package BP.WF.XML;

import BP.DA.*;
import BP.En.*;
import BP.Sys.*;
import BP.XML.*;

/** 
 公文右边谓词
 
*/
public class GovWordRight extends XmlEnNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 公文右边谓词
	 
	*/
	public GovWordRight()
	{
	}
	/** 
	 公文右边谓词s
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new GovWordRights();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}