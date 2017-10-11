package BP.WF.Entity;

import BP.En.*;


public class RememberMes extends Entities
{
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new RememberMe();
	}
	/** 
	 RememberMe
	 
	*/
	public RememberMes()
	{
	}
		///#endregion
}