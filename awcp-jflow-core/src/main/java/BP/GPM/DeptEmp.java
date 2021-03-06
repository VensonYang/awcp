package BP.GPM;

import BP.En.EntityMyPK;
import BP.En.Map;
import BP.En.UAC;

/** 
 部门人员信息 的摘要说明。
 
*/
public class DeptEmp extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	/** 
	 人员
	 
	*/
	public final String getFK_Emp()
	{
		return this.GetValStringByKey(DeptEmpAttr.FK_Emp);
	}
	public final void setFK_Emp(String value)
	{
		SetValByKey(DeptEmpAttr.FK_Emp, value);
		this.setMyPK(this.getFK_Dept() + "_" + this.getFK_Emp());
	}
	/** 
	 部门
	 
	*/
	public final String getFK_Dept()
	{
		return this.GetValStringByKey(DeptEmpAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		SetValByKey(DeptEmpAttr.FK_Dept, value);
		this.setMyPK(this.getFK_Dept() + "_" + this.getFK_Emp());
	}
	public final String getFK_DutyT()
	{
		return this.GetValRefTextByKey(DeptEmpAttr.FK_Duty);
	}
	/** 
	职务
	 
	*/
	public final String getFK_Duty()
	{
		return this.GetValStringByKey(DeptEmpAttr.FK_Duty);
	}
	public final void setFK_Duty(String value)
	{
		SetValByKey(DeptEmpAttr.FK_Duty, value);
		this.setMyPK(this.getFK_Dept() + "_" + this.getFK_Duty() + "_" + this.getFK_Emp());
	}
	/** 
	 领导
	 
	*/
	public final String getLeader()
	{
		return this.GetValStringByKey(DeptEmpAttr.Leader);
	}
	public final void setLeader(String value)
	{
		SetValByKey(DeptEmpAttr.Leader, value);
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
	 工作部门人员信息
	  
	*/
	public DeptEmp()
	{
	}
	/** 
	 查询
	 
	 @param deptNo 部门编号
	 @param empNo 人员编号
	*/
	public DeptEmp(String deptNo, String empNo)
	{
		this.setFK_Dept(deptNo);
		this.setFK_Emp(empNo);
		this.setMyPK(this.getFK_Dept() + "_" + this.getFK_Emp());
		this.Retrieve();
	}
	/** 
	 重写基类方法
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("Port_DeptEmp");
		map.setEnDesc("部门人员信息");

		map.AddMyPK();
		map.AddTBString(DeptEmpAttr.FK_Emp, null, "操作员", false, false, 1, 50, 1);
		map.AddTBString(DeptEmpAttr.FK_Dept, null, "部门", false, false, 1, 50, 1);
		map.AddTBString(DeptEmpAttr.FK_Duty, null, "职务", false, false, 0, 50, 1);
		map.AddTBInt(DeptEmpAttr.DutyLevel, 0, "职务级别", false, false);

		map.AddTBString(DeptEmpAttr.Leader, null, "领导", false, false, 0, 50, 1);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 更新前做的事情
	 
	 @return 
	*/
	@Override
	protected boolean beforeUpdateInsertAction()
	{
		this.setMyPK(this.getFK_Dept() + "_" + this.getFK_Emp());
		return super.beforeUpdateInsertAction();
	}
}