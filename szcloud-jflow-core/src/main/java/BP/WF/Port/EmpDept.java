package BP.WF.Port;

import BP.DA.*;
import BP.En.*;
import BP.Port.WebUser;

/** 
 操作员与工作部门 的摘要说明。
 
*/
public class EmpDept extends Entity
{
	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		if (WebUser.getNo().equals("admin"))
		{
			uac.IsView=true;
			uac.IsDelete=true;
			uac.IsInsert=true;
			uac.IsUpdate=true;
			uac.IsAdjunct=true;
		}
		return uac;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	/** 
	 工作人员ID
	 
	*/
	public final String getFK_Emp()
	{
		return this.GetValStringByKey(EmpDeptAttr.FK_Emp);
	}
	public final void setFK_Emp(String value)
	{
		SetValByKey(EmpDeptAttr.FK_Emp,value);
	}
	public final String getFK_DeptT()
	{
		return this.GetValRefTextByKey(EmpDeptAttr.FK_Dept);
	}
	/** 
	部门
	 
	*/
	public final String getFK_Dept()
	{
		return this.GetValStringByKey(EmpDeptAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		SetValByKey(EmpDeptAttr.FK_Dept,value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 扩展属性

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造函数
	/** 
	 工作人员岗位
	  
	*/
	public EmpDept()
	{
	}
	/** 
	 工作人员部门对应
	 
	 @param _empoid 工作人员ID
	 @param wsNo 部门编号 	
	*/
	public EmpDept(String _empoid, String wsNo)
	{
		this.setFK_Emp(_empoid);
		this.setFK_Dept(wsNo);
		if (this.Retrieve()==0)
		{
			this.Insert();
		}
	}
	/** 
	 重写基类方法
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap()!=null)
		{
			return this.get_enMap();
		}

		Map map = new Map("Port_EmpDept");
		map.setEnDesc("操作员与工作部门");
		map.setEnType(EnType.Dot2Dot);


		map.AddTBStringPK(EmpDeptAttr.FK_Emp, null, "操作员", false, false, 1, 15, 1);
			//map.AddDDLEntitiesPK(EmpDeptAttr.FK_Emp,null,"操作员",new Emps(),true);
		map.AddDDLEntitiesPK(EmpDeptAttr.FK_Dept,null,"部门",new Depts(),true);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 重载基类方法
	/** 
	 插入前所做的工作
	 
	 @return true/false
	*/
	@Override
	protected boolean beforeInsert()
	{
		return super.beforeInsert();
	}
	/** 
	 更新前所做的工作
	 
	 @return true/false
	*/
	@Override
	protected boolean beforeUpdate()
	{
		return super.beforeUpdate();
	}
	/** 
	 删除前所做的工作
	 
	 @return true/false
	*/
	@Override
	protected boolean beforeDelete()
	{
		return super.beforeDelete();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}