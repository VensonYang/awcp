package BP.GPM;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 职务
 
*/
public class Dutys extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 职务s
	 
	*/
	public Dutys()
	{
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Duty();
	}
}