package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.Entities;
import BP.En.Entity;

/** 
 扩展s
 
*/
public class MapExts extends Entities
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 扩展s
	 
	*/
	public MapExts()
	{
	}
	
	public static ArrayList<MapExt> convertMapExts(Object obj) {
		return (ArrayList<MapExt>) obj;
	}
	/** 
	 扩展s
	 
	 @param fk_mapdata s
	*/
	public MapExts(String fk_mapdata)
	{
		this.Retrieve(MapExtAttr.FK_MapData, fk_mapdata, MapExtAttr.PRI);
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MapExt();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}