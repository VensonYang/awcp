package BP.WF.Port;

import BP.DA.*;
import BP.En.*;
import BP.Port.WebUser;
import BP.Web.*;

/** 
部门集合
 
*/
public class Depts extends EntitiesNoName
{
	/** 
	 查询全部。
	 
	 @return 
	*/
	@Override
	public int RetrieveAll()
	{
		if (WebUser.getNo().equals("admin"))
		{
			return super.RetrieveAll();
		}

		QueryObject qo = new QueryObject(this);
		qo.AddWhere(DeptAttr.No, " = ", WebUser.getFK_Dept());
		qo.addOr();
		qo.AddWhere(DeptAttr.ParentNo, " = ", WebUser.getFK_Dept());
		return qo.DoQuery();
	}
	/** 
	 得到一个新实体
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Dept();
	}
	/** 
	 create ens
	 
	*/
	public Depts()
	{
	}
}