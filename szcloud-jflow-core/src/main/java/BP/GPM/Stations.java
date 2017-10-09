package BP.GPM;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

 /** 
  岗位s
  
 */
public class Stations extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 岗位
	 
	*/
	public Stations()
	{
	}
	
	public static ArrayList<Station> convertStations(Object obj) {
		return (ArrayList<Station>) obj;
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new BP.GPM.Station();
	}
}