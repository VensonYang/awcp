package BP.WF.Port;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

 /** 
  岗位s
  
 */
public class Stations extends EntitiesNoName
{
	/** 
	 岗位
	 
	*/
	public Stations()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Station();
	}
	public static ArrayList<Station> convertStations(Object sts) {
		return (ArrayList<Station>)sts;
	}
}