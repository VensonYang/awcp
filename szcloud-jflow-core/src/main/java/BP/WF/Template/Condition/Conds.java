package BP.WF.Template.Condition;

import java.util.ArrayList;

import BP.En.Entities;
import BP.En.Entity;
import BP.WF.Data.GERpt;
import BP.WF.Template.CondOrAnd;

/** 
 条件s
 
*/
public class Conds extends Entities
{
	@SuppressWarnings("unchecked")
	public static ArrayList<Cond> convertConds(Object obj) {
		return (ArrayList<Cond>) obj;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 获得Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Cond();
	}
	/** 
	 在这里面的所有条件是不是都符合.
	 
	*/
	public final boolean getIsAllPassed()
	{
		if (this.size() == 0)
		{
			throw new RuntimeException("@没有要判断的集合.");
		}

		for (Cond en : Conds.convertConds(this))
		{
			if (en.getIsPassed() == false)
			{
				return false;
			}
		}
		return true;
	}
	public final CondOrAnd getCondOrAnd()
	{
		for (Cond item :  Conds.convertConds(this))
		{
			return item.getCondOrAnd();
		}

		return CondOrAnd.ByAnd;
	}
	/** 
	 是否通过
	 
	*/
	public final boolean getIsPass()
	{
		if (this.getCondOrAnd() == CondOrAnd.ByAnd)
		{
			return this.getIsPassAnd();
		}
		else
		{
			return this.getIsPassOr();
		}
	}
	/** 
	 是否通过  
	 
	*/
	public final boolean getIsPassAnd()
	{
			// 判断  and. 的关系。
		for (Cond en : Conds.convertConds(this))
		{
			if (en.getIsPassed() == false)
			{
				return false;
			}
		}
		return true;
	}
	public final boolean getIsPassOr()
	{
			// 判断  and. 的关系。
		for (Cond en :  Conds.convertConds(this))
		{
			if (en.getIsPassed() == true)
			{
				return true;
			}
		}
		return false;
	}
	/** 
	 描述
	 
	*/
	public final String getMsgOfDesc()
	{
		String msg = "";
		for (Cond c : Conds.convertConds(this))
		{
			msg += "@" + c.MsgOfCond;
		}
		return msg;
	}
	/** 
	 是不是其中的一个passed. 
	 
	*/
	public final boolean getIsOneOfCondPassed()
	{
		for (Cond en :  Conds.convertConds(this))
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
	public final Cond getGetOneOfCondPassed()
	{
		for (Cond en :  Conds.convertConds(this))
		{
			if (en.getIsPassed() == true)
			{
				return en;
			}
		}
		throw new RuntimeException("@没有完成条件。");
	}
	public int NodeID = 0;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 条件
	 
	*/
	public Conds()
	{
	}
	/** 
	 条件
	 
	 @param fk_flow 流程编号
	*/
	public Conds(String fk_flow)
	{
		this.Retrieve(CondAttr.FK_Flow, fk_flow);
	}
	/** 
	 条件
	 
	 @param ct 类型
	 @param nodeID 节点
	*/
	public Conds(CondType ct, int nodeID, long workid, GERpt enData)
	{
		this.NodeID = nodeID;
		this.Retrieve(CondAttr.NodeID, nodeID, CondAttr.CondType, ct.getValue(), CondAttr.PRI);
		for (Cond en :  Conds.convertConds(this))
		{
			en.setWorkID(workid);
			en.en = enData;
		}
	}

	public final String getConditionDesc()
	{
		return "";
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}