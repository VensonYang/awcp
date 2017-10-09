package BP.CN;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
城市编码

*/
public class Areas extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region  得到它的 Entity
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Area();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 城市编码s
	 
	*/
	public Areas()
	{
	}
	/** 
	 城市编码s
	 
	 @param sf 省份
	*/
	public Areas(String sf)
	{
		this.Retrieve(AreaAttr.FK_SF, sf);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
