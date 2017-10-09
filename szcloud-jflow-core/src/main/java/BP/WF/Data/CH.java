package BP.WF.Data;

import BP.DA.Depositary;
import BP.En.EntityMyPK;
import BP.En.Map;
import BP.En.UAC;
import BP.WF.Comm.CHSta;

/** 
时效考核
 
*/
public class CH extends EntityMyPK
{
		///#region 基本属性
	/** 
	 考核状态
	 
	*/
	public final CHSta getCHSta()
	{
		return CHSta.forValue(this.GetValIntByKey(CHAttr.CHSta));
	}
	public final void setCHSta(CHSta value)
	{
		this.SetValByKey(CHAttr.CHSta, value.getValue());
	}
	/** 
	 时间到
	 
	*/
	public final String getDTTo()
	{
		return this.GetValStringByKey(CHAttr.DTTo);
	}
	public final void setDTTo(String value)
	{
		this.SetValByKey(CHAttr.DTTo, value);
	}
	/** 
	 时间从
	 
	*/
	public final String getDTFrom()
	{
		return this.GetValStringByKey(CHAttr.DTFrom);
	}
	public final void setDTFrom(String value)
	{
		this.SetValByKey(CHAttr.DTFrom, value);
	}
	/** 
	 应完成日期
	 
	*/
	public final String getSDT()
	{
		return this.GetValStringByKey(CHAttr.SDT);
	}
	public final void setSDT(String value)
	{
		this.SetValByKey(CHAttr.SDT, value);
	}
	/** 
	 流程标题
	 
	*/
	public final String getTitle()
	{
		return this.GetValStringByKey(CHAttr.Title);
	}
	public final void setTitle(String value)
	{
		this.SetValByKey(CHAttr.Title, value);
	}
	/** 
	 流程编号
	 
	*/
	public final String getFK_Flow()
	{
		return this.GetValStringByKey(CHAttr.FK_Flow);
	}
	public final void setFK_Flow(String value)
	{
		this.SetValByKey(CHAttr.FK_Flow, value);
	}
	/** 
	 流程
	 
	*/
	public final String getFK_FlowT()
	{
		return this.GetValStringByKey(CHAttr.FK_FlowT);
	}
	public final void setFK_FlowT(String value)
	{
		this.SetValByKey(CHAttr.FK_FlowT, value);
	}
	/** 
	 限期
	 
	*/
	public final String getTSpan()
	{
		return this.GetValStringByKey(CHAttr.TSpan);
	}
	public final void setTSpan(String value)
	{
		this.SetValByKey(CHAttr.TSpan, value);
	}
	/** 
	 实际完成用时.
	 
	*/
	public final int getUseMinutes()
	{
		return this.GetValIntByKey(CHAttr.UseMinutes);
	}
	public final void setUseMinutes(int value)
	{
		this.SetValByKey(CHAttr.UseMinutes, value);
	}
	public final String getUseTime()
	{
		return this.GetValStringByKey(CHAttr.UseTime);
	}
	public final void setUseTime(String value)
	{
		this.SetValByKey(CHAttr.UseTime, value);
	}
	/** 
	 超过时限
	 
	*/
	public final int getOverMinutes()
	{
		return this.GetValIntByKey(CHAttr.OverMinutes);
	}
	public final void setOverMinutes(int value)
	{
		this.SetValByKey(CHAttr.OverMinutes, value);
	}
	/** 
	 预期
	 
	*/
	public final String getOverTime()
	{
		return this.GetValStringByKey(CHAttr.OverTime);
	}
	public final void setOverTime(String value)
	{
		this.SetValByKey(CHAttr.OverTime, value);
	}
	/** 
	 操作人员
	 
	*/
	public final String getFK_Emp()
	{
		return this.GetValStringByKey(CHAttr.FK_Emp);
	}
	public final void setFK_Emp(String value)
	{
		this.SetValByKey(CHAttr.FK_Emp, value);
	}
	/** 
	 人员
	 
	*/
	public final String getFK_EmpT()
	{
		return this.GetValStringByKey(CHAttr.FK_EmpT);
	}
	public final void setFK_EmpT(String value)
	{
		this.SetValByKey(CHAttr.FK_EmpT, value);
	}
	/** 
	 部门
	 
	*/
	public final String getFK_Dept()
	{
		return this.GetValStrByKey(CHAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		this.SetValByKey(CHAttr.FK_Dept, value);
	}
	/** 
	 部门名称
	 
	*/
	public final String getFK_DeptT()
	{
		return this.GetValStrByKey(CHAttr.FK_DeptT);
	}
	public final void setFK_DeptT(String value)
	{
		this.SetValByKey(CHAttr.FK_DeptT, value);
	}
	/** 
	 年月
	 
	*/
	public final String getFK_NY()
	{
		return this.GetValStrByKey(CHAttr.FK_NY);
	}
	public final void setFK_NY(String value)
	{
		this.SetValByKey(CHAttr.FK_NY, value);
	}
	/** 
	 周
	 
	*/
	public final int getWeek()
	{
		return this.GetValIntByKey(CHAttr.Week);
	}
	public final void setWeek(int value)
	{
		this.SetValByKey(CHAttr.Week, value);
	}
	/** 
	 工作ID
	 
	*/
	public final long getWorkID()
	{
		return this.GetValInt64ByKey(CHAttr.WorkID);
	}
	public final void setWorkID(long value)
	{
		this.SetValByKey(CHAttr.WorkID, value);
	}
	/** 
	 流程ID
	 
	*/
	public final long getFID()
	{
		return this.GetValInt64ByKey(CHAttr.FID);
	}
	public final void setFID(long value)
	{
		this.SetValByKey(CHAttr.FID, value);
	}
	/** 
	 节点ID
	 
	*/
	public final int getFK_Node()
	{
		return this.GetValIntByKey(CHAttr.FK_Node);
	}
	public final void setFK_Node(int value)
	{
		this.SetValByKey(CHAttr.FK_Node, value);
	}
	/** 
	 节点名称
	 
	*/
	public final String getFK_NodeT()
	{
		return this.GetValStrByKey(CHAttr.FK_NodeT);
	}
	public final void setFK_NodeT(String value)
	{
		this.SetValByKey(CHAttr.FK_NodeT, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.IsDelete = false;
		uac.IsInsert = false;
		uac.IsUpdate = false;
		uac.IsView = true;
		return uac;
	}
	/** 
	 时效考核
	 
	*/
	public CH()
	{
	}
	/** 
	 
	 
	 @param pk
	*/
	public CH(String pk)
	{
		super(pk);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Map
	/** 
	 EnMap
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("WF_CH");
		map.setDepositaryOfMap(Depositary.None);
		map.setEnDesc("时效考核");

		map.AddMyPK();

		map.AddTBInt(CHAttr.WorkID, 0, "工作ID", false, true);
		map.AddTBInt(CHAttr.FID, 0, "FID", false, true);

		map.AddTBString(CHAttr.Title, null, "标题", false, false, 0, 900, 5);

		map.AddTBString(CHAttr.FK_Flow, null, "流程", false, false, 3, 3, 3);
		map.AddTBString(CHAttr.FK_FlowT, null, "流程名称", true, true, 0, 50, 5);

		map.AddTBInt(CHAttr.FK_Node, 0, "节点", false, false);
		map.AddTBString(CHAttr.FK_NodeT, null, "节点名称", true, true, 0, 50, 5);

		map.AddTBString(CHAttr.DTFrom, null, "时间从", true, true, 0, 50, 5);
		map.AddTBString(CHAttr.DTTo, null, "到", true, true, 0, 50, 5);
		map.AddTBString(CHAttr.SDT, null, "应完成日期", true, true, 0, 50, 5);

		map.AddTBString(CHAttr.TSpan, null, "规定限期", true, true, 0, 50, 5);

		map.AddTBInt(CHAttr.UseMinutes, 0, "实际使用分钟", false, true);
		map.AddTBString(CHAttr.UseTime, null, "实际使用时间", true, true, 0, 50, 5);

		map.AddTBInt(CHAttr.OverMinutes, 0, "逾期分钟", false, true);
		map.AddTBString(CHAttr.OverTime, null, "逾期", true, true, 0, 50, 5);

		map.AddTBString(CHAttr.FK_Dept, null, "隶属部门", true, true, 0, 50, 5);
		map.AddTBString(CHAttr.FK_DeptT, null, "部门名称", true, true, 0, 50, 5);

		map.AddTBString(CHAttr.FK_Emp, null, "当事人", true, true, 0, 30, 3);
		map.AddTBString(CHAttr.FK_EmpT, null, "当事人名称", true, true, 0, 50, 5);

		map.AddTBString(CHAttr.FK_NY, null, "隶属月份", true, true, 0, 10, 10);
		map.AddTBInt(CHAttr.Week, 0, "第几周", false, true);

		map.AddTBInt(CHAttr.FID, 0, "FID", false, true);
		map.AddTBInt(CHAttr.CHSta, 0, "状态", true, true);
		map.AddTBIntMyNum();

			//map.AddSearchAttr(CHAttr.FK_Dept);
			//map.AddSearchAttr(CHAttr.FK_NY);
			//map.AddSearchAttr(CHAttr.FK_Emp);

			//RefMethod rm = new RefMethod();
			//rm.Title = "打开";
			//rm.ClassMethodName = this.ToString() + ".DoOpen";
			//rm.Icon = "/WF/Img/FileType/doc.gif";
			//map.AddRefMethod(rm);

			//rm = new RefMethod();
			//rm.Title = "打开";
			//rm.ClassMethodName = this.ToString() + ".DoOpenPDF";
			//rm.Icon = "/WF/Img/FileType/pdf.gif";
			//map.AddRefMethod(rm);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		return super.beforeUpdateInsertAction();
	}
}

