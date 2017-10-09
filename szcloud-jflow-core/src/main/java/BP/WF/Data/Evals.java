package BP.WF.Data;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.Port.*;
import BP.En.*;

/** 
 工作质量评价s BP.Port.FK.Evals
 
*/
public class Evals extends EntitiesMyPK
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Eval();
	}
	/** 
	 工作质量评价s
	 
	*/
	public Evals()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}