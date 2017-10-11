package BP.CN;

import BP.En.EntitiesNoName;
import BP.En.Entity;

public class SheQs extends EntitiesNoName{
	@Override
	public Entity getGetNewEntity()
	{
		return new SheQ();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 构造方法
	/** 
	 城市s
	 
	*/
	public SheQs()
	{
	}

	/** 
	 城市s
	 
	 @param sf 省份
	*/
	public SheQs(String sf)
	{
		this.Retrieve(SheQAttr.FK_JD, sf);
	}
}
