package BP.Sys.Frm;


public enum PicType
{
	/** 
	 自动签名
	 
	*/
	ZiDong,
	/** 
	 手动签名
	 
	*/
	ShouDong;

	public int getValue()
	{
		return this.ordinal();
	}

	public static PicType forValue(int value)
	{
		return values()[value];
	}
}