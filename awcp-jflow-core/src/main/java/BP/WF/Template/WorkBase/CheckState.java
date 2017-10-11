package BP.WF.Template.WorkBase;

import BP.DA.*;
import BP.WF.*;
import BP.En.*;

/** 
 审核状态
 
*/
public enum CheckState
{
	/** 
	 暂停
	 
	*/
	Pause(2),
	/** 
	 同意
	 
	*/
	Agree(1),
	/** 
	 不同意
	 
	*/
	Dissent(0);

	private int intValue;
	private static java.util.HashMap<Integer, CheckState> mappings;
	private synchronized static java.util.HashMap<Integer, CheckState> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, CheckState>();
		}
		return mappings;
	}

	private CheckState(int value)
	{
		intValue = value;
		CheckState.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static CheckState forValue(int value)
	{
		return getMappings().get(value);
	}
}