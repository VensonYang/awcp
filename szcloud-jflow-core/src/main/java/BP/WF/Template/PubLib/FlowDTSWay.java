package BP.WF.Template.PubLib;

/** 
 数据同步方案
 
*/
public enum FlowDTSWay
{
	/** 
	 不同步
	 
	*/
	None,
	/** 
	 按照工作ID
	 
	*/
	ByWorkID,
	/** 
	 按照指定的字段
	 
	*/
	BySpecField;

	public int getValue()
	{
		return this.ordinal();
	}

	public static FlowDTSWay forValue(int value)
	{
		return values()[value];
	}
}