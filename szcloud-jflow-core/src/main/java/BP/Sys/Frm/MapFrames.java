package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;

/** 
 框架s
 
*/
public class MapFrames extends EntitiesMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 框架s
	 
	*/
	public MapFrames()
	{
	}
	/** 
	 框架s
	 
	 @param fk_mapdata s
	*/
	public MapFrames(String fk_mapdata)
	{
		this.Retrieve(MapFrameAttr.FK_MapData, fk_mapdata, MapFrameAttr.GroupID);
	}
	public static ArrayList<MapFrame> convertMapFrames(Object obj) {
		return (ArrayList<MapFrame>) obj;
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MapFrame();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}