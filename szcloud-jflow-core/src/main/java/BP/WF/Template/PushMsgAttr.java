package BP.WF.Template;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;
import BP.Sys.*;

/** 
 消息推送属性
 
*/
public class PushMsgAttr
{
	/** 
	 节点
	 
	*/
	public static final String FK_Node = "FK_Node";
	/** 
	 事件
	 
	*/
	public static final String FK_Event = "FK_Event";
	/** 
	 推送方式
	 
	*/
	public static final String PushWay = "PushWay";
	/** 
	 推送处理内容
	 
	*/
	public static final String PushDoc = "PushDoc";
	/** 
	 推送处理内容 tag.
	 
	*/
	public static final String Tag = "Tag";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 消息设置.
	/** 
	 是否启用发送邮件
	 
	*/
	public static final String MsgMailEnable = "MsgMailEnable";
	/** 
	 消息标题
	 
	*/
	public static final String MailTitle = "MailTitle";
	/** 
	 消息内容模版
	 
	*/
	public static final String MailDoc = "MailDoc";
	/** 
	 是否启用短信
	 
	*/
	public static final String SMSEnable = "SMSEnable";
	/** 
	 短信内容模版
	 
	*/
	public static final String SMSDoc = "SMSDoc";
	/** 
	 是否推送？
	 
	*/
	public static final String MobilePushEnable = "MobilePushEnable";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 消息设置.

}