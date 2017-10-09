package BP.WF.Template.Condition;

import BP.DA.*;
import BP.En.*;

/** 
 条件类型
 
*/
public enum TurnToType
{
	/** 
	 节点
	 
	*/
	Node,
	/** 
	 流程
	 
	*/
	Flow;

	public int getValue()
	{
		return this.ordinal();
	}

	public static TurnToType forValue(int value)
	{
		return values()[value];
	}
}