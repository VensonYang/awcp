package BP.Sys;

import BP.En.EntityTreeAttr;

 /** 
  属性
  
 */
public class DefValAttr extends EntityTreeAttr
{
	/** 
	 属性Key
	 
	*/
	public static final String AttrKey="AttrKey";
	/** 
	 描述
	 
	*/
	public static final String AttrDesc = "AttrDesc";
	/** 
	 工作人员ID
	 
	*/
	public static final String FK_Emp="FK_Emp";
	/** 
	 默认值
	 
	*/
	public static final String Val="Val";
	/** 
	 EnsName
	 
	*/
	public static final String EnsName="EnsName";
	/** 
	 描述
	 
	*/
	public static final String EnsDesc = "EnsDesc";
	/** 
	 父节点编号
	 
	*/
	public static final String ParentNo = "ParentNo";
	/** 
	 是否父节点
	*/
	public static final String IsParent = "IsParent";
	/** 
	 历史词汇----新增  保留以前最后使用的30条词汇
	 
	*/
	public static final String HistoryWords = "HistoryWords";
	
	/** 
	 类别
	 
	*/
	public static final String WordsSort = "WordsSort";
	/** 
	 节点表编号
	 
	*/
	public static final String FK_MapData = "FK_MapData";
	/** 
	 节点对应字段
	 
	*/
	public static final String NodeAttrKey = "NodeAttrKey";
	/** 
	 历史词汇
	 
	*/
	public static final String IsHisWords = "IsHisWords";
	/** 
	 节点文本
	 
	*/
	public static final String CurValue = "CurValue";
}