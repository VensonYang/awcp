package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.Attr;
import BP.En.Entity;
import BP.En.EntityMyPK;
import BP.En.Map;
import BP.En.UAC;
import BP.Tools.StringHelper;

/** 
 事件
 节点的节点保存事件有两部分组成.	 
 记录了从一个节点到其他的多个节点.
 也记录了到这个节点的其他的节点.
 
*/
public class FrmEvent extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.IsAdjunct = false;
		uac.IsDelete = false;
		uac.IsInsert = false;
		uac.IsUpdate = true;
		return uac;
	}
	/** 
	 节点
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStringByKey(FrmEventAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmEventAttr.FK_MapData, value);
	}
	public final String getDoDoc()
	{
		return this.GetValStringByKey(FrmEventAttr.DoDoc).replace("~", "'");
	}
	public final void setDoDoc(String value)
	{
		String doc = value.replace("'", "~");
		this.SetValByKey(FrmEventAttr.DoDoc, doc);
	}
	/** 
	 执行成功提示
	 
	*/
	public final String MsgOK(Entity en)
	{
		String val = this.GetValStringByKey(FrmEventAttr.MsgOK);
		if (val.trim().equals(""))
		{
			return "";
		}

		if (val.indexOf('@') == -1)
		{
			return val;
		}

		for (Attr attr : en.getEnMap().getAttrs())
		{
			val = val.replace("@" + attr.getKey(), en.GetValStringByKey(attr.getKey()));
		}
		return val;
	}
	public final String getMsgOKString()
	{
		return this.GetValStringByKey(FrmEventAttr.MsgOK);
	}
	public final void setMsgOKString(String value)
	{
		this.SetValByKey(FrmEventAttr.MsgOK, value);
	}
	public final String getMsgErrorString()
	{
		return this.GetValStringByKey(FrmEventAttr.MsgError);
	}
	public final void setMsgErrorString(String value)
	{
		this.SetValByKey(FrmEventAttr.MsgError, value);
	}
	/** 
	 错误或异常提示
	 
	 @param en
	 @return 
	*/
	public final String MsgError(Entity en)
	{
		String val = this.GetValStringByKey(FrmEventAttr.MsgError);
		if (val.trim().equals(""))
		{
			return null;
		}

		if (val.indexOf('@') == -1)
		{
			return val;
		}

		for (Attr attr : en.getEnMap().getAttrs())
		{
			val = val.replace("@" + attr.getKey(), en.GetValStringByKey(attr.getKey()));
		}
		return val;
	}

	public final String getFK_Event()
	{
		return this.GetValStringByKey(FrmEventAttr.FK_Event);
	}
	public final void setFK_Event(String value)
	{
		this.SetValByKey(FrmEventAttr.FK_Event, value);
	}
	/** 
	 执行类型
	 
	*/
	public final EventDoType getHisDoType()
	{
		return EventDoType.forValue(this.GetValIntByKey(FrmEventAttr.DoType));
	}
	public final void setHisDoType(EventDoType value)
	{
		this.SetValByKey(FrmEventAttr.DoType, value.getValue());
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 事件消息.
	/** 
	 消息控制类型.
	 
	*/
	public final MsgCtrl getMsgCtrl()
	{
		return MsgCtrl.forValue(this.GetValIntByKey(FrmEventAttr.MsgCtrl));
	}
	public final void setMsgCtrl(MsgCtrl value)
	{
		this.SetValByKey(FrmEventAttr.MsgCtrl, value.getValue());
	}
	public final boolean getMobilePushEnable()
	{
		return this.GetValBooleanByKey(FrmEventAttr.MobilePushEnable);
	}
	public final void setMobilePushEnable(boolean value)
	{
		this.SetValByKey(FrmEventAttr.MobilePushEnable, value);
	}
	/** 
	 是否启用邮件发送?
	 
	*/
	public final boolean getMsgMailEnable()
	{
		return this.GetValBooleanByKey(FrmEventAttr.MsgMailEnable);
	}
	public final void setMsgMailEnable(boolean value)
	{
		this.SetValByKey(FrmEventAttr.MsgMailEnable, value);
	}
	public final String getMailTitle()
	{
		String str = this.GetValStrByKey(FrmEventAttr.MailTitle);
		if (!StringHelper.isNullOrEmpty(str) )
		{
			return str;
		}
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (this.FK_Event)
//ORIGINAL LINE: case EventListOfNode.SendSuccess:
		if (this.getFK_Event().equals(EventListOfNode.SendSuccess))
		{
				return "新工作{{Title}},发送人@WebUser.No,@WebUser.Name";
		}
//ORIGINAL LINE: case EventListOfNode.ShitAfter:
		else if (this.getFK_Event().equals(EventListOfNode.ShitAfter))
		{
				return "移交来的新工作{{Title}},移交人@WebUser.No,@WebUser.Name";
		}
//ORIGINAL LINE: case EventListOfNode.ReturnAfter:
		else if (this.getFK_Event().equals(EventListOfNode.ReturnAfter))
		{
				return "被退回来{{Title}},退回人@WebUser.No,@WebUser.Name";
		}
//ORIGINAL LINE: case EventListOfNode.UndoneAfter:
		else if (this.getFK_Event().equals(EventListOfNode.UndoneAfter))
		{
				return "工作被撤销{{Title}},发送人@WebUser.No,@WebUser.Name";
		}
//ORIGINAL LINE: case EventListOfNode.AskerReAfter:
		else if (this.getFK_Event().equals(EventListOfNode.AskerReAfter))
		{
				return "加签新工作{{Title}},发送人@WebUser.No,@WebUser.Name";
		}
		else
		{
				throw new RuntimeException("@该事件类型没有定义默认的消息模版:" + this.getFK_Event());
		}
		/*
		 * warning return str;*/
	}
	/** 
	 邮件标题
	 
	*/
	public final String getMailTitle_Real()
	{
		String str = this.GetValStrByKey(FrmEventAttr.MailTitle);
		return str;
	}
	public final void setMailTitle_Real(String value)
	{
		this.SetValByKey(FrmEventAttr.MailTitle, value);
	}
	/** 
	 邮件内容
	 
	*/
	public final String getMailDoc_Real()
	{
		return this.GetValStrByKey(FrmEventAttr.MailDoc);
	}
	public final void setMailDoc_Real(String value)
	{
		this.SetValByKey(FrmEventAttr.MailDoc, value);
	}
	public final String getMailDoc()
	{
		String str = this.GetValStrByKey(FrmEventAttr.MailDoc);
		if (!StringHelper.isNullOrEmpty(str) )
		{
			return str;
		}
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (this.FK_Event)
//ORIGINAL LINE: case EventListOfNode.SendSuccess:
		if (this.getFK_Event().equals(EventListOfNode.SendSuccess))
		{
				str += "\t\n您好:";
				str += "\t\n    有新工作{{Title}}需要您处理, 点击这里打开工作{Url} .";
				str += "\t\n致! ";
				str += "\t\n    @WebUser.No, @WebUser.Name";
				str += "\t\n    @RDT";
		}
//ORIGINAL LINE: case EventListOfNode.ReturnAfter:
		else if (this.getFK_Event().equals(EventListOfNode.ReturnAfter))
		{
				str += "\t\n您好:";
				str += "\t\n    工作{{Title}}被退回来了, 点击这里打开工作{Url} .";
				str += "\t\n 致! ";
				str += "\t\n    @WebUser.No,@WebUser.Name";
				str += "\t\n    @RDT";
		}
//ORIGINAL LINE: case EventListOfNode.ShitAfter:
		else if (this.getFK_Event().equals(EventListOfNode.ShitAfter))
		{
				str += "\t\n您好:";
				str += "\t\n    移交给您的工作{{Title}}, 点击这里打开工作{Url} .";
				str += "\t\n 致! ";
				str += "\t\n    @WebUser.No,@WebUser.Name";
				str += "\t\n    @RDT";
		}
//ORIGINAL LINE: case EventListOfNode.UndoneAfter:
		else if (this.getFK_Event().equals(EventListOfNode.UndoneAfter))
		{
				 str += "\t\n您好:";
				 str += "\t\n    移交给您的工作{{Title}}, 点击这里打开工作{Url} .";
				str += "\t\n 致! ";
				str += "\t\n    @WebUser.No,@WebUser.Name";
				str += "\t\n    @RDT";
		}
//ORIGINAL LINE: case EventListOfNode.AskerReAfter:
		else if (this.getFK_Event().equals(EventListOfNode.AskerReAfter)) //加签.
		{
				str += "\t\n您好:";
				str += "\t\n    移交给您的工作{{Title}}, 点击这里打开工作{Url} .";
				str += "\t\n 致! ";
				str += "\t\n    @WebUser.No,@WebUser.Name";
				str += "\t\n    @RDT";
		}
		else
		{
				throw new RuntimeException("@该事件类型没有定义默认的消息模版:" + this.getFK_Event());
		}
		return str;
	}
	/** 
	 是否启用短信发送
	 
	*/
	public final boolean getSMSEnable()
	{
		return this.GetValBooleanByKey(FrmEventAttr.SMSEnable);
	}
	public final void setSMSEnable(boolean value)
	{
		this.SetValByKey(FrmEventAttr.SMSEnable, value);
	}
	/** 
	 短信模版内容
	 
	*/
	public final String getSMSDoc_Real()
	{
		String str = this.GetValStrByKey(FrmEventAttr.SMSDoc);
		return str;
	}
	public final void setSMSDoc_Real(String value)
	{
		this.SetValByKey(FrmEventAttr.SMSDoc, value);
	}
	/** 
	 短信模版内容
	 
	*/
	public final String getSMSDoc()
	{
		String str = this.GetValStrByKey(FrmEventAttr.SMSDoc);
		if (!StringHelper.isNullOrEmpty(str) )
		{
			return str;
		}

//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (this.FK_Event)
//ORIGINAL LINE: case EventListOfNode.SendSuccess:
		if (this.getFK_Event().equals(EventListOfNode.SendSuccess))
		{
				str = "有新工作{{Title}}需要您处理, 发送人:@WebUser.No, @WebUser.Name,打开{Url} .";
		}
//ORIGINAL LINE: case EventListOfNode.ReturnAfter:
		else if (this.getFK_Event().equals(EventListOfNode.ReturnAfter))
		{
				str = "工作{{Title}}被退回,退回人:@WebUser.No, @WebUser.Name,打开{Url} .";
		}
//ORIGINAL LINE: case EventListOfNode.ShitAfter:
		else if (this.getFK_Event().equals(EventListOfNode.ShitAfter))
		{
				str = "移交工作{{Title}},移交人:@WebUser.No, @WebUser.Name,打开{Url} .";
		}
//ORIGINAL LINE: case EventListOfNode.UndoneAfter:
		else if (this.getFK_Event().equals(EventListOfNode.UndoneAfter))
		{
				str = "工作撤销{{Title}},撤销人:@WebUser.No, @WebUser.Name,打开{Url}.";
		}
//ORIGINAL LINE: case EventListOfNode.AskerReAfter:
		else if (this.getFK_Event().equals(EventListOfNode.AskerReAfter)) //加签.
		{
				str = "工作加签{{Title}},加签人:@WebUser.No, @WebUser.Name,打开{Url}.";
		}
		else
		{
				throw new RuntimeException("@该事件类型没有定义默认的消息模版:" + this.getFK_Event());
		}
		return str;
	}
	public final void setSMSDoc(String value)
	{
		this.SetValByKey(FrmEventAttr.SMSDoc, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 事件
	 
	*/
	public FrmEvent()
	{
	}
	public FrmEvent(String mypk)
	{
		this.setMyPK(mypk);
		this.RetrieveFromDBSources();
	}
	public FrmEvent(String fk_mapdata, String fk_Event)
	{
		this.setFK_Event(fk_Event);
		this.setFK_MapData(fk_mapdata);
		this.setMyPK(this.getFK_MapData() + "_" + this.getFK_Event());
		this.RetrieveFromDBSources();
	}
	/** 
	 重写基类方法
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("Sys_FrmEvent");
		map.setEnDesc("事件");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.AddMyPK();

		map.AddTBString(FrmEventAttr.FK_Event, null, "事件名称", true, true, 0, 400, 10);
		map.AddTBString(FrmEventAttr.FK_MapData, null, "FK_MapData", true, true, 0, 400, 10);

		map.AddTBInt(FrmEventAttr.DoType, 0, "事件类型", true, true);
		map.AddTBString(FrmEventAttr.DoDoc, null, "执行内容", true, true, 0, 400, 10);
		map.AddTBString(FrmEventAttr.MsgOK, null, "成功执行提示", true, true, 0, 400, 10);
		map.AddTBString(FrmEventAttr.MsgError, null, "异常信息提示", true, true, 0, 400, 10);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region 消息设置.
		map.AddDDLSysEnum(FrmEventAttr.MsgCtrl, 0, "消息发送控制", true, true, FrmEventAttr.MsgCtrl, "@0=不发送@1=按设置的发送范围自动发送@2=由本节点表单系统字段(IsSendEmail,IsSendSMS)来决定@3=由SDK开发者参数(IsSendEmail,IsSendSMS)来决定", true);

		map.AddBoolean(FrmEventAttr.MsgMailEnable, true, "是否启用邮件发送？(如果启用就要设置邮件模版，支持ccflow表达式。)", true, true, true);
		map.AddTBString(FrmEventAttr.MailTitle, null, "邮件标题模版", true, false, 0, 200, 20, true);
		map.AddTBStringDoc(FrmEventAttr.MailDoc, null, "邮件内容模版", true, false, true);

			//是否启用手机短信？
		map.AddBoolean(FrmEventAttr.SMSEnable, false, "是否启用短信发送？(如果启用就要设置短信模版，支持ccflow表达式。)", true, true, true);
		map.AddTBStringDoc(FrmEventAttr.SMSDoc, null, "短信内容模版", true, false, true);

		map.AddBoolean(FrmEventAttr.MobilePushEnable, true, "是否推送到手机、pad端。", true, true, true);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion 消息设置.


		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		this.setMyPK(this.getFK_MapData() + "_" + this.getFK_Event());
		return super.beforeUpdateInsertAction();
	}
}