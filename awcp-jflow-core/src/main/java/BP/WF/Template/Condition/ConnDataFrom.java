package BP.WF.Template.Condition;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Data.*;

/** 
 条件数据源
 
*/
public enum ConnDataFrom
{
	/** 
	 表单数据
	 
	*/
	Form,
	/** 
	 岗位数据
	 
	*/
	Stas,
	/** 
	 Depts
	 
	*/
	Depts,
	/** 
	 按sql计算.
	 
	*/
	SQL,
	/** 
	 按参数
	 
	*/
	Paras,
	/** 
	 按Url.
	 
	*/
	Url;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ConnDataFrom forValue(int value)
	{
		return values()[value];
	}
}