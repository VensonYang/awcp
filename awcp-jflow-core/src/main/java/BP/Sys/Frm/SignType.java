package BP.Sys.Frm;


/** 
 数字签名类型
 
*/
public enum SignType
{
	/** 
	 无
	 
	*/
	None,
	/** 
	 图片
	 
	*/
	Pic,
	/** 
	 CA签名.
	 
	*/
	CA;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SignType forValue(int value)
	{
		return values()[value];
	}
}