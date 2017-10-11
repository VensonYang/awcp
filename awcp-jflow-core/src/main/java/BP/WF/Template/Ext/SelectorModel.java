package BP.WF.Template.Ext;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Port.*;

public enum SelectorModel
{
	/** 
	 表格
	 
	*/
	Station,
	/** 
	 树
	 
	*/
	Dept,
	/** 
	 操作员
	 
	*/
	Emp,
	/** 
	 SQL
	 
	*/
	SQL,
	/** 
	 自定义链接
	 
	*/
	Url;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SelectorModel forValue(int value)
	{
		return values()[value];
	}
}