package BP.WF.Template.PubLib;

///// <summary>
///// 流程节点类型
///// </summary>
//public enum FNType
//{
//    /// <summary>
//    /// 平面节点
//    /// </summary>
//    Plane = 0,
//    /// <summary>
//    /// 分合流
//    /// </summary>
//    River = 1,
//    /// <summary>
//    /// 支流
//    /// </summary>
//    Branch = 2
//}
/** 
 谁执行它
 
*/
public enum CCRole
{
	/** 
	 不能抄送
	 
	*/
	UnCC,
	/** 
	 手工抄送
	 
	*/
	HandCC,
	/** 
	 自动抄送
	 
	*/
	AutoCC,
	/** 
	 手工与自动并存
	 
	*/
	HandAndAuto,
	/** 
	 按字段
	 
	*/
	BySysCCEmps,
	/** 
	 在发送前打开
	 
	*/
	WhenSend;

	public int getValue()
	{
		return this.ordinal();
	}

	public static CCRole forValue(int value)
	{
		return values()[value];
	}
}