package BP.Port;

import BP.En.EnType;
import BP.En.EntityMM;
import BP.En.Map;
import BP.En.UAC;

/** 
人员部门对应

*/
public class EmpDept extends EntityMM
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		SetValByKey(EmpDeptAttr.FK_Emp, value);
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
		SetValByKey(EmpDeptAttr.FK_Dept, value);
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
		map.setEnDesc("工作人员部门对应信息");
		map.setEnType(EnType.Dot2Dot); //实体类型，admin 系统管理员表，PowerAble 权限管理表,也是用户表,你要想把它加入权限管理里面请在这里设置。。

		 //   map.AddTBStringPK(EmpDeptAttr.FK_Emp, null, "Emp", false, false, 1, 15,1);
			//map.AddTBStringPK(EmpDeptAttr.FK_Dept, null, "Dept", false, false, 1, 15,1);
			//map.AddDDLEntitiesPK(EmpDeptAttr.FK_Emp,null,"操作员",new Emps(),true);

		map.AddTBStringPK(EmpDeptAttr.FK_Emp, null, "操作员", false, false, 1, 20, 1);
		map.AddDDLEntitiesPK(EmpDeptAttr.FK_Dept, null, "部门", new Depts(), true);


			//map.AddDDLEntitiesPK(EmpDeptAttr.FK_Emp,0, DataType.AppInt,"操作员",new 县局(),"OID","Name",true);
			//map.AddSearchAttr(EmpDeptAttr.FK_Emp);
			//map.AddSearchAttr(EmpDeptAttr.FK_Dept);

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