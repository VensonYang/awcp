package BP.WF.Entity;

import BP.DA.*;
import BP.En.*;
import BP.WF.Template.*;
import BP.WF.*;

/** 
 附件类型
*/
public enum FWCAth
{
	/** 
	 使用附件
	*/
	None,
	/** 
	 多附件
	*/
	MinAth,
	/** 
	 单附件
	*/
	SingerAth,
	/** 
	 图片附件
	*/
	ImgAth;
	public int getValue()
	{
		return this.ordinal();
	}

	public static FWCAth forValue(int value)
	{
		return values()[value];
	}
}