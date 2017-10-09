package BP.Sys;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
纳税人集合 

*/
public class SysEnumMains extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<SysEnumMain> convertSysEnumMains(Object obj) {
		return (ArrayList<SysEnumMain>) obj;
	}
	/** 
	 SysEnumMains
	 
	*/
	public SysEnumMains()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new SysEnumMain();
	}
}