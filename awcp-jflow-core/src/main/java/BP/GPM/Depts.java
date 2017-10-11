package BP.GPM;

import java.util.ArrayList;

import BP.En.Attr;
import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
得到集合
 
*/
public class Depts extends EntitiesNoName
{
	public static ArrayList<Dept> convertDepts(Object obj) {
		return (ArrayList<Dept>) obj;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 得到一个新实体
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Dept();
	}
	/** 
	 部门集合
	 
	*/
	public Depts()
	{

	}
	/** 
	 部门集合
	 
	 @param parentNo 父部门No
	*/
	public Depts(String parentNo)
	{
		this.Retrieve(DeptAttr.ParentNo, parentNo);
	}
}