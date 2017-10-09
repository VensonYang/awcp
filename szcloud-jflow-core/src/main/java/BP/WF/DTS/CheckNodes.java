package BP.WF.DTS;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.Port.*;
import BP.En.*;
import BP.Sys.*;
import BP.WF.Data.*;
import BP.WF.Template.*;
import BP.DTS.*;

public class CheckNodes extends BP.DTS.DataIOEn
{
	/** 
	 调度人员,岗位,部门
	 
	*/
	public CheckNodes()
	{
		this.HisDoType = DoType.UnName;
		this.Title = "修复节点信息";
	  //  this.HisRunTimeType = RunTimeType.UnName;
		this.FromDBUrl = DBUrlType.AppCenterDSN;
		this.ToDBUrl = DBUrlType.AppCenterDSN;
	}
	@Override
	public void Do()
	{

		//MDCheck md = new MDCheck();
		//md.Do();

		//执行调度部门。
		//BP.Port.DTS.GenerDept gd = new BP.Port.DTS.GenerDept();
		//gd.Do();

		// 调度人员信息。
		// Emp emp = new Emp(Web.WebUser.No);
		// emp.DoDTSEmpDeptStation();
	}
}