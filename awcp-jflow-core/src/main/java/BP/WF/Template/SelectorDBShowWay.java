package BP.WF.Template;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Port.*;

/** 
 显示方式
 
*/
public enum SelectorDBShowWay
{
	/** 
	 表格
	 
	*/
	Table,
	/** 
	 树
	 
	*/
	Tree;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SelectorDBShowWay forValue(int value)
	{
		return values()[value];
	}
}