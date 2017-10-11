package BP.GPM;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 部门类型
 
*/
public class DeptTypes extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 部门类型s
	 
	*/
	public DeptTypes()
	{
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new DeptType();
	}
}