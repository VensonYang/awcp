package BP.WF.Template.PubLib;

/** 
 附件开放类型
 
*/
public enum FJOpen
{
	/** 
	 不开放
	 
	*/
	None,
	/** 
	 对操作员开放
	 
	*/
	ForEmp,
	/** 
	 对工作ID开放
	 
	*/
	ForWorkID,
	/** 
	 对流程ID开放
	 
	*/
	ForFID;

	public int getValue()
	{
		return this.ordinal();
	}

	public static FJOpen forValue(int value)
	{
		return values()[value];
	}
}