package BP.WF.Template.PubLib;

/** 
 导入流程的模式
 
*/
public enum ImpFlowTempleteModel
{
	/** 
	 按新的流程导入
	 
	*/
	AsNewFlow,
	/** 
	 按模版的流程编号
	 
	*/
	AsTempleteFlowNo,
	/** 
	 按模版的流程编号如果存在该编号就覆盖它
	 
	*/
	AsTempleteFlowNoOvrewaiteWhenExit,
	/** 
	 按指定的流程编号导入
	 
	*/
	AsSpecFlowNo;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ImpFlowTempleteModel forValue(int value)
	{
		return values()[value];
	}
}