package BP.CN;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
城市

*/
public class Citys extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new City();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 构造方法
	/** 
	 城市s
	 
	*/
	public Citys()
	{
	}

	/** 
	 城市s
	 
	 @param sf 省份
	*/
	public Citys(String sf)
	{
		this.Retrieve(CityAttr.FK_SF, sf);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
}