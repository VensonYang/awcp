package BP.WF.Template.Condition;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;

/** 
 条件s
 
*/
public class TurnTos extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 条件
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new TurnTo();
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<TurnTo> convertTurnTos(Object obj) {
		return (ArrayList<TurnTo>) obj;
	}
	/** 
	 条件.
	 
	*/
	public final boolean getIsAllPassed()
	{
		if (this.size() == 0)
		{
			throw new RuntimeException("@没有要判断的集合.");
		}

		for (TurnTo en : convertTurnTos(this))
		{
			if (en.getIsPassed() == false)
			{
				return false;
			}
		}
		return true;
	}
	/** 
	 是否通过
	 
	*/
	public final boolean getIsPass()
	{
		if (this.size() == 1)
		{
			if (this.getIsOneOfTurnToPassed())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	public final String getMsgOfDesc()
	{
		String msg = "";
		for (TurnTo c : convertTurnTos(this))
		{
			msg += "@" + c.MsgOfTurnTo;
		}
		return msg;
	}
	/** 
	 是不是其中的一个passed. 
	 
	*/
	public final boolean getIsOneOfTurnToPassed()
	{
		for (TurnTo en : convertTurnTos(this))
		{
			if (en.getIsPassed() == true)
			{
				return true;
			}
		}
		return false;
	}
	/** 
	 取出其中一个的完成条件。. 
	 
	*/
	public final TurnTo getGetOneOfTurnToPassed()
	{
		for (TurnTo en : convertTurnTos(this))
		{
			if (en.getIsPassed() == true)
			{
				return en;
			}
		}
		throw new RuntimeException("@没有完成条件。");
	}
	/** 
	 节点ID
	 
	*/
	public int NodeID = 0;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 条件
	 
	*/
	public TurnTos()
	{
	}
	/** 
	 条件
	 
	*/
	public TurnTos(String fk_flow)
	{
		this.Retrieve(TurnToAttr.FK_Flow, fk_flow);
	}
	/** 
	 条件
	 
	 @param ct 类型
	 @param nodeID 节点
	*/
	public TurnTos(TurnToType ct, int nodeID, long workid)
	{
		this.NodeID = nodeID;
		this.Retrieve(TurnToAttr.FK_Node, nodeID, TurnToAttr.TurnToType, ct.getValue());

		for (TurnTo en : convertTurnTos(this))
		{
			en.WorkID = workid;
		}
	}
	/** 
	 描述
	 
	*/
	public final String getTurnToitionDesc()
	{
		return "";
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}