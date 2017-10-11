package BP.WF.Template.Condition;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Data.*;

/** 
 条件类型
 
*/
public enum CondType
{
	/** 
	 节点完成条件
	 
	*/
	Node,
	/** 
	 流程条件
	 
	*/
	Flow,
	/** 
	 方向条件
	 
	*/
	Dir;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CondType forValue(int value)
	{
		return values()[value];
	}
}