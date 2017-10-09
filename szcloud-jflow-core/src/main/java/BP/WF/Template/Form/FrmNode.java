package BP.WF.Template.Form;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.Port.*;
import BP.Sys.*;
import BP.Sys.Frm.FrmType;

/** 
 节点表单
 节点的工作节点有两部分组成.	 
 记录了从一个节点到其他的多个节点.
 也记录了到这个节点的其他的节点.
 
*/
public class FrmNode extends EntityMyPK
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 关于节点与office表单的toolbar权限控制方案.

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 关于节点与office表单的toolbar权限控制方案.

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	public final String getFrmUrl()
	{
		switch (this.getHisFrmType())
		{
			case Column4Frm:
				return BP.WF.Glo.getCCFlowAppPath() + "WF/CCForm/FrmFix";
			case FreeFrm:
				return BP.WF.Glo.getCCFlowAppPath() + "WF/CCForm/Frm";
			case SLFrm:
				return BP.WF.Glo.getCCFlowAppPath() + "WF/CCForm/SLFrm";
			default:
				throw new RuntimeException("err,未处理。");
		}
	}
	private Frm _hisFrm = null;
	public final Frm getHisFrm()
	{
		if (this._hisFrm == null)
		{
			this._hisFrm = new Frm(this.getFK_Frm());
			this._hisFrm.HisFrmNode = this;
		}
		return this._hisFrm;
	}
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
	public final FrmType getHisFrmType()
	{
		return FrmType.forValue(this.GetValIntByKey(FrmNodeAttr.FrmType));
	}
	public final void setHisFrmType(FrmType value)
	{
		this.SetValByKey(FrmNodeAttr.FrmType, value.getValue());
	}
	/** 
	 是否启用装载填充事件
	 
	*/
	public final boolean getIsEnableLoadData()
	{
		return this.GetValBooleanByKey(FrmNodeAttr.IsEnableLoadData);
	}
	public final void setIsEnableLoadData(boolean value)
	{
		this.SetValByKey(FrmNodeAttr.IsEnableLoadData, value);
	}
	/** 
	节点
	 
	*/
	public final int getFK_Node()
	{
		return this.GetValIntByKey(FrmNodeAttr.FK_Node);
	}
	public final void setFK_Node(int value)
	{
		this.SetValByKey(FrmNodeAttr.FK_Node, value);
	}
	/** 
	 顺序号
	 
	*/
	public final int getIdx()
	{
		return this.GetValIntByKey(FrmNodeAttr.Idx);
	}
	public final void setIdx(int value)
	{
		this.SetValByKey(FrmNodeAttr.Idx, value);
	}
	/** 
	 谁是主键？
	 
	*/
	public final WhoIsPK getWhoIsPK()
	{
		return WhoIsPK.forValue(this.GetValIntByKey(FrmNodeAttr.WhoIsPK));
	}
	public final void setWhoIsPK(WhoIsPK value)
	{
		this.SetValByKey(FrmNodeAttr.WhoIsPK, value.getValue());
	}
	/** 
	 工作流程
	 
	*/
	public final String getFK_Frm()
	{
		return this.GetValStringByKey(FrmNodeAttr.FK_Frm);
	}
	public final void setFK_Frm(String value)
	{
		this.SetValByKey(FrmNodeAttr.FK_Frm, value);
	}
	/** 
	 对应的解决方案
	 
	*/
	public final int getFrmSln()
	{
		return this.GetValIntByKey(FrmNodeAttr.FrmSln);
	}
	public final void setFrmSln(int value)
	{
		this.SetValByKey(FrmNodeAttr.FrmSln, value);
	}
	/** 
	 流程编号
	 
	*/
	public final String getFK_Flow()
	{
		return this.GetValStringByKey(FrmNodeAttr.FK_Flow);
	}
	public final void setFK_Flow(String value)
	{
		this.SetValByKey(FrmNodeAttr.FK_Flow, value);
	}
	public final boolean getIsEdit()
	{
		return this.GetValBooleanByKey(FrmNodeAttr.IsEdit);
	}
	public final void setIsEdit(boolean value)
	{
		this.SetValByKey(FrmNodeAttr.IsEdit, value);
	}
	public final boolean getIsPrint()
	{
		return this.GetValBooleanByKey(FrmNodeAttr.IsPrint);
	}
	public final void setIsPrint(boolean value)
	{
		this.SetValByKey(FrmNodeAttr.IsPrint, value);
	}
	public final int getIsEditInt()
	{
		return this.GetValIntByKey(FrmNodeAttr.IsEdit);
	}
	public final int getIsPrintInt()
	{
		return this.GetValIntByKey(FrmNodeAttr.IsPrint);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 节点表单
	 
	*/
	public FrmNode()
	{
	}
	/** 
	 节点表单
	 
	 @param mypk
	*/
	public FrmNode(String mypk)
	{
		super(mypk);
	}
	/** 
	 节点表单
	 
	 @param fk_node 节点
	 @param fk_frm 表单
	*/
	public FrmNode(String fk_flow, int fk_node, String fk_frm)
	{
		int i = this.Retrieve(FrmNodeAttr.FK_Flow, fk_flow, FrmNodeAttr.FK_Node, fk_node, FrmNodeAttr.FK_Frm, fk_frm);
		if (i == 0)
		{
			this.setIsPrint(false);
			this.setIsEdit(false);
			return;
			//throw new RuntimeException("@表单关联信息已被删除。");
		}
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

		Map map = new Map("WF_FrmNode");
		map.setEnDesc ( "节点表单");

		map.setDepositaryOfEntity ( Depositary.None);
		map.setDepositaryOfMap ( Depositary.Application);

		map.AddMyPK();
		map.AddTBString(FrmNodeAttr.FK_Frm, null, "表单ID", true, true, 1, 32, 32);
		map.AddTBInt(FrmNodeAttr.FK_Node, 0, "节点编号", true, false);
		map.AddTBString(FrmNodeAttr.FK_Flow, null, "流程编号", true, true, 1, 20, 20);

		map.AddTBString(FrmNodeAttr.FrmType, "0", "表单类型", true, true, 1, 20, 20);

			//菜单在本节点的权限控制.
		map.AddTBInt(FrmNodeAttr.IsEdit, 1, "是否可以更新", true, false);
		map.AddTBInt(FrmNodeAttr.IsPrint, 0, "IsPrint", true, false);
		map.AddTBInt(FrmNodeAttr.IsEnableLoadData, 0, "是否启用装载填充事件", true, false);


			//显示的
		map.AddTBInt(FrmNodeAttr.Idx, 0, "顺序号", true, false);
		map.AddTBInt(FrmNodeAttr.FrmSln, 0, "表单控制方案", true, false);

			// add 2014-01-26
		map.AddTBInt(FrmNodeAttr.WhoIsPK, 0, "谁是主键？", true, false);


		this.set_enMap ( map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final void DoUp()
	{
		this.DoOrderUp(FrmNodeAttr.FK_Node, (new Integer(this.getFK_Node())).toString(), FrmNodeAttr.Idx);
	}
	public final void DoDown()
	{
		this.DoOrderDown(FrmNodeAttr.FK_Node, (new Integer(this.getFK_Node())).toString(), FrmNodeAttr.Idx);
	}

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		this.setMyPK( this.getFK_Frm() + "_" + this.getFK_Node() + "_" + this.getFK_Flow());
		return super.beforeUpdateInsertAction();
	}
}