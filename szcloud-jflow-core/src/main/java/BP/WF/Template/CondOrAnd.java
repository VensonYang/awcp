package BP.WF.Template;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Port.*;

/** 
 关系类型
 
*/
public enum CondOrAnd
{
	/** 
	 关系集合里面的所有条件都成立.
	 
	*/
	ByAnd,
	/** 
	 关系集合里的只有一个条件成立.
	 
	*/
	ByOr;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CondOrAnd forValue(int value)
	{
		return values()[value];
	}
}