package BP.WF.Template.Ext;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Port.*;

/** 
 待办工作超时处理方式
 
*/
public enum OutTimeDeal
{
	/** 
	 不处理
	 
	*/
	None,
	/** 
	 自动的转向下一步骤
	 
	*/
	AutoTurntoNextStep,
	/** 
	 自动跳转到指定的点
	 
	*/
	AutoJumpToSpecNode,
	/** 
	 自动移交到指定的人员
	 
	*/
	AutoShiftToSpecUser,
	/** 
	 向指定的人员发送消息
	 
	*/
	SendMsgToSpecUser,
	/** 
	 删除流程
	 
	*/
	DeleteFlow,
	/** 
	 执行SQL
	 
	*/
	RunSQL;

	public int getValue()
	{
		return this.ordinal();
	}

	public static OutTimeDeal forValue(int value)
	{
		return values()[value];
	}
}