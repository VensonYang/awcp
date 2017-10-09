package BP.WF.Template.PubLib;

/** 
 谁执行它
 
*/
public enum WhoDoIt
{
	/** 
	 操作员
	 
	*/
	Operator,
	/** 
	 机器
	 
	*/
	MachtionOnly,
	/** 
	 混合
	 
	*/
	Mux;

	public int getValue()
	{
		return this.ordinal();
	}

	public static WhoDoIt forValue(int value)
	{
		return values()[value];
	}
}