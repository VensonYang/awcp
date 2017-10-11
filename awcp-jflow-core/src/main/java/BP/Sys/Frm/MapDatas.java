package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;

/** 
 映射基础s
 
*/
public class MapDatas extends EntitiesMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	
	public static ArrayList<MapData> convertMapDatas(Object obj) {
		return (ArrayList<MapData>) obj;
	}
	/** 
	 映射基础s
	 
	*/
	public MapDatas()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MapData();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}