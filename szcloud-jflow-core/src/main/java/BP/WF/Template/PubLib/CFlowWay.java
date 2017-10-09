package BP.WF.Template.PubLib;

/** 
 延续流程方式
 
*/
public enum CFlowWay
{
	/** 
	 无:非延续类流程
	 
	*/
	None,
	/** 
	 按照参数获取
	 
	*/
	ByParas,
	/** 
	 按照指定的字段获取
	 
	*/
	BySpecField;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CFlowWay forValue(int value)
	{
		return values()[value];
	}
}