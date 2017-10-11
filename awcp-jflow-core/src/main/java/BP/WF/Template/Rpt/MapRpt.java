package BP.WF.Template.Rpt;

import org.apache.commons.lang3.StringUtils;

import BP.DA.*;
import BP.Port.*;
import BP.En.*;
import BP.WF.*;
import BP.Sys.*;
import BP.Sys.Frm.DTSearchWay;
import BP.Sys.Frm.FrmAttachments;
import BP.Sys.Frm.FrmBtns;
import BP.Sys.Frm.FrmEles;
import BP.Sys.Frm.FrmEvents;
import BP.Sys.Frm.FrmImgAths;
import BP.Sys.Frm.FrmImgs;
import BP.Sys.Frm.FrmLabs;
import BP.Sys.Frm.FrmLines;
import BP.Sys.Frm.FrmLinks;
import BP.Sys.Frm.FrmRBs;
import BP.Sys.Frm.GroupFields;
import BP.Sys.Frm.MapAttr;
import BP.Sys.Frm.MapAttrAttr;
import BP.Sys.Frm.MapAttrs;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDtl;
import BP.Sys.Frm.MapDtls;
import BP.Sys.Frm.MapExts;
import BP.Sys.Frm.MapFrames;
import BP.Sys.Frm.MapM2Ms;
import BP.Tools.StringHelper;

/**
 * 报表设计
 */
public class MapRpt extends EntityNoName {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 报表权限控制方式
	/**
	 * 报表查看权限控制.
	 */
	public final RightViewWay getRightViewWay() {
		return RightViewWay.forValue(this
				.GetValIntByKey(MapRptAttr.RightViewWay));
	}

	public final void setRightViewWay(RightViewWay value) {
		this.SetValByKey(MapRptAttr.RightViewWay, value.getValue());
	}

	/**
	 * 报表查看权限控制-数据
	 */
	public final String getRightViewTag() {
		return this.GetValStringByKey(MapRptAttr.RightViewTag);
	}

	public final void setRightViewTag(String value) {
		this.SetValByKey(MapRptAttr.RightViewTag, value);
	}

	/**
	 * 报表部门权限控制.
	 */
	public final RightDeptWay getRightDeptWay() {
		return RightDeptWay.forValue(this
				.GetValIntByKey(MapRptAttr.RightDeptWay));
	}

	public final void setRightDeptWay(RightDeptWay value) {
		this.SetValByKey(MapRptAttr.RightDeptWay, value.getValue());
	}

	/**
	 * 报表部门权限控制-数据
	 */
	public final String getRightDeptTag() {
		return this.GetValStringByKey(MapRptAttr.RightDeptTag);
	}

	public final void setRightDeptTag(String value) {
		this.SetValByKey(MapRptAttr.RightDeptTag, value);
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion 报表权限控制方式

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 外键属性
	/**
	 * 框架
	 */
	public final MapFrames getMapFrames() {
		Object tempVar = this.GetRefObject("MapFrames");
		MapFrames obj = (MapFrames) ((tempVar instanceof MapFrames) ? tempVar
				: null);
		if (obj == null) {
			obj = new MapFrames(this.getNo());
			this.SetRefObject("MapFrames", obj);
		}
		return obj;
	}

	/**
	 * 分组字段
	 */
	public final GroupFields getGroupFields() {
		Object tempVar = this.GetRefObject("GroupFields");
		GroupFields obj = (GroupFields) ((tempVar instanceof GroupFields) ? tempVar
				: null);
		if (obj == null) {
			obj = new GroupFields(this.getNo());
			this.SetRefObject("GroupFields", obj);
		}
		return obj;
	}

	/**
	 * 逻辑扩展
	 */
	public final MapExts getMapExts() {
		Object tempVar = this.GetRefObject("MapExts");
		MapExts obj = (MapExts) ((tempVar instanceof MapExts) ? tempVar : null);
		if (obj == null) {
			obj = new MapExts(this.getNo());
			this.SetRefObject("MapExts", obj);
		}
		return obj;
	}

	/**
	 * 事件
	 */
	public final FrmEvents getFrmEvents() {
		Object tempVar = this.GetRefObject("FrmEvents");
		FrmEvents obj = (FrmEvents) ((tempVar instanceof FrmEvents) ? tempVar
				: null);
		if (obj == null) {
			obj = new FrmEvents(this.getNo());
			this.SetRefObject("FrmEvents", obj);
		}
		return obj;
	}

	/**
	 * 一对多
	 */
	public final MapM2Ms getMapM2Ms() {
		Object tempVar = this.GetRefObject("MapM2Ms");
		MapM2Ms obj = (MapM2Ms) ((tempVar instanceof MapM2Ms) ? tempVar : null);
		if (obj == null) {
			obj = new MapM2Ms(this.getNo());
			this.SetRefObject("MapM2Ms", obj);
		}
		return obj;
	}

	/**
	 * 从表
	 */
	public final MapDtls getMapDtls() {
		Object tempVar = this.GetRefObject("MapDtls");
		MapDtls obj = (MapDtls) ((tempVar instanceof MapDtls) ? tempVar : null);
		if (obj == null) {
			obj = new MapDtls(this.getNo());
			this.SetRefObject("MapDtls", obj);
		}
		return obj;
	}

	/**
	 * 超连接
	 */
	public final FrmLinks getFrmLinks() {
		Object tempVar = this.GetRefObject("FrmLinks");
		FrmLinks obj = (FrmLinks) ((tempVar instanceof FrmLinks) ? tempVar
				: null);
		if (obj == null) {
			obj = new FrmLinks(this.getNo());
			this.SetRefObject("FrmLinks", obj);
		}
		return obj;
	}

	/**
	 * 按钮
	 */
	public final FrmBtns getFrmBtns() {
		Object tempVar = this.GetRefObject("FrmLinks");
		FrmBtns obj = (FrmBtns) ((tempVar instanceof FrmBtns) ? tempVar : null);
		if (obj == null) {
			obj = new FrmBtns(this.getNo());
			this.SetRefObject("FrmBtns", obj);
		}
		return obj;
	}

	/**
	 * 元素
	 */
	public final FrmEles getFrmEles() {
		Object tempVar = this.GetRefObject("FrmEles");
		FrmEles obj = (FrmEles) ((tempVar instanceof FrmEles) ? tempVar : null);
		if (obj == null) {
			obj = new FrmEles(this.getNo());
			this.SetRefObject("FrmEles", obj);
		}
		return obj;
	}

	/**
	 * 线
	 */
	public final FrmLines getFrmLines() {
		Object tempVar = this.GetRefObject("FrmLines");
		FrmLines obj = (FrmLines) ((tempVar instanceof FrmLines) ? tempVar
				: null);
		if (obj == null) {
			obj = new FrmLines(this.getNo());
			this.SetRefObject("FrmLines", obj);
		}
		return obj;
	}

	/**
	 * 标签
	 */
	public final FrmLabs getFrmLabs() {
		Object tempVar = this.GetRefObject("FrmLabs");
		FrmLabs obj = (FrmLabs) ((tempVar instanceof FrmLabs) ? tempVar : null);
		if (obj == null) {
			obj = new FrmLabs(this.getNo());
			this.SetRefObject("FrmLabs", obj);
		}
		return obj;
	}

	/**
	 * 图片
	 */
	public final FrmImgs getFrmImgs() {
		Object tempVar = this.GetRefObject("FrmLabs");
		FrmImgs obj = (FrmImgs) ((tempVar instanceof FrmImgs) ? tempVar : null);
		if (obj == null) {
			obj = new FrmImgs(this.getNo());
			this.SetRefObject("FrmLabs", obj);
		}
		return obj;
	}

	/**
	 * 附件
	 */
	public final FrmAttachments getFrmAttachments() {
		Object tempVar = this.GetRefObject("FrmAttachments");
		FrmAttachments obj = (FrmAttachments) ((tempVar instanceof FrmAttachments) ? tempVar
				: null);
		if (obj == null) {
			obj = new FrmAttachments(this.getNo());
			this.SetRefObject("FrmAttachments", obj);
		}
		return obj;
	}

	/**
	 * 图片附件
	 */
	public final FrmImgAths getFrmImgAths() {
		Object tempVar = this.GetRefObject("FrmImgAths");
		FrmImgAths obj = (FrmImgAths) ((tempVar instanceof FrmImgAths) ? tempVar
				: null);
		if (obj == null) {
			obj = new FrmImgAths(this.getNo());
			this.SetRefObject("FrmImgAths", obj);
		}
		return obj;
	}

	/**
	 * 单选按钮
	 */
	public final FrmRBs getFrmRBs() {
		Object tempVar = this.GetRefObject("FrmRBs");
		FrmRBs obj = (FrmRBs) ((tempVar instanceof FrmRBs) ? tempVar : null);
		if (obj == null) {
			obj = new FrmRBs(this.getNo());
			this.SetRefObject("FrmRBs", obj);
		}
		return obj;
	}

	/**
	 * 属性
	 */
	public final MapAttrs getMapAttrs() {
		Object tempVar = this.GetRefObject("MapAttrs");
		MapAttrs obj = (MapAttrs) ((tempVar instanceof MapAttrs) ? tempVar
				: null);
		if (obj == null) {
			obj = new MapAttrs(this.getNo());
			this.SetRefObject("MapAttrs", obj);
		}
		return obj;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 属性
	public final String getFK_Flow() {
		String s = this.GetValStrByKey(MapRptAttr.FK_Flow);
		if (s.equals("") || s == null) {
			s = this.getParentMapData();
			if (StringHelper.isNullOrEmpty(s)) {
				throw new RuntimeException("@错误报表" + this.getNo() + ","
						+ this.getName() + " , 字段ParentMapData:"
						+ this.getParentMapData() + " 不应当为空.");
			}
			s = s.replace("ND", "");
			s = s.replace("Rpt", "");
			s = StringUtils.leftPad(s, 3, "0");
		
			return s;
		}
		return s;
	}

	public final void setFK_Flow(String value) {
		this.SetValByKey(MapRptAttr.FK_Flow, value);
	}

	public final String getPTable() {
		String s = this.GetValStrByKey(MapRptAttr.PTable);
		if (s.equals("") || s == null) {
			return this.getNo();
		}
		return s;
	}

	public final void setPTable(String value) {
		this.SetValByKey(MapRptAttr.PTable, value);
	}

	/**
	 * 备注
	 */
	public final String getNote() {
		return this.GetValStrByKey(MapRptAttr.Note);
	}

	public final void setNote(String value) {
		this.SetValByKey(MapRptAttr.Note, value);
	}

	public final String getParentMapData() {
		return this.GetValStrByKey(MapRptAttr.ParentMapData);
	}

	public final void setParentMapData(String value) {
		this.SetValByKey(MapRptAttr.ParentMapData, value);
	}

	public Entities _HisEns = null;

	public final Entities getHisEns() {
		if (_HisEns == null) {
			_HisEns = BP.En.ClassFactory.GetEns(this.getNo());
		}
		return _HisEns;
	}

	public final Entity getHisEn() {
		return this.getHisEns().getGetNewEntity();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 构造方法
	private GEEntity _HisEn = null;

	public final GEEntity getHisGEEn() {
		if (this._HisEn == null) {
			_HisEn = new GEEntity(this.getNo());
		}
		return _HisEn;
	}

	/**
	 * 生成实体
	 * 
	 * @param ds
	 * @return
	 */
	public final GEEntity GenerGEEntityByDataSet(DataSet ds) {
		// New 它的实例.
		GEEntity en = this.getHisGEEn();

		// 它的table.
		DataTable dt = ds.hashTables.get(this.getNo());

		// 装载数据.
		en.getRow().LoadDataTable(dt, dt.Rows.get(0));

		// dtls.
		MapDtls dtls = this.getMapDtls();
		for (MapDtl item : MapDtls.convertMapDtls(dtls)) {
			DataTable dtDtls = ds.hashTables.get(item.getNo());
			GEDtls dtlsEn = new GEDtls(item.getNo());
			for (DataRow dr : dtDtls.Rows) {
				// 产生它的Entity data.
				GEDtl dtl = (GEDtl) dtlsEn.getGetNewEntity();
				dtl.getRow().LoadDataTable(dtDtls, dr);

				// 加入这个集合.
				dtlsEn.AddEntity(dtl);
			}

			// 加入到他的集合里.
			en.getDtls().add(dtDtls);
		}
		return en;
	}

	/**
	 * 报表设计
	 */
	public MapRpt() {
	}

	/**
	 * 报表设计
	 * 
	 * @param no
	 *            映射编号
	 */
	public MapRpt(String no) {
		this.setNo(no);
		this.Retrieve();
	}

	/**
	 * EnMap
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}
		Map map = new Map("Sys_MapData");
		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("报表设计");
		map.setEnType(EnType.Sys);
		map.setCodeStruct("4");

		map.AddTBStringPK(MapRptAttr.No, null, "编号", true, false, 1, 20, 20);
		map.AddTBString(MapRptAttr.Name, null, "描述", true, false, 0, 500, 20);
		// map.AddTBString(MapRptAttr.SearchKeys, null, "查询键", true, false, 0,
		// 500, 20);
		map.AddTBString(MapRptAttr.PTable, null, "物理表", true, false, 0, 500, 20);
		map.AddTBString(MapRptAttr.FK_Flow, null, "流程编号", true, false, 0, 3, 3);

		// Tag
		// map.AddTBString(MapRptAttr.Tag, null, "Tag", true, false, 0, 500,
		// 20);

		// 时间查询:用于报表查询.
		// map.AddTBInt(MapRptAttr.IsSearchKey, 0, "是否需要关键字查询", true, false);
		// map.AddTBInt(MapRptAttr.DTSearchWay, 0, "时间查询方式", true, false);
		// map.AddTBString(MapRptAttr.DTSearchKey, null, "时间查询字段", true, false,
		// 0, 200, 20);
		map.AddTBString(MapRptAttr.Note, null, "备注", true, false, 0, 500, 20);

		map.AddTBString(MapRptAttr.ParentMapData, null, "ParentMapData", true,
				false, 0, 128, 20);

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 权限控制. 2014-12-18
		map.AddTBInt(MapRptAttr.RightViewWay, 0, "报表查看权限控制方式", true, false);
		map.AddTBString(MapRptAttr.RightViewTag, null, "报表查看权限控制Tag", true,
				false, 0, 4000, 20);

		map.AddTBInt(MapRptAttr.RightDeptWay, 0, "部门数据查看控制方式", true, false);
		map.AddTBString(MapRptAttr.RightDeptTag, null, "部门数据查看控制Tag", true,
				false, 0, 4000, 20);

		map.getAttrsOfOneVSM().Add(new RptStations(), new Stations(),
				RptStationAttr.FK_Rpt, RptStationAttr.FK_Station,
				DeptAttr.Name, DeptAttr.No, "岗位权限");
		map.getAttrsOfOneVSM().Add(new RptDepts(), new Depts(),
				RptDeptAttr.FK_Rpt, RptDeptAttr.FK_Dept, DeptAttr.Name,
				DeptAttr.No, "部门权限");
		map.getAttrsOfOneVSM().Add(new RptEmps(), new Emps(),
				RptEmpAttr.FK_Rpt, RptEmpAttr.FK_Emp, DeptAttr.Name,
				DeptAttr.No, "人员权限");
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 权限控制.

		this.set_enMap(map);
		return this.get_enMap();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	public final MapAttrs getHisShowColsAttrs() {
		MapAttrs mattrs = new MapAttrs(this.getNo());
		return mattrs;
	}

	@Override
	protected boolean beforeInsert() {
		this.ResetIt();
		return super.beforeInsert();
	}

	public final void ResetIt() {
		MapData md = new MapData(this.getNo());
		md.setRptIsSearchKey(true);
		md.setRptDTSearchWay(DTSearchWay.None);
		md.setRptDTSearchKey("");
		md.setRptSearchKeys("*FK_Dept*WFSta*FK_NY*");

		MapData pmd = new MapData(this.getParentMapData());
		this.setPTable(pmd.getPTable());
		this.Update();

		String keys = "'OID','FK_Dept','FlowStarter','WFState','Title','FlowStartRDT','FlowEmps','FlowDaySpan','FlowEnder','FlowEnderRDT','FK_NY','FlowEndNode','WFSta'";
		MapAttrs attrs = new MapAttrs(this.getParentMapData());
		attrs.Delete(MapAttrAttr.FK_MapData, this.getNo()); // 删除已经有的字段。
		for (MapAttr attr : MapAttrs.convertMapAttrs(attrs)) {
			if (keys.contains("'" + attr.getKeyOfEn() + "'") == false) {
				continue;
			}
			attr.setFK_MapData(this.getNo());
			attr.Insert();
		}
	}
	
	/**
	 * 报表设计
	 * 
	 * @param no
	 *            映射编号
	 */
	public MapRpt(String no, String flowNo) {
		this.setNo(no);
		this.Retrieve();
	}

	@Override
	protected boolean beforeDelete() {
		MapAttrs attrs = new MapAttrs();
		attrs.Delete(MapAttrAttr.FK_MapData, this.getNo());
		return super.beforeDelete();
	}
}