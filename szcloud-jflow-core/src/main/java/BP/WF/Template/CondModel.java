package BP.WF.Template;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Port.*;

/** 
 方向条件控制规则
 
*/
public enum CondModel
{
	/** 
	 按照用户设置的方向条件计算
	 
	*/
	ByLineCond,
	/** 
	 按照用户选择计算
	 
	*/
	ByUserSelected;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CondModel forValue(int value)
	{
		return values()[value];
	}
}