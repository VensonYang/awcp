package BP.Sys.Frm;

import BP.DA.DBAccess;
import BP.DA.Depositary;
import BP.En.Attrs;
import BP.En.EditType;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.FieldTypeS;
import BP.En.Map;
import BP.En.UIContralType;
import BP.Sys.GEDtl;
import BP.Sys.GEDtls;
import BP.Sys.GEEntity;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;

/** 
 明细
 
*/
public class MapDtl extends EntityNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 导入导出属性.
	/** 
	 是否可以导出
	 
	*/
	public final boolean getIsExp()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsExp);
	}
	public final void setIsExp(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsExp, value);
	}
	/** 
	 是否可以导入？
	 
	*/
	public final boolean getIsImp()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsImp);
	}
	public final void setIsImp(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsImp, value);
	}
	/** 
	 是否启用选择数据项目导入？
	 
	*/
	public final boolean getIsEnableSelectImp()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsEnableSelectImp);
	}
	public final void setIsEnableSelectImp(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsEnableSelectImp, value);
	}
	/** 
	 查询sql
	 
	*/
	public final String getImpSQLInit()
	{
		return this.GetValStringByKey(MapDtlAttr.ImpSQLInit).replace("~", "'");
	}
	public final void setImpSQLInit(String value)
	{
		this.SetValByKey(MapDtlAttr.ImpSQLInit, value);
	}
	/** 
	 搜索sql
	 
	*/
	public final String getImpSQLSearch()
	{
		return this.GetValStringByKey(MapDtlAttr.ImpSQLSearch).replace("~", "'");
	}
	public final void setImpSQLSearch(String value)
	{
		this.SetValByKey(MapDtlAttr.ImpSQLSearch, value);
	}
	/** 
	 填充数据
	 
	*/
	public final String getImpSQLFull()
	{
		return this.GetValStringByKey(MapDtlAttr.ImpSQLFull).replace("~","'");
	}
	public final void setImpSQLFull(String value)
	{
		this.SetValByKey(MapDtlAttr.ImpSQLFull, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本设置

	public final String getImpFixDataSql()
	{
		return this.GetValStringByKey(MapDtlAttr.ImpFixDataSql);
	}
	public final void setImpFixDataSql(String value)
	{
		this.SetValByKey(MapDtlAttr.ImpFixDataSql, value);
	}


	/** 
	 填充属性sql
	 
	*/

	public final String getImpFixTreeSql()
	{
		return this.GetValStringByKey(MapDtlAttr.ImpFixTreeSql);
	}
	public final void setImpFixTreeSql(String value)
	{
		this.SetValByKey(MapDtlAttr.ImpFixTreeSql, value);
	}

	/** 
	 工作模式
	 
	*/
	public final DtlModel getDtlModel()
	{
		return DtlModel.forValue(this.GetValIntByKey(MapDtlAttr.Model));
	}
	public final void setDtlModel(DtlModel value)
	{
		this.SetValByKey(MapDtlAttr.Model, value.getValue());
	}
	/** 
	 是否启用行锁定.
	 
	*/
	public final boolean getIsRowLock()
	{
		return this.GetParaBoolen(MapDtlAttr.IsRowLock, false);
	}
	public final void setIsRowLock(boolean value)
	{
		this.SetPara(MapDtlAttr.IsRowLock, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 基本设置

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 参数属性
	public final boolean getIsEnableLink()
	{
		return this.GetParaBoolen(MapDtlAttr.IsEnableLink, false);
	}
	public final void setIsEnableLink(boolean value)
	{
		this.SetPara(MapDtlAttr.IsEnableLink, value);
	}
	public final String getLinkLabel()
	{
		String s= this.GetParaString(MapDtlAttr.LinkLabel);
		if (StringHelper.isNullOrEmpty(s))
		{
			return "详细";
		}
		return s;
	}
	public final void setLinkLabel(String value)
	{
		this.SetPara(MapDtlAttr.LinkLabel, value);
	}
	public final String getLinkUrl()
	{
		String s = this.GetParaString(MapDtlAttr.LinkUrl);
		if (StringHelper.isNullOrEmpty(s))
		{
			return "http://ccport.org";
		}

		s = s.replace("*", "@");
		return s;
	}
	public final void setLinkUrl(String value)
	{
		String val = value;
		val = val.replace("@", "*");
		this.SetPara(MapDtlAttr.LinkUrl, val);
	}
	public final String getLinkTarget()
	{
		String s = this.GetParaString(MapDtlAttr.LinkTarget);
		if (StringHelper.isNullOrEmpty(s))
		{
			return "_blank";
		}
		return s;
	}
	public final void setLinkTarget(String value)
	{
		this.SetPara(MapDtlAttr.LinkTarget, value);
	}
	/** 
	 节点ID
	 
	*/
	public final int getFK_Node()
	{
		return this.GetValIntByKey(MapDtlAttr.FK_Node);
	}
	public final void setFK_Node(int value)
	{
		this.SetValByKey(MapDtlAttr.FK_Node, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 参数属性

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 外键属性
	/** 
	 框架
	 
	*/
	public final MapFrames getMapFrames()
	{
		Object tempVar = this.GetRefObject("MapFrames");
		MapFrames obj = (MapFrames)((tempVar instanceof MapFrames) ? tempVar : null);
		if (obj == null)
		{
			obj = new MapFrames(this.getNo());
			this.SetRefObject("MapFrames", obj);
		}
		return obj;
	}
	/** 
	 分组字段
	 
	*/
	public final GroupFields getGroupFields_del()
	{
		Object tempVar = this.GetRefObject("GroupFields");
		GroupFields obj = (GroupFields)((tempVar instanceof GroupFields) ? tempVar : null);
		if (obj == null)
		{
			obj = new GroupFields(this.getNo());
			this.SetRefObject("GroupFields", obj);
		}
		return obj;
	}
	/** 
	 逻辑扩展
	 
	*/
	public final MapExts getMapExts()
	{
		Object tempVar = this.GetRefObject("MapExts");
		MapExts obj = (MapExts)((tempVar instanceof MapExts) ? tempVar : null);
		if (obj == null)
		{
			obj = new MapExts(this.getNo());
			this.SetRefObject("MapExts", obj);
		}
		return obj;
	}
	/** 
	 事件
	 
	*/
	public final FrmEvents getFrmEvents()
	{
		Object tempVar = this.GetRefObject("FrmEvents");
		FrmEvents obj = (FrmEvents)((tempVar instanceof FrmEvents) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmEvents(this.getNo());
			this.SetRefObject("FrmEvents", obj);
		}
		return obj;
	}
	/** 
	 一对多
	 
	*/
	public final MapM2Ms getMapM2Ms()
	{
		Object tempVar = this.GetRefObject("MapM2Ms");
		MapM2Ms obj = (MapM2Ms)((tempVar instanceof MapM2Ms) ? tempVar : null);
		if (obj == null)
		{
			obj = new MapM2Ms(this.getNo());
			this.SetRefObject("MapM2Ms", obj);
		}
		return obj;
	}
	/** 
	 从表
	 
	*/
	public final MapDtls getMapDtls()
	{
		Object tempVar = this.GetRefObject("MapDtls");
		MapDtls obj = (MapDtls)((tempVar instanceof MapDtls) ? tempVar : null);
		if (obj == null)
		{
			obj = new MapDtls(this.getNo());
			this.SetRefObject("MapDtls", obj);
		}
		return obj;
	}
	/** 
	 超连接
	 
	*/
	public final FrmLinks getFrmLinks()
	{
		Object tempVar = this.GetRefObject("FrmLinks");
		FrmLinks obj = (FrmLinks)((tempVar instanceof FrmLinks) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmLinks(this.getNo());
			this.SetRefObject("FrmLinks", obj);
		}
		return obj;
	}
	/** 
	 按钮
	 
	*/
	public final FrmBtns getFrmBtns()
	{
		Object tempVar = this.GetRefObject("FrmLinks");
		FrmBtns obj = (FrmBtns)((tempVar instanceof FrmBtns) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmBtns(this.getNo());
			this.SetRefObject("FrmBtns", obj);
		}
		return obj;
	}
	/** 
	 元素
	 
	*/
	public final FrmEles getFrmEles()
	{
		Object tempVar = this.GetRefObject("FrmEles");
		FrmEles obj = (FrmEles)((tempVar instanceof FrmEles) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmEles(this.getNo());
			this.SetRefObject("FrmEles", obj);
		}
		return obj;
	}
	/** 
	 线
	 
	*/
	public final FrmLines getFrmLines()
	{
		Object tempVar = this.GetRefObject("FrmLines");
		FrmLines obj = (FrmLines)((tempVar instanceof FrmLines) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmLines(this.getNo());
			this.SetRefObject("FrmLines", obj);
		}
		return obj;
	}
	/** 
	 标签
	 
	*/
	public final FrmLabs getFrmLabs()
	{
		Object tempVar = this.GetRefObject("FrmLabs");
		FrmLabs obj = (FrmLabs)((tempVar instanceof FrmLabs) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmLabs(this.getNo());
			this.SetRefObject("FrmLabs", obj);
		}
		return obj;
	}
	/** 
	 图片
	 
	*/
	public final FrmImgs getFrmImgs()
	{
		Object tempVar = this.GetRefObject("FrmLabs");
		FrmImgs obj = (FrmImgs)((tempVar instanceof FrmImgs) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmImgs(this.getNo());
			this.SetRefObject("FrmLabs", obj);
		}
		return obj;
	}
	/** 
	 附件
	 
	*/
	public final FrmAttachments getFrmAttachments()
	{
		Object tempVar = this.GetRefObject("FrmAttachments");
		FrmAttachments obj = (FrmAttachments)((tempVar instanceof FrmAttachments) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmAttachments(this.getNo());
			this.SetRefObject("FrmAttachments", obj);
		}
		return obj;
	}
	/** 
	 图片附件
	 
	*/
	public final FrmImgAths getFrmImgAths()
	{
		Object tempVar = this.GetRefObject("FrmImgAths");
		FrmImgAths obj = (FrmImgAths)((tempVar instanceof FrmImgAths) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmImgAths(this.getNo());
			this.SetRefObject("FrmImgAths", obj);
		}
		return obj;
	}
	/** 
	 单选按钮
	 
	*/
	public final FrmRBs getFrmRBs()
	{
		Object tempVar = this.GetRefObject("FrmRBs");
		FrmRBs obj = (FrmRBs)((tempVar instanceof FrmRBs) ? tempVar : null);
		if (obj == null)
		{
			obj = new FrmRBs(this.getNo());
			this.SetRefObject("FrmRBs", obj);
		}
		return obj;
	}
	/** 
	 属性
	 
	*/
	public final MapAttrs getMapAttrs()
	{
		Object tempVar = this.GetRefObject("MapAttrs");
		MapAttrs obj = (MapAttrs)((tempVar instanceof MapAttrs) ? tempVar : null);
		if (obj == null)
		{
			obj = new MapAttrs(this.getNo());
			this.SetRefObject("MapAttrs", obj);
		}
		return obj;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public GEDtls HisGEDtls_temp = null;
	public final DtlShowModel getHisDtlShowModel()
	{
		return DtlShowModel.forValue(this.GetValIntByKey(MapDtlAttr.DtlShowModel));
	}
	public final void setHisDtlShowModel(DtlShowModel value)
	{
		this.SetValByKey(MapDtlAttr.DtlShowModel, value.getValue());
	}
	/** 
	 
	 
	*/
	public final WhenOverSize getHisWhenOverSize()
	{
		return WhenOverSize.forValue(this.GetValIntByKey(MapDtlAttr.WhenOverSize));
	}
	public final void setHisWhenOverSize(WhenOverSize value)
	{
		this.SetValByKey(MapDtlAttr.WhenOverSize, value.getValue());
	}

	public final boolean getIsShowSum()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsShowSum);
	}
	public final void setIsShowSum(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsShowSum, value);
	}
	public final boolean getIsShowIdx()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsShowIdx);
	}
	public final void setIsShowIdx(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsShowIdx, value);
	}
	public final boolean getIsReadonly_del()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsReadonly);
	}
	public final void setIsReadonly_del(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsReadonly, value);
	}
	public final boolean getIsShowTitle()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsShowTitle);
	}
	public final void setIsShowTitle(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsShowTitle, value);
	}
	/** 
	 是否是合流汇总数据
	 
	*/
	public final boolean getIsHLDtl()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsHLDtl);
	}
	public final void setIsHLDtl(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsHLDtl, value);
	}
	public int _IsReadonly = 2;
	public final boolean getIsReadonly()
	{
		if (_IsReadonly != 2)
		{
			if (_IsReadonly == 1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		if (this.getIsDelete() || this.getIsInsert() || this.getIsUpdate())
		{
			_IsReadonly = 0;
			return false;
		}
		_IsReadonly = 1;
		return true;
	}
	public final boolean getIsDelete()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsDelete);
	}
	public final void setIsDelete(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsDelete, value);
	}
	public final boolean getIsInsert()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsInsert);
	}
	public final void setIsInsert(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsInsert, value);
	}
	/** 
	 是否可见
	 
	*/
	public final boolean getIsView()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsView);
	}
	public final void setIsView(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsView, value);
	}
	public final boolean getIsUpdate()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsUpdate);
	}
	public final void setIsUpdate(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsUpdate, value);
	}
	/** 
	 是否启用多附件
	 
	*/
	public final boolean getIsEnableAthM()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsEnableAthM);
	}
	public final void setIsEnableAthM(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsEnableAthM, value);
	}
	/** 
	 是否启用分组字段
	 
	*/
	public final boolean getIsEnableGroupField()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsEnableGroupField);
	}
	public final void setIsEnableGroupField(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsEnableGroupField, value);
	}
	/** 
	 是否起用审核连接
	 
	*/
	public final boolean getIsEnablePass()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsEnablePass);
	}
	public final void setIsEnablePass(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsEnablePass, value);
	}

	/** 
	 是否copy数据？
	 
	*/
	public final boolean getIsCopyNDData()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsCopyNDData);
	}
	public final void setIsCopyNDData(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsCopyNDData, value);
	}
	/** 
	 是否启用一对多
	 
	*/
	public final boolean getIsEnableM2M()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsEnableM2M);
	}
	public final void setIsEnableM2M(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsEnableM2M, value);
	}
	/** 
	 是否启用一对多多
	 
	*/
	public final boolean getIsEnableM2MM()
	{
		return this.GetValBooleanByKey(MapDtlAttr.IsEnableM2MM);
	}
	public final void setIsEnableM2MM(boolean value)
	{
		this.SetValByKey(MapDtlAttr.IsEnableM2MM, value);
	}

	public boolean IsUse = false;
	/** 
	 是否检查人员的权限
	 
	*/
	public final DtlOpenType getDtlOpenType()
	{
		return DtlOpenType.forValue(this.GetValIntByKey(MapDtlAttr.DtlOpenType));
	}
	public final void setDtlOpenType(DtlOpenType value)
	{
		this.SetValByKey(MapDtlAttr.DtlOpenType, value.getValue());
	}
	/** 
	 分组字段
	 
	*/
	public final String getGroupField()
	{
		return this.GetValStrByKey(MapDtlAttr.GroupField);
	}
	public final void setGroupField(String value)
	{
		this.SetValByKey(MapDtlAttr.GroupField, value);
	}
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(MapDtlAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(MapDtlAttr.FK_MapData, value);
	}
	public final int getRowsOfList()
	{
		return this.GetValIntByKey(MapDtlAttr.RowsOfList);
	}
	public final void setRowsOfList(int value)
	{
		this.SetValByKey(MapDtlAttr.RowsOfList, value);
	}
	public final int getRowIdx()
	{
		return this.GetValIntByKey(MapDtlAttr.RowIdx);
	}
	public final void setRowIdx(int value)
	{
		this.SetValByKey(MapDtlAttr.RowIdx, value);
	}
	public final int getGroupID()
	{
		return this.GetValIntByKey(MapDtlAttr.GroupID);
	}
	public final void setGroupID(int value)
	{
		this.SetValByKey(MapDtlAttr.GroupID, value);
	}
	public final String getPTable()
	{
		String s = this.GetValStrByKey(MapDtlAttr.PTable);
		if (s.equals("") || s == null)
		{
			s = this.getNo();
			if (s.substring(0, 1).equals("0"))
			{
				return "T" + this.getNo();
			}
			else
			{
				return s;
			}
		}
		else
		{
			if (s.substring(0, 1).equals("0"))
			{
				return "T" + this.getNo();
			}
			else
			{
				return s;
			}
		}
	}
	public final void setPTable(String value)
	{
		this.SetValByKey(MapDtlAttr.PTable, value);
	}
	/** 
	 多表头
	 
	*/
	public final String getMTR()
	{
		String s= this.GetValStrByKey(MapDtlAttr.MTR);
		s = s.replace("《","<");
		s = s.replace("》",">");
		s = s.replace("‘","'");
		return s;
	}
	public final void setMTR(String value)
	{
		String s = value;
		s = s.replace("<","《");
		s = s.replace(">", "》");
		s = s.replace("'", "‘");
		this.SetValByKey(MapDtlAttr.MTR, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	public final Map GenerMap()
	{
		boolean isdebug = SystemConfig.getIsDebug();

		if (!isdebug )
		{
			Map m = BP.DA.Cash.GetMap(this.getNo());
			if (m != null)
			{
				return m;
			}
		}

		MapAttrs mapAttrs = this.getMapAttrs();
		Map map = new Map(this.getPTable());
		map.setEnDesc(this.getName());
		map.setEnType(EnType.App);
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);

		Attrs attrs = new Attrs();
		for (MapAttr mapAttr : MapAttrs.convertMapAttrs(mapAttrs))
		{
			map.AddAttr(mapAttr.getHisAttr());
		}

		BP.DA.Cash.SetMap(this.getNo(), map);
		return map;
	}
	public final GEDtl getHisGEDtl()
	{
		GEDtl dtl = new GEDtl(this.getNo());
		return dtl;
	}
	public final GEEntity GenerGEMainEntity(String mainPK)
	{
		GEEntity en = new GEEntity(this.getFK_MapData(), mainPK);
		return en;
	}
	/** 
	 明细
	 
	*/
	public MapDtl()
	{
	}
	public MapDtl(String mypk)
	{
		this.setNo(mypk);
		this._IsReadonly = 2;
		this.Retrieve();
	}
	/** 
	 EnMap
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Sys_MapDtl");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("明细");
		map.setEnType(EnType.Sys);

		map.AddTBStringPK(MapDtlAttr.No, null, "编号", true, false, 1, 20, 20);
		map.AddTBString(MapDtlAttr.Name, null, "描述", true, false, 1, 50, 20);
		map.AddTBString(MapDtlAttr.FK_MapData, null, "主表", true, false, 0, 30, 20);
		map.AddTBString(MapDtlAttr.PTable, null, "物理表", true, false, 0, 30, 20);
		map.AddTBString(MapDtlAttr.GroupField, null, "分组字段", true, false, 0, 50, 20);

			//map.AddTBInt(MapDtlAttr.Model, 0, "工作模式", false, false);
		map.AddDDLSysEnum(MapDtlAttr.Model, 0, "工作模式", true, true, MapDtlAttr.Model, "@0=普通@1=固定行");

		map.AddTBString(MapDtlAttr.ImpFixTreeSql, null, "固定列树形SQL", true, false, 0, 500, 20);
		map.AddTBString(MapDtlAttr.ImpFixDataSql, null, "固定列数据SQL", true, false, 0, 500, 20);

		map.AddTBInt(MapDtlAttr.RowIdx, 99, "位置", false, false);
		map.AddTBInt(MapDtlAttr.GroupID, 0, "GroupID", false, false);
		map.AddTBInt(MapDtlAttr.RowsOfList, 6, "Rows", false, false);

		map.AddBoolean(MapDtlAttr.IsEnableGroupField, false, "是否启用分组字段", false, false);

		map.AddBoolean(MapDtlAttr.IsShowSum, true, "IsShowSum", false, false);
		map.AddBoolean(MapDtlAttr.IsShowIdx, true, "IsShowIdx", false, false);
		map.AddBoolean(MapDtlAttr.IsCopyNDData, true, "IsCopyNDData", false, false);
		map.AddBoolean(MapDtlAttr.IsHLDtl, false, "是否是合流汇总", false, false);

		map.AddBoolean(MapDtlAttr.IsReadonly, false, "IsReadonly", false, false);
		map.AddBoolean(MapDtlAttr.IsShowTitle, true, "IsShowTitle", false, false);
		map.AddBoolean(MapDtlAttr.IsView, true, "是否可见", false, false);



		map.AddBoolean(MapDtlAttr.IsInsert, true, "IsInsert", false, false);
		map.AddBoolean(MapDtlAttr.IsDelete, true, "IsDelete", false, false);
		map.AddBoolean(MapDtlAttr.IsUpdate, true, "IsUpdate", false, false);

		map.AddBoolean(MapDtlAttr.IsEnablePass, false, "是否启用通过审核功能?", false, false);
		map.AddBoolean(MapDtlAttr.IsEnableAthM, false, "是否启用多附件", false, false);

		map.AddBoolean(MapDtlAttr.IsEnableM2M, false, "是否启用M2M", false, false);
		map.AddBoolean(MapDtlAttr.IsEnableM2MM, false, "是否启用M2M", false, false);
		map.AddDDLSysEnum(MapDtlAttr.WhenOverSize, 0, "WhenOverSize", true, true, MapDtlAttr.WhenOverSize, "@0=不处理@1=向下顺增行@2=次页显示");

		map.AddDDLSysEnum(MapDtlAttr.DtlOpenType, 1, "数据开放类型", true, true, MapDtlAttr.DtlOpenType, "@0=操作员@1=工作ID@2=流程ID");

		map.AddDDLSysEnum(MapDtlAttr.DtlShowModel, 0, "显示格式", true, true, MapDtlAttr.DtlShowModel, "@0=表格@1=卡片");

		map.AddTBFloat(MapDtlAttr.X, 5, "X", true, false);
		map.AddTBFloat(MapDtlAttr.Y, 5, "Y", false, false);

		map.AddTBFloat(MapDtlAttr.H, 150, "H", true, false);
		map.AddTBFloat(MapDtlAttr.W, 200, "W", false, false);

		map.AddTBFloat(MapDtlAttr.FrmW, 900, "FrmW", true, true);
		map.AddTBFloat(MapDtlAttr.FrmH, 1200, "FrmH", true, true);

			//MTR 多表头列.
		map.AddTBString(MapDtlAttr.MTR, null, "多表头列", true, false, 0, 3000, 20);
		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);

			//add 2014-02-21.
		map.AddTBInt(MapDtlAttr.FK_Node, 0, "节点(用户流程表单权限控制)", false, false);

			//参数.
		map.AddTBAtParas(300);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region 导入导出填充.
			// 2014-07-17 for xinchang bank.
		map.AddBoolean(MapDtlAttr.IsExp, true, "IsExp", false, false);
		map.AddBoolean(MapDtlAttr.IsImp, true, "IsImp", false, false);
		map.AddBoolean(MapDtlAttr.IsEnableSelectImp, false, "是否启用选择数据导入?", false, false);
		map.AddTBString(MapDtlAttr.ImpSQLSearch, null, "查询SQL", true, false, 0, 500, 20);
		map.AddTBString(MapDtlAttr.ImpSQLInit, null, "初始化SQL", true, false, 0, 500, 20);
		map.AddTBString(MapDtlAttr.ImpSQLFull, null, "数据填充SQL", true, false, 0, 500, 20);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion 导入导出填充.



		this.set_enMap(map);
		return this.get_enMap();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性.
	public final float getX()
	{
		return this.GetValFloatByKey(FrmImgAttr.X);
	}
	public final float getY()
	{
		return this.GetValFloatByKey(FrmImgAttr.Y);
	}
	public final float getW()
	{
		return this.GetValFloatByKey(FrmImgAttr.W);
	}
	public final float getH()
	{
		return this.GetValFloatByKey(FrmImgAttr.H);
	}
	public final float getFrmW()
	{
		return this.GetValFloatByKey(MapDtlAttr.FrmW);
	}
	public final float getFrmH()
	{
		return this.GetValFloatByKey(MapDtlAttr.FrmH);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 基本属性.

	/** 
	 获取个数
	 
	 @param fk_val
	 @return 
	*/
	public final int GetCountByFK(int workID)
	{
		try {
			return BP.DA.DBAccess.RunSQLReturnValInt("select COUNT(OID) from " + this.getPTable() + " WHERE WorkID=" + workID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public final int GetCountByFK(String field, String val)
	{
		try {
			return BP.DA.DBAccess.RunSQLReturnValInt("select COUNT(OID) from " + this.getPTable() + " WHERE " + field + "='" + val + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public final int GetCountByFK(String field, long val)
	{
		try {
			return BP.DA.DBAccess.RunSQLReturnValInt("select COUNT(OID) from " + this.getPTable() + " WHERE " + field + "=" + val);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public final int GetCountByFK(String f1, long val1, String f2, String val2)
	{
		try {
			return BP.DA.DBAccess.RunSQLReturnValInt("SELECT COUNT(OID) from " + this.getPTable() + " WHERE " + f1 + "=" + val1 + " AND " + f2 + "='" + val2 + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final void IntMapAttrs()
	{
		MapData md = new MapData();
		md.setNo(this.getNo());
		if (md.RetrieveFromDBSources() == 0)
		{
			md.setName(this.getName());
			md.Insert();
		}

		MapAttrs attrs = new MapAttrs(this.getNo());
		MapAttr attr = new MapAttr();
		if (!attrs.Contains(MapAttrAttr.KeyOfEn, "OID") )
		{
			attr = new MapAttr();
			attr.setFK_MapData(this.getNo());
			attr.setHisEditType(EditType.Readonly);

			attr.setKeyOfEn("OID");
			attr.setName("主键");
			attr.setMyDataType(BP.DA.DataType.AppInt);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setDefVal("0");
			attr.Insert();
		}

		if (!attrs.Contains(MapAttrAttr.KeyOfEn, "RefPK") )
		{
			attr = new MapAttr();
			attr.setFK_MapData(this.getNo());
			attr.setHisEditType(EditType.Readonly);

			attr.setKeyOfEn("RefPK");
			attr.setName("关联ID");
			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setDefVal("0");
			attr.Insert();
		}

		if (!attrs.Contains(MapAttrAttr.KeyOfEn, "FID") )
		{
			attr = new MapAttr();
			attr.setFK_MapData(this.getNo());
			attr.setHisEditType(EditType.Readonly);

			attr.setKeyOfEn("FID");
			attr.setName("FID");
			attr.setMyDataType(BP.DA.DataType.AppInt);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setDefVal("0");
			attr.Insert();
		}

		if (!attrs.Contains(MapAttrAttr.KeyOfEn, "RDT") )
		{
			attr = new MapAttr();
			attr.setFK_MapData(this.getNo());
			attr.setHisEditType(EditType.UnDel);

			attr.setKeyOfEn("RDT");
			attr.setName("记录时间");
			attr.setMyDataType(BP.DA.DataType.AppDateTime);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setTag("1");
			attr.Insert();
		}

		if (!attrs.Contains(MapAttrAttr.KeyOfEn, "Rec") )
		{
			attr = new MapAttr();
			attr.setFK_MapData(this.getNo());
			attr.setHisEditType(EditType.Readonly);

			attr.setKeyOfEn("Rec");
			attr.setName("记录人");
			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setMaxLen(20);
			attr.setMinLen(0);
			attr.setDefVal("@WebUser.No");
			attr.setTag("@WebUser.No");
			attr.Insert();
		}
	}
	private void InitExtMembers()
	{
		// 如果启用了多附件
		if (this.getIsEnableAthM())
		{
			FrmAttachment athDesc = new FrmAttachment();
			athDesc.setMyPK(this.getNo() + "_AthM");
			if (athDesc.RetrieveFromDBSources() == 0)
			{
				athDesc.setFK_MapData(this.getNo());
				athDesc.setNoOfObj("AthM");
				athDesc.setName(this.getName());
				athDesc.Insert();
			}
		}

		if (this.getIsEnableM2M())
		{
			MapM2M m2m = new MapM2M();
			m2m.setMyPK(this.getNo() + "_M2M");
			m2m.setName("M2M");
			m2m.setNoOfObj("M2M");
			m2m.setFK_MapData(this.getNo());
			if (m2m.RetrieveFromDBSources() == 0)
			{
				m2m.setFK_MapData(this.getNo());
				m2m.setNoOfObj("M2M");
				m2m.Insert();
			}
		}

		if (this.getIsEnableM2MM())
		{
			MapM2M m2m = new MapM2M();
			m2m.setMyPK(this.getNo() + "_M2MM");
			m2m.setName("M2MM");
			m2m.setNoOfObj("M2MM");
			m2m.setFK_MapData(this.getNo());
			if (m2m.RetrieveFromDBSources() == 0)
			{
				m2m.setFK_MapData(this.getNo());
				m2m.setNoOfObj("M2MM");
				m2m.Insert();
			}
		}
	}
	@Override
	protected boolean beforeInsert()
	{
		this.InitExtMembers();
		return super.beforeInsert();
	}
	@Override
	protected boolean beforeUpdateInsertAction()
	{
		MapData md = new MapData();
		md.setNo(this.getNo());
		if (md.RetrieveFromDBSources() == 0)
		{
			md.setName(this.getName());
			md.Insert();
		}

		if (this.getIsRowLock())
		{
			//检查
			MapAttrs attrs = new MapAttrs(this.getNo());
		
			if (!attrs.Contains(MapAttrAttr.KeyOfEn, "IsRowLock") )
			{
				throw new RuntimeException("您启用了从表单(" + this.getName() + ")行数据锁定功能，但是该从表里没IsRowLock字段，请参考帮助文档。");
			}
		}

		if (this.getIsEnablePass())
		{
			//判断是否有IsPass 字段。
			MapAttrs attrs = new MapAttrs(this.getNo());
			if (!attrs.Contains(MapAttrAttr.KeyOfEn, "IsPass") )
			{
				throw new RuntimeException("您启用了从表单(" + this.getName() + ")条数据审核选项，但是该从表里没IsPass字段，请参考帮助文档。");
			}
		}
		return super.beforeUpdateInsertAction();
	}
	@Override
	protected boolean beforeUpdate()
	{
		MapAttrs attrs = new MapAttrs(this.getNo());
		boolean isHaveEnable = false;
		for (MapAttr attr : MapAttrs.convertMapAttrs(attrs))
		{
			if (attr.getUIIsEnable() && attr.getUIContralType() == UIContralType.TB)
			{
				isHaveEnable = true;
			}
		}
		this.InitExtMembers();
		return super.beforeUpdate();
	}
	@Override
	protected boolean beforeDelete()
	{
		String sql = "";
		sql += "@DELETE FROM Sys_FrmLine WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_FrmLab WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_FrmLink WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_FrmImg WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_FrmImgAth WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_FrmRB WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_FrmAttachment WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_MapFrame WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_MapExt WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_MapAttr WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_MapData WHERE No='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_GroupField WHERE EnName='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_MapM2M WHERE FK_MapData='" + this.getNo() + "'";
		try {
			DBAccess.RunSQLs(sql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			BP.DA.DBAccess.RunSQL("DROP TABLE " + this.getPTable());
		}
		catch (java.lang.Exception e)
		{
		}
		return super.beforeDelete();
	}
}