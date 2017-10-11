package BP.WF.Template.FindWorker;

import BP.DA.*;
import BP.En.*;
import BP.Web.*;
import BP.Sys.*;

public enum FindColleague
{
	/** 
	 所有 
	 
	*/
	All,
	/** 
	 指定职务
	 
	*/
	SpecDuty,
	/** 
	 指定岗位
	 
	*/
	SpecStation;

	public int getValue()
	{
		return this.ordinal();
	}

	public static FindColleague forValue(int value)
	{
		return values()[value];
	}
}