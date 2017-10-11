package BP.WF.Data;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
月份s

*/
public class GenerWorkFlowViewNYs extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 月份s
	 
	*/
	public GenerWorkFlowViewNYs()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new GenerWorkFlowViewNY();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	public int RetrieveAll()
	{
		String sql = "SELECT DISTINCT FK_NY, FK_NY FROM WF_GenerWorkFlow";

		return super.RetrieveAll();
	}
}
