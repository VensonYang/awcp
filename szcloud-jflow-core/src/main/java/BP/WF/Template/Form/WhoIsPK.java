package BP.WF.Template.Form;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.Port.*;
import BP.Sys.*;

/** 
 谁是主键？
 
*/
public enum WhoIsPK
{
	/** 
	 工作ID是主键
	 
	*/
	OID,
	/** 
	 流程ID是主键
	 
	*/
	FID,
	/** 
	 父流程ID是主键
	 
	*/
	PWorkID,
	/** 
	 延续流程ID是主键
	 
	*/
	CWorkID;

	public int getValue()
	{
		return this.ordinal();
	}

	public static WhoIsPK forValue(int value)
	{
		return values()[value];
	}
}