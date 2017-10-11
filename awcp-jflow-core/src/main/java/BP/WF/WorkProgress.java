package BP.WF;

import BP.WF.*;
import BP.Web.*;
import BP.En.*;
import BP.DA.*;
import BP.En.*;

public enum WorkProgress
{
	/** 
	 正常运行
	 
	*/
	Runing,
	/** 
	 预警
	 
	*/
	Alert,
	/** 
	 逾期
	 
	*/
	Timeout;

	public int getValue()
	{
		return this.ordinal();
	}

	public static WorkProgress forValue(int value)
	{
		return values()[value];
	}
}