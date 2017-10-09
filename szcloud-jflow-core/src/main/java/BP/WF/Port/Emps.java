package BP.WF.Port;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 工作人员
 
*/
public class Emps extends EntitiesNoName
{
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Emp();
	}
	/** 
	 工作人员s
	 
	*/
	public Emps()
	{
	}
	/** 
	 查询全部
	 
	 @return 
	*/
	@Override
	public int RetrieveAll()
	{
	   return super.RetrieveAll();

		//QueryObject qo = new QueryObject(this);
		//qo.AddWhere(EmpAttr.FK_Dept, " like ", BP.Web.WebUser.FK_Dept + "%");
		//qo.addOrderBy(EmpAttr.No);
		//return qo.DoQuery();
	}
	public static ArrayList<Emp> convertEmps(Object sts) {
		return (ArrayList<Emp>)sts;
	}
}