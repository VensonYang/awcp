package BP.WF.Entity;

import BP.DA.*;
import BP.En.*;
import BP.Sys.SystemConfig;
import BP.WF.Template.*;
import BP.WF.*;

/** 
 审核组件s
*/
public class FrmWorkChecks extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 审核组件s
	 
	*/
	public FrmWorkChecks()
	{
	}
	/** 
	 审核组件s
	 
	 @param fk_mapdata s
	*/
	public FrmWorkChecks(String fk_mapdata)
	{
		if (SystemConfig.getIsDebug())
		{
			this.Retrieve("No", fk_mapdata);
		}
		else
		{
			this.RetrieveFromCash("No", (Object)fk_mapdata);
		}
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmWorkCheck();
	}
}