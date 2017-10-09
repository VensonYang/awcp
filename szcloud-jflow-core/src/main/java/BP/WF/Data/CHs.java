package BP.WF.Data;

import BP.En.Entities;
import BP.En.Entity;

/** 
时效考核s

*/
public class CHs extends Entities
{

		///#region 构造方法属性
	/** 
	 时效考核s
	 
	*/
	public CHs()
	{
	}

	/** 
	 时效考核
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new CH();
	}

		///#endregion
}
