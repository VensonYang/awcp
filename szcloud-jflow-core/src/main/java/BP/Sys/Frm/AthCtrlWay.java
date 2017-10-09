package BP.Sys.Frm;


public enum AthCtrlWay
{
	/** 
	 表单主键
	 
	*/
	PK,
	/** 
	 FID
	 
	*/
	FID,
	/** 
	 父流程ID
	 
	*/
	PWorkID;

	public int getValue()
	{
		return this.ordinal();
	}

	public static AthCtrlWay forValue(int value)
	{
		return values()[value];
	}
}