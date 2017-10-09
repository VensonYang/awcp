package BP.CN;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
洲地市

*/
public class ZDSs extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return (Entity)(new ZDS());
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 构造方法
	/** 
	 洲地市s
	 
	*/
	public ZDSs()
	{
	}

	public ZDSs(String sf)
	{
		this.Retrieve(ZDSAttr.FK_SF, sf);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
}
