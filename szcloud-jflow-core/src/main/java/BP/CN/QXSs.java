package BP.CN;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
区县市

*/
public class QXSs extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new QXS();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
	
	public static ArrayList<QXS> convertQXSs(Object obj) {
		return (ArrayList<QXS>) obj;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 构造方法
	/** 
	 区县市s
	 
	*/
	public QXSs()
	{
	}

	/** 
	 区县市s
	 
	 @param sf 省份
	*/
	public QXSs(String sf)
	{
		this.Retrieve(QXSAttr.FK_SF, sf);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
}
