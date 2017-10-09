package BP.Sys.Frm;


/** 
 事件标记列表
 
*/
public class EventListOfNode extends FrmEventList
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 节点事件
	/** 
	 节点发送前
	 
	*/
	public static final String SendWhen = "SendWhen";
	/** 
	 节点发送成功后
	 
	*/
	public static final String SendSuccess = "SendSuccess";
	/** 
	 节点发送失败后
	 
	*/
	public static final String SendError = "SendError";
	/** 
	 当节点退回前
	 
	*/
	public static final String ReturnBefore = "ReturnBefore";
	/** 
	 当节点退后
	 
	*/
	public static final String ReturnAfter = "ReturnAfter";
	/** 
	 当节点撤销发送前
	 
	*/
	public static final String UndoneBefore = "UndoneBefore";
	/** 
	 当节点撤销发送后
	 
	*/
	public static final String UndoneAfter = "UndoneAfter";
	/** 
	 当前节点移交后
	 
	*/
	public static final String ShitAfter = "ShitAfter";
	/** 
	 当节点加签后
	 
	*/
	public static final String AskerAfter = "AskerAfter";
   /** 
	当节点加签答复后
	
   */
	public static final String AskerReAfter = "AskerReAfter";
	public static final String QueueSendAfter="QueueSendAfter";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 节点事件

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 流程事件
	/** 
	 流程完成时.
	 
	*/
	public static final String FlowOverBefore = "FlowOverBefore";
	/** 
	 结束后.
	 
	*/
	public static final String FlowOverAfter = "FlowOverAfter";
	/** 
	 流程删除前
	 
	*/
	public static final String BeforeFlowDel = "BeforeFlowDel";
	/** 
	 流程删除后
	 
	*/
	public static final String AfterFlowDel = "AfterFlowDel";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 流程事件
}