package BP.Sys;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityTree;
import BP.En.Map;

/** 
 默认值
 
*/
public class DefVal extends EntityTree
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	/** 
	 类名
	 
	*/
	public final String getEnsName()
	{
		return this.GetValStringByKey(DefValAttr.EnsName);
	}
	public final void setEnsName(String value)
	{
		this.SetValByKey(DefValAttr.EnsName, value);
	}
	/** 
	 描述
	 
	*/
	public final String getEnsDesc()
	{
		return this.GetValStringByKey(DefValAttr.EnsDesc);
	}
	public final void setEnsDesc(String value)
	{
		this.SetValByKey(DefValAttr.EnsDesc, value);
	}
	/** 
	 默认值
	 
	*/
	public final String getVal()
	{
		return this.GetValStringByKey(DefValAttr.Val);
	}
	public final void setVal(String value)
	{
		this.SetValByKey(DefValAttr.Val, value);
	}
	/** 
	 操作员ID
	 
	*/
	public final String getFK_Emp()
	{
		return this.GetValStringByKey(DefValAttr.FK_Emp);
	}
	public final void setFK_Emp(String value)
	{
		this.SetValByKey(DefValAttr.FK_Emp, value);
	}
	/** 
	 属性
	 
	*/
	public final String getAttrKey()
	{
		return this.GetValStringByKey(DefValAttr.AttrKey);
	}
	public final void setAttrKey(String value)
	{
		this.SetValByKey(DefValAttr.AttrKey, value);
	}
	/** 
	 属性描述
	 
	*/
	public final String getAttrDesc()
	{
		return this.GetValStringByKey(DefValAttr.AttrDesc);
	}
	public final void setAttrDesc(String value)
	{
		this.SetValByKey(DefValAttr.AttrDesc, value);
	}
	/** 
	 是否父节点
	 
	*/
	public final String getIsParent()
	{
		return this.GetValStringByKey(DefValAttr.IsParent);
	}
	public final void setIsParent(String value)
	{
		this.SetValByKey(DefValAttr.IsParent, value);
	}
	 /** 
	 历史词汇
	 
	 */
	public final String getHistoryWords()
	{
		return this.GetValStringByKey(DefValAttr.HistoryWords);
	}
	public final void setHistoryWords(String value)
	{
		this.SetValByKey(DefValAttr.HistoryWords, value);
	}
	
	/** 
	 词汇类别
	 
	*/
	public final String getWordsSort()
	{
		return this.GetValStringByKey(DefValAttr.WordsSort);
	}
	public final void setWordsSort(String value)
	{
		this.SetValByKey(DefValAttr.WordsSort, value);
	}
	/** 
	 节点编号
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStringByKey(DefValAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(DefValAttr.FK_MapData, value);
	}
	/** 
	 节点对应字段
	 
	*/
	public final String getNodeAttrKey()
	{
		return this.GetValStringByKey(DefValAttr.NodeAttrKey);
	}
	public final void setNodeAttrKey(String value)
	{
		this.SetValByKey(DefValAttr.NodeAttrKey, value);
	}
	/** 
	 是否历史词汇
	 
	*/
	public final String getIsHisWords()
	{
		return this.GetValStringByKey(DefValAttr.IsHisWords);
	}
	public final void setIsHisWords(String value)
	{
		this.SetValByKey(DefValAttr.IsHisWords, value);
	}
	 
	/** 
	 节点文本
	 
	*/
	public final String getCurValue()
	{
		return this.GetValStringByKey(DefValAttr.CurValue);
	}
	public final void setCurValue(String value)
	{
		this.SetValByKey(DefValAttr.CurValue, value);
	}

	/** 
	 默认值
	 
	*/
	public DefVal()
	{
	}
	/** 
	 map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Sys_DefVal");
		map.setEnType(EnType.Sys);
		map.setEnDesc("默认值");
		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);

		map.AddTBStringPK(DefValAttr.No, null, "编号", true, true, 1, 50, 20);
		map.AddTBString(DefValAttr.EnsName, null, "类名称", false, true, 0, 100, 10);
		map.AddTBString(DefValAttr.EnsDesc, null, "类描述", false, true, 0, 100, 10);

		map.AddTBString(DefValAttr.AttrKey, null, "属性", false, true, 0, 100, 10);
		map.AddTBString(DefValAttr.AttrDesc, null, "属性描述", false, false, 0, 100, 10);

		map.AddTBString(DefValAttr.FK_Emp, null, "人员", false, true, 0, 100, 10);
		map.AddTBString(DefValAttr.Val, null, "值", true, false, 0, 1000, 10);
		map.AddTBString(DefValAttr.ParentNo, null, "父节点编号", false, false, 0, 50, 20);
		map.AddTBInt(DefValAttr.IsParent, 0, "是否父节点", false, false);

		   //map.AddTBInt(DefValAttr.IsParent,0,"是否父节点";

		map.AddTBString(DefValAttr.HistoryWords, null, "历史词汇", false, false, 0, 2000, 20);
		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}