package BP.WF.Data;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.WF.Template.Node;

/** 
 单据模板
 
*/
public class BillTemplate extends EntityNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region  属性
	/** 
	 单据类型
	 
	*/
	public final String getFK_BillType()
	{
		return this.GetValStringByKey(BillTemplateAttr.FK_BillType);
	}
	public final void setFK_BillType(String value)
	{
		this.SetValByKey(BillTemplateAttr.FK_BillType, value);
	}
	/** 
	 要替换的值
	 
	*/
	public final String getReplaceVal()
	{
		return this.GetValStringByKey(BillTemplateAttr.ReplaceVal);
	}
	public final void setReplaceVal(String value)
	{
		this.SetValByKey(BillTemplateAttr.ReplaceVal, value);
	}
	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	/** 
	 编号
	 
	*/
	public final String getNo()
	{
		String no = this.GetValStrByKey("No");
		no = no.replace("\n", "");
		no = no.replace(" ", "");
		return no;
	}
	public final void setNo(String value)
	{
		this.SetValByKey("No", value);
		this.SetValByKey(BillTemplateAttr.Url, value);
	}
	/** 
	 生成的单据类型
	 
	*/
	public final BillFileType getHisBillFileType()
	{
		return BillFileType.forValue(this.GetValIntByKey(BillTemplateAttr.BillFileType));
	}
	public final void setHisBillFileType(BillFileType value)
	{
		this.SetValByKey(BillTemplateAttr.BillFileType, value.getValue());
	}
	/** 
	 打开的连接
	 
	*/
	public final String getUrl()
	{
		String s= this.GetValStrByKey(BillTemplateAttr.Url);
		if (s.equals("") || s == null)
		{
			return this.getNo();
		}
		return s;
	}
	public final void setUrl(String value)
	{
		this.SetValByKey(BillTemplateAttr.Url, value);
	}
	/** 
	 节点名称
	 
	*/
	public final String getNodeName()
	{
		Node nd = new Node(this.getNodeID());
		return nd.getName();
	}
	/** 
	 节点ID
	 
	*/
	public final int getNodeID()
	{
		return this.GetValIntByKey(BillTemplateAttr.NodeID);
	}
	public final void setNodeID(int value)
	{
		this.SetValByKey(BillTemplateAttr.NodeID, value);
	}
	/** 
	 是否需要送达
	 
	*/
	public final boolean getIsNeedSend_del()
	{
		return this.GetValBooleanByKey(BillTemplateAttr.IsNeedSend);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造函数
	/** 
	 单据模板
	 
	*/
	public BillTemplate()
	{
	}
	public BillTemplate(String no)
	{
		super(no.replace("\n","").trim());
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
		Map map = new Map("WF_BillTemplate");
		map.setEnDesc("单据模板"); // "单据模板";
		map.setEnType(EnType.Admin);
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setCodeStruct("6");

		map.AddTBStringPK(BillTemplateAttr.No, null, "No", true, false, 1, 300, 6);
		map.AddTBString(BillTemplateAttr.Name, null, "Name", true, false, 0, 200, 20);
		map.AddTBString(BillTemplateAttr.Url, null, "URL", true, false, 0, 200, 20);
		map.AddTBInt(BillTemplateAttr.NodeID, 0, "NodeID", true, false);

		map.AddDDLSysEnum(BillTemplateAttr.BillFileType, 0, "生成的文件类型", true, false, "BillFileType","@0=Word@1=PDF@2=Excel(未完成)@3=Html(未完成)@5=锐浪报表");

		map.AddTBString(BillTemplateAttr.FK_BillType, null, "单据类型", true, false, 0, 4, 4);

		map.AddTBString("IDX", null, "IDX", false, false, 0, 200, 20);
		map.AddTBString(BillTemplateAttr.ExpField, null, "要排除的字段", false, false, 0, 800, 20);
		map.AddTBString(BillTemplateAttr.ReplaceVal, null, "要替换的值", false, false, 0, 3000, 20);
		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}