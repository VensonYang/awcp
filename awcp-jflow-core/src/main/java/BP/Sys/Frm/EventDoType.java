package BP.Sys.Frm;


public enum EventDoType
{
	/** 
	 禁用
	 
	*/
	Disable(0),
	/** 
	 执行存储过程
	 
	*/
	SP(1),
	/** 
	 运行SQL
	 
	*/
	SQL(2),
	/** 
	 自定义URL
	 
	*/
	URLOfSelf(3),
	/** 
	 自定义WS
	 
	*/
	WSOfSelf(4),
	/** 
	 EXE
	 
	*/
	EXE(5),
	/** 
	 基类
	 
	*/
	EventBase(6),
	/** 
	 JS
	 
	*/
	Javascript(7);

	private int intValue;
	private static java.util.HashMap<Integer, EventDoType> mappings;
	private synchronized static java.util.HashMap<Integer, EventDoType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, EventDoType>();
		}
		return mappings;
	}

	private EventDoType(int value)
	{
		intValue = value;
		EventDoType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static EventDoType forValue(int value)
	{
		return getMappings().get(value);
	}
}