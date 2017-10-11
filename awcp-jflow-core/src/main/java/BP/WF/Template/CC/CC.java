package BP.WF.Template.CC;

import BP.DA.DBAccess;
import BP.DA.DataTable;
import BP.En.Attr;
import BP.En.EnType;
import BP.En.Entity;
import BP.En.Map;
import BP.En.UAC;
import BP.Port.DeptAttr;
import BP.Port.EmpDeptAttr;
import BP.Port.WebUser;
import BP.Tools.StringHelper;
import BP.WF.Template.NodeStationAttr;
import BP.WF.Template.AccepterRole.NodeDeptAttr;
import BP.WF.Template.AccepterRole.NodeEmpAttr;
import BP.WF.Template.PubLib.NodeAttr;

/** 
 抄送
 
*/
public class CC extends Entity
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 抄送
	 
	 @param rpt
	 @return 
	*/
	public final DataTable GenerCCers(Entity rpt)
	{
		String sql = "";
		switch (this.getHisCtrlWay())
		{
			case BySQL:
				Object tempVar = this.getCCSQL();
				sql = (String)((tempVar instanceof String) ? tempVar : null);
				sql = sql.replace("@WebUser.No", WebUser.getNo());
				sql = sql.replace("@WebUser.Name", WebUser.getName());
				sql = sql.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());
				if (sql.contains("@"))
				{
					for (Attr attr : rpt.getEnMap().getAttrs())
					{
						if (sql.contains("@") == false)
						{
							break;
						}
						sql = sql.replace("@" + attr.getKey(), rpt.GetValStrByKey(attr.getKey()));
					}
				}
				break;
			case ByEmp:
				sql = "SELECT No,Name FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM WF_CCEmp WHERE FK_Node=" + this.getNodeID() + ")";
				break;
			case ByDept:
				sql = "SELECT No,Name FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept IN ( SELECT FK_Dept FROM WF_CCDept WHERE FK_Node=" + this.getNodeID() + "))";
				break;
			case ByStation:
				sql = "SELECT No,Name FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM " + BP.WF.Glo.getEmpStation() + " WHERE FK_Station IN ( SELECT FK_Station FROM WF_CCStation WHERE FK_Node=" + this.getNodeID() + "))";
				break;
			default:
				throw new RuntimeException("未处理的异常");
		}
		DataTable dt= DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0)
		{
			throw new RuntimeException("@流程节点设计错误，未找到抄送人员，抄送方式:"+this.getHisCtrlWay()+" SQL:"+sql);
		}

		return dt;
	}
	/** 
	节点ID
	 
	*/
	public final int getNodeID()
	{
		return this.GetValIntByKey(NodeAttr.NodeID);
	}
	public final void setNodeID(int value)
	{
		this.SetValByKey(NodeAttr.NodeID, value);
	}
	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		if ( ! WebUser.getNo().equals("admin"))
		{
			uac.IsView = false;
			return uac;
		}
		uac.IsDelete = false;
		uac.IsInsert = false;
		uac.IsUpdate = true;
		return uac;
	}
	/** 
	 抄送标题
	 
	*/
	public final String getCCTitle()
	{
		String s= this.GetValStringByKey(CCAttr.CCTitle);
		if (StringHelper.isNullOrEmpty(s))
		{
			s = "来自@Rec的抄送信息.";
		}
		return s;
	}
	public final void setCCTitle(String value)
	{
		this.SetValByKey(CCAttr.CCTitle, value);
	}
	/** 
	 抄送内容
	 
	*/
	public final String getCCDoc()
	{
		String s = this.GetValStringByKey(CCAttr.CCDoc);
		if (StringHelper.isNullOrEmpty(s))
		{
			s = "来自@Rec的抄送信息.";
		}
		return s;
	}
	public final void setCCDoc(String value)
	{
		this.SetValByKey(CCAttr.CCDoc, value);
	}
	/** 
	 抄送对象
	 
	*/
	public final String getCCSQL()
	{
		String sql= this.GetValStringByKey(CCAttr.CCSQL);
		sql = sql.replace("~", "'");
		sql = sql.replace("‘", "'");
		sql = sql.replace("’", "'");
		sql = sql.replace("''", "'");
		return sql;
	}
	public final void setCCSQL(String value)
	{
		this.SetValByKey(CCAttr.CCSQL, value);
	}
	/** 
	 控制方式
	 
	*/
	public final CtrlWay getHisCtrlWay()
	{
		return CtrlWay.forValue(this.GetValIntByKey(CCAttr.CCCtrlWay));
	}
	public final void setHisCtrlWay(CtrlWay value)
	{
		this.SetValByKey(CCAttr.CCCtrlWay, value.getValue());
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造函数
	/** 
	 CC
	 
	*/
	public CC()
	{
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
		Map map = new Map("WF_Node");
		map.setEnDesc ( "抄送规则");
		map.setEnType ( EnType.Admin);
		map.AddTBString(NodeAttr.Name, null, "节点名称", true, true, 0, 100, 10, true);
		map.AddTBIntPK(NodeAttr.NodeID, 0,"节点ID", true, true);

		map.AddDDLSysEnum(CCAttr.CCCtrlWay, 0, "控制方式",true, true,"CtrlWay");
		map.AddTBString(CCAttr.CCSQL, null, "SQL表达式", true, false, 0, 500, 10, true);
		map.AddTBString(CCAttr.CCTitle, null, "抄送标题", true, false, 0, 500, 10,true);
		map.AddTBStringDoc(CCAttr.CCDoc, null, "抄送内容(标题与内容支持变量)", true, false,true);

		map.AddSearchAttr(CCAttr.CCCtrlWay);

			// 相关功能。
		map.getAttrsOfOneVSM().Add(new CCStations(), new BP.WF.Port.Stations(), NodeStationAttr.FK_Node, NodeStationAttr.FK_Station, DeptAttr.Name, DeptAttr.No, "抄送岗位");

		map.getAttrsOfOneVSM().Add(new CCDepts(), new BP.WF.Port.Depts(), NodeDeptAttr.FK_Node, NodeDeptAttr.FK_Dept, DeptAttr.Name, DeptAttr.No, "抄送部门");

		map.getAttrsOfOneVSM().Add(new CCEmps(), new BP.WF.Port.Emps(), NodeEmpAttr.FK_Node, EmpDeptAttr.FK_Emp, DeptAttr.Name, DeptAttr.No, "抄送人员");

		this.set_enMap ( map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}