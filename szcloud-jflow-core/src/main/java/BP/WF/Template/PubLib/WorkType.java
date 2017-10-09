package BP.WF.Template.PubLib;

/** 
 工作类型
 
*/
public enum WorkType
{
	/** 
	 普通的
	 
	*/
	Ordinary,
	/** 
	 自动的
	 
	*/
	Auto;

	public int getValue()
	{
		return this.ordinal();
	}

	public static WorkType forValue(int value)
	{
		return values()[value];
	}
}