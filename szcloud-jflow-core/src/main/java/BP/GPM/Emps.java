package BP.GPM;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 操作员s
// </summary>
*/
public class Emps extends EntitiesNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Emp();
	}
	/** 
	 操作员s
	 
	*/
	public Emps()
	{
	}
	@Override
	public int RetrieveAll()
	{
		return super.RetrieveAll("Name");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 构造方法
}