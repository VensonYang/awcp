package BP.GPM;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 岗位类型
 
*/
public class StationTypes extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 岗位类型s
	 
	*/
	public StationTypes()
	{
	}
	
	public static ArrayList<StationType> convertStationTypes(Object obj) {
		return (ArrayList<StationType>) obj;
	}
	
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new StationType();
	}
}