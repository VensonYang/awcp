package BP.WF.Template.CC;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.Template.Nodes;
import BP.WF.Template.PubLib.NodeAttr;

/** 
 抄送部门
 节点的工作部门有两部分组成.	 
 记录了从一个节点到其他的多个节点.
 也记录了到这个节点的其他的节点.
 
*/
public class CCDept extends EntityMM
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	/** 
	节点
	 
	*/
	public final int getFK_Node()
	{
		return this.GetValIntByKey(CCDeptAttr.FK_Node);
	}
	public final void setFK_Node(int value)
	{
		this.SetValByKey(CCDeptAttr.FK_Node,value);
	}
	/** 
	 工作部门
	 
	*/
	public final String getFK_Dept()
	{
		return this.GetValStringByKey(CCDeptAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		this.SetValByKey(CCDeptAttr.FK_Dept,value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 抄送部门
	 
	*/
	public CCDept()
	{
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

		Map map = new Map("WF_CCDept");
		map.setEnDesc("抄送部门");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.AddDDLEntitiesPK(CCDeptAttr.FK_Node,0,DataType.AppInt,"节点",new Nodes(),NodeAttr.NodeID,NodeAttr.Name,true);
		map.AddDDLEntitiesPK(CCDeptAttr.FK_Dept,null,"部门",new Depts(),true);
		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}