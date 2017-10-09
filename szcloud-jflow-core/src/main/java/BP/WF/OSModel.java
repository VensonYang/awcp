package BP.WF;

/** 
 组织结构模式
 
*/
public enum OSModel
{
	WorkFlow(0),
	BPM(1);

	private int intValue;
	private static java.util.HashMap<Integer, OSModel> mappings;
	private synchronized static java.util.HashMap<Integer, OSModel> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, OSModel>();
		}
		return mappings;
	}

	private OSModel(int value)
	{
		intValue = value;
		OSModel.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static OSModel forValue(int value)
	{
		return getMappings().get(value);
	}
}