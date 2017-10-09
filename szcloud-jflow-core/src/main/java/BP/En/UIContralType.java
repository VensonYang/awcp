package BP.En;


/** 
  控件类型
 
*/
public enum UIContralType
{
	/** 
	 文本框
	 
	*/
	TB(0),
	/** 
	 下拉框
	 
	*/
	DDL(1),
	/** 
	 CheckBok
	 
	*/
	CheckBok(2),
	/** 
	 单选择按钮
	 
	*/
	RadioBtn(3);

	private int intValue;
	private static java.util.HashMap<Integer, UIContralType> mappings;
	private synchronized static java.util.HashMap<Integer, UIContralType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, UIContralType>();
		}
		return mappings;
	}

	private UIContralType(int value)
	{
		intValue = value;
		UIContralType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static UIContralType forValue(int value)
	{
		return getMappings().get(value);
	}
}