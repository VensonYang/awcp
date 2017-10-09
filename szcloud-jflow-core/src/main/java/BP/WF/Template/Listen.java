package BP.WF.Template;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;
import BP.Tools.StringHelper;

/** 
 消息收听
 节点的收听节点有两部分组成.	 
 记录了从一个节点到其他的多个节点.
 也记录了到这个节点的其他的节点.
 
*/
public class Listen extends EntityOID
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	//public BP.WF.Port.AlertWay HisAlertWay
	//{
	//    get
	//    {
	//        return (BP.WF.Port.AlertWay)this.GetValIntByKey(ListenAttr.AlertWay);
	//    }
	//    set
	//    {
	//        this.SetValByKey(ListenAttr.AlertWay, (int)value);
	//    }
	//}

	/** 
	节点
	 
	*/
	public final int getFK_Node()
	{
		return this.GetValIntByKey(ListenAttr.FK_Node);
	}
	public final void setFK_Node(int value)
	{
		this.SetValByKey(ListenAttr.FK_Node, value);
	}
	/** 
	 收听节点
	 
	*/
	public final String getNodes()
	{
		return this.GetValStringByKey(ListenAttr.Nodes);
	}
	public final void setNodes(String value)
	{
		this.SetValByKey(ListenAttr.Nodes, value);
	}
	public final String getNodesDesc()
	{
		return this.GetValStringByKey(ListenAttr.NodesDesc);
	}
	public final void setNodesDesc(String value)
	{
		this.SetValByKey(ListenAttr.NodesDesc, value);
	}
	public final String getDoc()
	{
		String s= this.GetValStringByKey(ListenAttr.Doc);
		if (StringHelper.isNullOrEmpty(s) == true)
		{
			s = "";
		}
		return s;
	}
	public final void setDoc(String value)
	{
		this.SetValByKey(ListenAttr.Doc, value);
	}
	public final String getTitle()
	{
		return this.GetValStringByKey(ListenAttr.Title);
	}
	public final void setTitle(String value)
	{
		this.SetValByKey(ListenAttr.Title, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 消息收听
	 
	*/
	public Listen()
	{
	}
	public Listen(int oid)
	{
		this.setOID (oid);
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

		Map map = new Map("WF_Listen");
		map.setEnDesc("消息收听");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap( Depositary.Application);

		map.AddTBIntPKOID();
		map.AddTBInt(ListenAttr.FK_Node, 0, "节点", true, false);
		map.AddTBString(ListenAttr.Nodes, null, "Nodes", true, false, 0, 400, 10);
		map.AddTBString(ListenAttr.NodesDesc, null, "描述", true, false, 0, 400, 10);
		map.AddTBString(ListenAttr.Title, null, "Title", true, false, 0, 400, 10);
		map.AddTBStringDoc();
		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}