package BP.WF.Template.PubLib;

/** 
 表单运行类型
 
*/
public enum FormRunType
{
	/** 
	 傻瓜表单.
	 
	*/
	FixForm(0),
	/** 
	 自由表单.
	 
	*/
	FreeForm(1),
	/** 
	 自定义表单.
	 
	*/
	SelfForm(2),
	/** 
	 Silverlight表单
	 
	*/
	SLForm(4);

	private int intValue;
	private static java.util.HashMap<Integer, FormRunType> mappings;
	private synchronized static java.util.HashMap<Integer, FormRunType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, FormRunType>();
		}
		return mappings;
	}

	private FormRunType(int value)
	{
		intValue = value;
		FormRunType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static FormRunType forValue(int value)
	{
		return getMappings().get(value);
	}
}