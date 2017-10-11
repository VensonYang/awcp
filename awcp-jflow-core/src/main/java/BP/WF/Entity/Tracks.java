package BP.WF.Entity;

import java.util.ArrayList;

import BP.En.Entity;
/** 
 轨迹集合
 
*/
public class Tracks extends BP.En.Entities
{
	public static ArrayList<Track> convertTracks(Object obj) {
		return (ArrayList<Track>) obj;
	}
	
	/** 
	 轨迹集合
	 
	*/
	public Tracks()
	{
	}

	@Override
	public Entity getGetNewEntity()
	{
		return new Track();
	}
}