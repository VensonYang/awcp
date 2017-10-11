package BP.Sys;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 实体集合
 
*/
public class EnCfgs extends EntitiesNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 配置信息
	 
	*/
	public EnCfgs()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new EnCfg();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}