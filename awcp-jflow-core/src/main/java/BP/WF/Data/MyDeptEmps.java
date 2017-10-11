package BP.WF.Data;

import BP.En.Entity;
import BP.En.QueryObject;
import BP.Port.WebUser;

/** 
报表集合

*/
public class MyDeptEmps extends BP.En.EntitiesNoName
{
	/** 
	 报表集合
	 
	*/
	public MyDeptEmps()
	{
	}

	@Override
	public Entity getGetNewEntity()
	{
		return new MyDeptEmp();
	}
	@Override
	public int RetrieveAll()
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(MyDeptEmpAttr.FK_Dept, WebUser.getFK_Dept());
		return qo.DoQuery();
	}
}