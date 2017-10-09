package BP.WF.Port;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;

/** 
 Emp 的摘要说明。
 
*/
public class Emp extends EntityNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<Emp> convertEmps(Object obj) {
		return (ArrayList<Emp>) obj;
	}
	/** 
	 主要的部门。
	 
	*/
	public final Dept getHisDept()
	{

		try
		{
			return new Dept(this.getFK_Dept());
		}
		catch (RuntimeException ex)
		{
			throw new RuntimeException("@获取操作员" + this.getNo() + "部门[" + this.getFK_Dept() + "]出现错误,可能是系统管理员没有给他维护部门.@" + ex.getMessage());
		}
	}
	/** 
	 工作岗位集合。
	 
	*/
	public final Stations getHisStations()
	{
		EmpStations sts = new EmpStations();
		Stations mysts = sts.GetHisStations(this.getNo());
		return mysts;
			//return new Station(this.FK_Station);
	}
	/** 
	 部门
	 
	*/
	public final String getFK_Dept()
	{
		return this.GetValStrByKey(EmpAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		this.SetValByKey(EmpAttr.FK_Dept, value);
	}
	public final String getFK_DeptText()
	{
		return this.GetValRefTextByKey(EmpAttr.FK_Dept);
	}
	/** 
	 密码
	 
	*/
	public final String getPass()
	{
		return this.GetValStrByKey(EmpAttr.Pass);
	}
	public final void setPass(String value)
	{
		this.SetValByKey(EmpAttr.Pass, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final boolean CheckPass(String pass)
	{
		if (this.getPass().equals(pass))
		{
			return true;
		}
		return false;
	}
	/** 
	 工作人员
	 
	*/
	public Emp()
	{
	}
	/** 
	 工作人员编号
	 
	 @param _No No
	*/
	public Emp(String no)
	{
		this.setNo(no.trim());
		if (this.getNo().length() == 0)
		{
			throw new RuntimeException("@要查询的操作员编号为空。");
		}
		try
		{
			this.Retrieve();
		}
		catch (RuntimeException ex1)
		{
			int i = this.RetrieveFromDBSources();
			if (i == 0)
			{
				throw new RuntimeException("@用户或者密码错误：[" + no + "]，或者帐号被停用。@技术信息(从内存中查询出现错误)：ex1=" + ex1.getMessage());
			}
		}
	}
	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForAppAdmin();
		return uac;
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

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region 基本属性
		Map map = new Map();
		map.setEnDBUrl (new DBUrl(DBUrlType.AppCenterDSN)); //要连接的数据源（表示要连接到的那个系统数据库）。
		map.setPhysicsTable ("Port_Emp"); // 要物理表。
		map.setDepositaryOfMap (Depositary.Application); //实体map的存放位置.
		map.setDepositaryOfEntity (Depositary.Application); //实体存放位置
		map.setEnDesc("用户"); // "用户"; // 实体的描述.
		map.setEnType( EnType.App); //实体类型。
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region 字段
			//关于字段属性的增加 
		map.AddTBStringPK(EmpAttr.No, null, "编号", true, false, 1, 20, 100);
		map.AddTBString(EmpAttr.Name, null, "名称", true, false, 0, 100, 100);
		map.AddTBString(EmpAttr.Pass, "123", "密码", false, false, 0, 20, 10);
		map.AddDDLEntities(EmpAttr.FK_Dept, null, "部门", new BP.Port.Depts(), true);
		map.AddTBString(EmpAttr.SID, null, "SID", false, false, 0, 20, 10);

		  //  map.AddTBString(EmpAttr.FK_Unit, "1", "隶属单位", false, false, 0, 50, 10);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion 字段

		map.AddSearchAttr(EmpAttr.FK_Dept); //查询条件.

			//增加点对多属性 一个操作员的部门查询权限与岗位权限.
		map.getAttrsOfOneVSM().Add(new EmpStations(), new Stations(), EmpStationAttr.FK_Emp, EmpStationAttr.FK_Station, DeptAttr.Name, DeptAttr.No, "岗位权限");
		map.getAttrsOfOneVSM().Add(new EmpDepts(), new Depts(), EmpDeptAttr.FK_Emp, EmpDeptAttr.FK_Dept, DeptAttr.Name, DeptAttr.No, "工作部门");

		RefMethod rm = new RefMethod();
		rm.Title = "禁用";
		rm.Warning = "您确定要执行吗?";
		rm.ClassMethodName = this.toString() + ".DoDisableIt";
		map.AddRefMethod(rm);
		rm = new RefMethod();
		rm.Title = "启用";
		rm.Warning = "您确定要执行吗?";
		rm.ClassMethodName = this.toString() + ".DoEnableIt";
		map.AddRefMethod(rm);
		this.set_enMap (map);
		return this.get_enMap();
	}
	/** 
	 执行禁用
	 
	*/
	public final String DoDisableIt()
	{
		WFEmp emp = new WFEmp(this.getNo());
		emp.setUseSta(0);
		emp.Update();
		return "已经执行(禁用)成功";
	}
	/** 
	 执行启用
	 
	*/
	public final String DoEnableIt()
	{
		WFEmp emp = new WFEmp(this.getNo());
		emp.setUseSta(1);
		emp.Update();
		return "已经执行(启用)成功";
	}

	@Override
	protected boolean beforeUpdate()
	{
		WFEmp emp = new WFEmp(this.getNo());
		emp.Update();
		return super.beforeUpdate();
	}
	@Override
	public Entities getGetNewEntities()
	{
		return new Emps();
	}
}