package BP.Sys.Frm;


/** 
 图片应用类型
 
*/
public enum ImgAppType
{
	/** 
	 图片
	 
	*/
	Img,
	/** 
	 公章
	 
	*/
	Seal;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ImgAppType forValue(int value)
	{
		return values()[value];
	}
}