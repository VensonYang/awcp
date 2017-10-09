package BP.WF.Template.PubLib;

public enum DraftRole
{
	/** 
	 按节点
	 
	*/
	None,
	/** 
	 保存到待办
	 
	*/
	SaveToTodolist,
	/** 
	 保存到草稿箱
	 
	*/
	SaveToDraftList;

	public int getValue()
	{
		return this.ordinal();
	}

	public static DraftRole forValue(int value)
	{
		return values()[value];
	}
}