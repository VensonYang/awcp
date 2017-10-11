package BP.Sys.Frm;

import BP.En.EntityNoNameAttr;

/** 
 映射基础
 
*/
public class MapDataAttr extends EntityNoNameAttr
{
	public static final String PTable = "PTable";
	public static final String Dtls = "Dtls";
	public static final String EnPK = "EnPK";
	public static final String FrmW = "FrmW";
	public static final String FrmH = "FrmH";
	/** 
	 表格列(对傻瓜表单有效)
	 
	*/
	public static final String TableCol = "TableCol";
	/** 
	 表格宽度
	 
	*/
	public static final String TableWidth = "TableWidth";
	/** 
	 来源
	 
	*/
	public static final String FrmFrom = "FrmFrom";
	/** 
	 DBURL
	 
	*/
	public static final String DBURL = "DBURL";
	/** 
	 设计者
	 
	*/
	public static final String Designer = "Designer";
	/** 
	 设计者单位
	 
	*/
	public static final String DesignerUnit = "DesignerUnit";
	/** 
	 设计者联系方式
	 
	*/
	public static final String DesignerContact = "DesignerContact";
	/** 
	 表单类别
	 
	*/
	public static final String FK_FrmSort = "FK_FrmSort";
	/** 
	 表单树类别
	 
	*/
	public static final String FK_FormTree = "FK_FormTree";
	/** 
	 应用类型
	 
	*/
	public static final String AppType = "AppType";
	/** 
	 表单类型
	 
	*/
	public static final String FrmType = "FrmType";
	/** 
	 Url(对于自定义表单有效)
	 
	*/
	public static final String Url = "Url";
	/** 
	 Tag
	 
	*/
	public static final String Tag = "Tag";
	/** 
	 备注
	 
	*/
	public static final String Note = "Note";
	/** 
	 Idx
	 
	*/
	public static final String Idx = "Idx";
	/** 
	 GUID
	 
	*/
	public static final String GUID = "GUID";
	/** 
	 版本号
	 
	*/
	public static final String Ver = "Ver";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 报表属性(参数的方式存储).
	/** 
	 是否关键字查询
	 
	*/
	public static final String RptIsSearchKey = "RptIsSearchKey";
	/** 
	 时间段查询方式
	 
	*/
	public static final String RptDTSearchWay = "RptDTSearchWay";
	/** 
	 时间字段
	 
	*/
	public static final String RptDTSearchKey = "RptDTSearchKey";
	/** 
	 查询外键枚举字段
	 
	*/
	public static final String RptSearchKeys = "RptSearchKeys";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 报表属性(参数的方式存储).

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 其他计算属性，参数存储.
	/** 
	 最左边的值
	 
	*/
	public static final String MaxLeft = "MaxLeft";
	/** 
	 最右边的值
	 
	*/
	public static final String MaxRight = "MaxRight";
	/** 
	 最头部的值
	 
	*/
	public static final String MaxTop = "MaxTop";
	/** 
	 最底部的值
	 
	*/
	public static final String MaxEnd = "MaxEnd";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 其他计算属性，参数存储.
}