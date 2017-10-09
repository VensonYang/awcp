package BP.GPM;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;

/** 
 部门岗位人员对应 
 
*/
public class DeptEmpStations extends EntitiesMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 工作部门岗位人员对应
	 
	*/
	public DeptEmpStations()
	{
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<DeptEmpStation> convertDeptEmpStations(Object obj) {
		return (ArrayList<DeptEmpStation>) obj;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new DeptEmpStation();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}