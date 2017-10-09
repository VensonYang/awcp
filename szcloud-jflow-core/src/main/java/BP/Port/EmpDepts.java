package BP.Port;

import java.util.ArrayList;

import BP.En.EntitiesMM;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
人员部门对应s -集合 

*/
public class EmpDepts extends EntitiesMM
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<EmpDept> convertEmpDepts(Object obj) {
		return (ArrayList<EmpDept>) obj;
	}
	/** 
	 工作人员与部门集合
	 
	*/
	public EmpDepts()
	{
	}
	/** 
	 工作人员与部门集合
	 
	 @param FK_Emp FK_Emp
	*/
	public EmpDepts(String FK_Emp)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(EmpDeptAttr.FK_Emp, FK_Emp);
		qo.DoQuery();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 重写方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new EmpDept();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法
	/** 
	 部门对应的节点
	 
	 @param stationNo 部门编号
	 @return 节点s
	*/
	public final Emps GetHisEmps(String stationNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(EmpDeptAttr.FK_Dept, stationNo);
		qo.DoQuery();

		Emps ens = new Emps();
		for(Object en : this)
		{
			ens.AddEntity(new Emp(((EmpDept)en).getFK_Emp()));
		}
		return ens;
	}
	/** 
	 得到他的部门权限
	 
	 @param empid empid 
	 @return Depts 
	*/
	public final Depts GetHisDepts(String empid)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(EmpDeptAttr.FK_Emp, empid);
		qo.DoQuery();
		Depts ens = new Depts();
		for(Object en : this)
		{
			ens.AddEntity(new Dept(((EmpDept)en).getFK_Dept()));
		}
		return ens;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}