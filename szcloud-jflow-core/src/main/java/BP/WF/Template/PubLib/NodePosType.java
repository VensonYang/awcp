package BP.WF.Template.PubLib;

/** 
 位置类型
 
*/
public enum NodePosType
{
	Start,
	Mid,
	End;

	public int getValue()
	{
		return this.ordinal();
	}

	public static NodePosType forValue(int value)
	{
		return values()[value];
	}
}