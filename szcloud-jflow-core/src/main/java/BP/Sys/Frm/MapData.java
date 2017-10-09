package BP.Sys.Frm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import BP.DA.DBAccess;
import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.DataColumn;
import BP.DA.DataRow;
import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.DA.DataType;
import BP.DA.Depositary;
import BP.En.EditType;
import BP.En.EnType;
import BP.En.Entities;
import BP.En.Entity;
import BP.En.EntityNoName;
import BP.En.FieldTypeS;
import BP.En.Map;
import BP.En.UIContralType;
import BP.Sys.GEDtl;
import BP.Sys.GEDtls;
import BP.Sys.GEEntity;
import BP.Sys.PubClass;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;

public class MapData extends EntityNoName {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(MapData.class);

	public final float getMaxLeft() {
		return this.GetParaFloat(MapDataAttr.MaxLeft);
	}

	public final void setMaxLeft(float value) {
		this.SetPara(MapDataAttr.MaxLeft, value);
	}

	public final float getMaxRight() {
		return this.GetParaFloat(MapDataAttr.MaxRight);
	}

	public final void setMaxRight(float value) {
		this.SetPara(MapDataAttr.MaxRight, value);
	}

	public final float getMaxTop() {
		return this.GetParaFloat(MapDataAttr.MaxTop);
	}

	public final void setMaxTop(float value) {
		this.SetPara(MapDataAttr.MaxTop, value);
	}

	public final float getMaxEnd() {
		return this.GetParaFloat(MapDataAttr.MaxEnd);
	}

	public final void setMaxEnd(float value) {
		this.SetPara(MapDataAttr.MaxEnd, value);
	}

	public final boolean getRptIsSearchKey() {
		return this.GetParaBoolen(MapDataAttr.RptIsSearchKey, true);
	}

	public final void setRptIsSearchKey(boolean value) {
		this.SetPara(MapDataAttr.RptIsSearchKey, value);
	}

	public final DTSearchWay getRptDTSearchWay() {
		return DTSearchWay.forValue(this.GetParaInt(MapDataAttr.RptDTSearchWay));
	}

	public final void setRptDTSearchWay(DTSearchWay value) {
		this.SetPara(MapDataAttr.RptDTSearchWay, value.getValue());
	}

	public final String getRptDTSearchKey() {
		return this.GetParaString(MapDataAttr.RptDTSearchKey);
	}

	public final void setRptDTSearchKey(String value) {
		this.SetPara(MapDataAttr.RptDTSearchKey, value);
	}

	public final String getRptSearchKeys() {
		return this.GetParaString(MapDataAttr.RptSearchKeys, "*");
	}

	public final void setRptSearchKeys(String value) {
		this.SetPara(MapDataAttr.RptSearchKeys, value);
	}

	public final String getVer() {
		return this.GetValStringByKey(MapDataAttr.Ver);
	}

	public final void setVer(String value) {
		this.SetValByKey(MapDataAttr.Ver, value);
	}

	public final int getIdx() {
		return this.GetValIntByKey(MapDataAttr.Idx);
	}

	public final void setIdx(int value) {
		this.SetValByKey(MapDataAttr.Idx, value);
	}

	public final MapFrames getMapFrames() {
		Object tempVar = this.GetRefObject("MapFrames");
		MapFrames obj = (MapFrames) ((tempVar instanceof MapFrames) ? tempVar : null);
		if (obj == null) {
			obj = new MapFrames(this.getNo());
			this.SetRefObject("MapFrames", obj);
		}
		return obj;
	}

	public final GroupFields getGroupFields() {
		Object tempVar = this.GetRefObject("GroupFields");
		GroupFields obj = (GroupFields) ((tempVar instanceof GroupFields) ? tempVar : null);
		if (obj == null) {
			obj = new GroupFields(this.getNo());
			this.SetRefObject("GroupFields", obj);
		}
		return obj;
	}

	public final MapExts getMapExts() {
		Object tempVar = this.GetRefObject("MapExts");
		MapExts obj = (MapExts) ((tempVar instanceof MapExts) ? tempVar : null);
		if (obj == null) {
			obj = new MapExts(this.getNo());
			this.SetRefObject("MapExts", obj);
		}
		return obj;
	}

	public final FrmEvents getFrmEvents() {
		Object tempVar = this.GetRefObject("FrmEvents");
		FrmEvents obj = (FrmEvents) ((tempVar instanceof FrmEvents) ? tempVar : null);
		if (obj == null) {
			obj = new FrmEvents(this.getNo());
			this.SetRefObject("FrmEvents", obj);
		}
		return obj;
	}

	public final MapM2Ms getMapM2Ms() {
		Object tempVar = this.GetRefObject("MapM2Ms");
		MapM2Ms obj = (MapM2Ms) ((tempVar instanceof MapM2Ms) ? tempVar : null);
		if (obj == null) {
			obj = new MapM2Ms(this.getNo());
			this.SetRefObject("MapM2Ms", obj);
		}
		return obj;
	}

	public final MapDtls getMapDtls() {
		Object tempVar = this.GetRefObject("MapDtls");
		MapDtls obj = (MapDtls) ((tempVar instanceof MapDtls) ? tempVar : null);
		if (obj == null) {
			obj = new MapDtls(this.getNo());
			this.SetRefObject("MapDtls", obj);
		}
		return obj;
	}

	public final FrmRpts getFrmRpts() {
		Object tempVar = this.GetRefObject("FrmRpts");
		FrmRpts obj = (FrmRpts) ((tempVar instanceof FrmRpts) ? tempVar : null);
		if (obj == null) {
			obj = new FrmRpts(this.getNo());
			this.SetRefObject("FrmRpts", obj);
		}
		return obj;
	}

	public final FrmLinks getFrmLinks() {
		Object tempVar = this.GetRefObject("FrmLinks");
		FrmLinks obj = (FrmLinks) ((tempVar instanceof FrmLinks) ? tempVar : null);
		if (obj == null) {
			obj = new FrmLinks(this.getNo());
			this.SetRefObject("FrmLinks", obj);
		}
		return obj;
	}

	public final FrmBtns getFrmBtns() {
		Object tempVar = this.GetRefObject("FrmLinks");
		FrmBtns obj = (FrmBtns) ((tempVar instanceof FrmBtns) ? tempVar : null);
		if (obj == null) {
			obj = new FrmBtns(this.getNo());
			this.SetRefObject("FrmBtns", obj);
		}
		return obj;
	}

	public final FrmEles getFrmEles() {
		Object tempVar = this.GetRefObject("FrmEles");
		FrmEles obj = (FrmEles) ((tempVar instanceof FrmEles) ? tempVar : null);
		if (obj == null) {
			obj = new FrmEles(this.getNo());
			this.SetRefObject("FrmEles", obj);
		}
		return obj;
	}

	public final FrmLines getFrmLines() {
		Object tempVar = this.GetRefObject("FrmLines");
		FrmLines obj = (FrmLines) ((tempVar instanceof FrmLines) ? tempVar : null);
		if (obj == null) {
			obj = new FrmLines(this.getNo());
			this.SetRefObject("FrmLines", obj);
		}
		return obj;
	}

	public final FrmLabs getFrmLabs() {
		Object tempVar = this.GetRefObject("FrmLabs");
		FrmLabs obj = (FrmLabs) ((tempVar instanceof FrmLabs) ? tempVar : null);
		if (obj == null) {
			obj = new FrmLabs(this.getNo());
			this.SetRefObject("FrmLabs", obj);
		}
		return obj;
	}

	public final FrmImgs getFrmImgs() {
		Object tempVar = this.GetRefObject("FrmLabs");
		FrmImgs obj = (FrmImgs) ((tempVar instanceof FrmImgs) ? tempVar : null);
		if (obj == null) {
			obj = new FrmImgs(this.getNo());
			this.SetRefObject("FrmLabs", obj);
		}
		return obj;
	}

	public final FrmAttachments getFrmAttachments() {
		Object tempVar = this.GetRefObject("FrmAttachments");
		FrmAttachments obj = (FrmAttachments) ((tempVar instanceof FrmAttachments) ? tempVar : null);
		if (obj == null) {
			obj = new FrmAttachments(this.getNo());
			this.SetRefObject("FrmAttachments", obj);
		}
		return obj;
	}

	public final FrmImgAths getFrmImgAths() {
		Object tempVar = this.GetRefObject("FrmImgAths");
		FrmImgAths obj = (FrmImgAths) ((tempVar instanceof FrmImgAths) ? tempVar : null);
		if (obj == null) {
			obj = new FrmImgAths(this.getNo());
			this.SetRefObject("FrmImgAths", obj);
		}
		return obj;
	}

	public final FrmRBs getFrmRBs() {
		Object tempVar = this.GetRefObject("FrmRBs");
		FrmRBs obj = (FrmRBs) ((tempVar instanceof FrmRBs) ? tempVar : null);
		if (obj == null) {
			obj = new FrmRBs(this.getNo());
			this.SetRefObject("FrmRBs", obj);
		}
		return obj;
	}

	public final MapAttrs getMapAttrs() {
		Object tempVar = this.GetRefObject("MapAttrs");
		MapAttrs obj = (MapAttrs) ((tempVar instanceof MapAttrs) ? tempVar : null);
		if (obj == null) {
			obj = new MapAttrs(this.getNo());
			this.SetRefObject("MapAttrs", obj);
		}
		return obj;
	}

	public static boolean getIsEditDtlModel() {
		String s = BP.Port.WebUser.GetSessionByKey("IsEditDtlModel", "0");
		if (s.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	public static void setIsEditDtlModel(boolean value) {
		BP.Port.WebUser.SetSessionByKey("IsEditDtlModel", "1");
	}

	public final String getPTable() {
		String s = this.GetValStrByKey(MapDataAttr.PTable);
		if (s.equals("") || s == null) {
			return this.getNo();
		}
		return s;
	}

	public final void setPTable(String value) {
		this.SetValByKey(MapDataAttr.PTable, value);
	}

	public final String getUrl() {
		return this.GetValStrByKey(MapDataAttr.Url);
	}

	public final void setUrl(String value) {
		this.SetValByKey(MapDataAttr.Url, value);
	}

	public final DBUrlType getHisDBUrl() {
		return DBUrlType.forValue(this.GetValIntByKey(MapDataAttr.DBURL));
	}

	public final int getHisFrmTypeInt() {
		return this.GetValIntByKey(MapDataAttr.FrmType);
	}

	public final void setHisFrmTypeInt(int value) {
		this.SetValByKey(MapDataAttr.FrmType, value);
	}

	public final FrmType getHisFrmType() {
		return FrmType.forValue(this.GetValIntByKey(MapDataAttr.FrmType));
	}

	public final void setHisFrmType(FrmType value) {
		this.SetValByKey(MapDataAttr.FrmType, value.getValue());
	}

	public final AppType getHisAppType() {
		return AppType.forValue(this.GetValIntByKey(MapDataAttr.AppType));
	}

	public final void setHisAppType(AppType value) {
		this.SetValByKey(MapDataAttr.AppType, value.getValue());
	}

	public final String getNote() {
		return this.GetValStrByKey(MapDataAttr.Note);
	}

	public final void setNote(String value) {
		this.SetValByKey(MapDataAttr.Note, value);
	}

	public final boolean getIsHaveCA() {
		return this.GetParaBoolen("IsHaveCA", false);

	}

	public final void setIsHaveCA(boolean value) {
		this.SetPara("IsHaveCA", value);
	}

	public final String getFK_FrmSort() {
		return this.GetValStrByKey(MapDataAttr.FK_FrmSort);
	}

	public final void setFK_FrmSort(String value) {
		this.SetValByKey(MapDataAttr.FK_FrmSort, value);
	}

	public final String getFK_FormTree() {
		return this.GetValStrByKey(MapDataAttr.FK_FormTree);
	}

	public final void setFK_FormTree(String value) {
		this.SetValByKey(MapDataAttr.FK_FormTree, value);
	}

	public final String getDtls() {
		return this.GetValStrByKey(MapDataAttr.Dtls);
	}

	public final void setDtls(String value) {
		this.SetValByKey(MapDataAttr.Dtls, value);
	}

	public final String getEnPK() {
		String s = this.GetValStrByKey(MapDataAttr.EnPK);
		if (StringHelper.isNullOrEmpty(s)) {
			return "OID";
		}
		return s;
	}

	public final void setEnPK(String value) {
		this.SetValByKey(MapDataAttr.EnPK, value);
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

	public final float getFrmW() {
		return this.GetValFloatByKey(MapDataAttr.FrmW);
	}

	public final void setFrmW(float value) {
		this.SetValByKey(MapDataAttr.FrmW, value);
	}

	public final float getFrmH() {
		return this.GetValFloatByKey(MapDataAttr.FrmH);
	}

	public final void setFrmH(float value) {
		this.SetValByKey(MapDataAttr.FrmH, value);
	}

	public final int getTableCol() {
		int i = this.GetValIntByKey(MapDataAttr.TableCol);
		if (i == 0 || i == 1) {
			return 4;
		}
		return i;
	}

	public final void setTableCol(int value) {
		this.SetValByKey(MapDataAttr.TableCol, value);
	}

	public final String getTableWidth() {
		// switch (this.TableCol)
		// {
		// case 2:
		// return
		// labCol = 25;
		// ctrlCol = 75;
		// break;
		// case 4:
		// labCol = 20;
		// ctrlCol = 30;
		// break;
		// case 6:
		// labCol = 15;
		// ctrlCol = 30;
		// break;
		// case 8:
		// labCol = 10;
		// ctrlCol = 15;
		// break;
		// default:
		// break;
		// }

		int i = this.GetValIntByKey(MapDataAttr.TableWidth);
		if (i <= 50) {
			return "100%";
		}
		return i + "px";
	}

	public final Map GenerHisMap() {
		MapAttrs mapAttrs = this.getMapAttrs();
		if (mapAttrs.size() == 0) {
			this.RepairMap();
			mapAttrs = this.getMapAttrs();
		}

		Map map = new Map(this.getPTable());
		DBUrl u = new DBUrl(this.getHisDBUrl());
		map.setEnDBUrl(u);
		map.setEnDesc(this.getName());
		map.setEnType(EnType.App);
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);

		// xiaozhoupeng 20150126 update Start
		// Attrs attrs = new Attrs();
		// for (Object mapAttr : mapAttrs)
		for (MapAttr mapAttr : MapAttrs.convertMapAttrs(mapAttrs)) {

			// logger.debug(((MapAttr)mapAttr).getHisAttr().getKey());
			// logger.debug(((MapAttr)mapAttr).getHisAttr().getField());
			// logger.debug(((MapAttr)mapAttr).getHisAttr().getDesc());
			// if(!((MapAttr)mapAttr).getHisAttr().getKey().equals(""))
			map.AddAttr(mapAttr.getHisAttr());
		}

		MapDtls dtls = this.getMapDtls(); // new MapDtls(this.No);
		// for (Object dtl : dtls)
		for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
			GEDtls dtls1 = new GEDtls(dtl.getNo());
			map.AddDtl(dtls1, "RefPK");
		}
		// xiaozhoupeng 20150126 update End

		map.IsShowSearchKey = this.getRptIsSearchKey();
		map.DTSearchWay = this.getRptDTSearchWay();
		map.DTSearchKey = this.getRptDTSearchKey();

		String[] keys = this.getRptSearchKeys().split("[*]", -1);
		for (String key : keys) {
			if (StringHelper.isNullOrEmpty(key)) {
				continue;
			}

			map.AddSearchAttr(key);
		}

		return map;
	}

	private GEEntity _HisEn = null;

	public final GEEntity getHisGEEn() {
		if (this._HisEn == null) {
			_HisEn = new GEEntity(this.getNo());
		}
		return _HisEn;
	}

	public final GEEntity GenerGEEntityByDataSet(DataSet ds) {
		GEEntity en = this.getHisGEEn();

		DataTable dt = ds.Tables.get(Integer.parseInt(this.getNo()));

		en.getRow().LoadDataTable(dt, dt.Rows.get(0));

		MapDtls dtls = this.getMapDtls();
		for (Object item : dtls) {
			DataTable dtDtls = ds.Tables.get(Integer.parseInt(((MapDtl) item).getNo()));
			GEDtls dtlsEn = new GEDtls(((MapDtl) item).getNo());
			for (DataRow dr : dtDtls.Rows) {
				GEDtl dtl = (GEDtl) dtlsEn.getGetNewEntity();
				dtl.getRow().LoadDataTable(dtDtls, dr);
				dtlsEn.AddEntity(dtl);
			}

			en.getDtls().add(dtDtls);
		}
		return en;
	}

	public static Map GenerHisMap(String no) {
		if (SystemConfig.getIsDebug()) {
			MapData md = new MapData();
			md.setNo(no);
			md.Retrieve();
			return md.GenerHisMap();
		} else {
			Map map = BP.DA.Cash.GetMap(no);
			if (map == null) {
				MapData md = new MapData();
				md.setNo(no);
				md.Retrieve();
				map = md.GenerHisMap();
				BP.DA.Cash.SetMap(no, map);
			}
			return map;
		}
	}

	/**
	 * 映射基础
	 * 
	 */
	public MapData() {
	}

	/**
	 * 映射基础
	 * 
	 * @param no
	 *            映射编号
	 */
	public MapData(String no) {
		super(no);
	}

	/**
	 * EnMap
	 * 
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}
		Map map = new Map("Sys_MapData");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("映射基础");
		map.setEnType(EnType.Sys);
		map.setCodeStruct("4");

		map.AddTBStringPK(MapDataAttr.No, null, "编号", true, false, 1, 20, 20);
		map.AddTBString(MapDataAttr.Name, null, "描述", true, false, 0, 500, 20);
		map.AddTBString(MapDataAttr.EnPK, null, "实体主键", true, false, 0, 10, 20);
		map.AddTBString(MapDataAttr.PTable, null, "物理表", true, false, 0, 500, 20);
		map.AddTBString(MapDataAttr.Url, null, "连接(对自定义表单有效)", true, false, 0, 500, 20);
		map.AddTBString(MapDataAttr.Dtls, null, "从表", true, false, 0, 500, 20);

		// 格式为: @1=方案名称1@2=方案名称2@3=方案名称3
		// map.AddTBString(MapDataAttr.Slns, null, "表单控制解决方案", true, false, 0,
		// 500, 20);

		map.AddTBInt(MapDataAttr.FrmW, 900, "FrmW", true, true);
		map.AddTBInt(MapDataAttr.FrmH, 1200, "FrmH", true, true);

		map.AddTBInt(MapDataAttr.TableCol, 4, "傻瓜表单显示的列", true, true);
		map.AddTBInt(MapDataAttr.TableWidth, 600, "表格宽度", true, true);

		// 数据源.
		map.AddTBInt(MapDataAttr.DBURL, 0, "DBURL", true, false);

		// Tag
		map.AddTBString(MapDataAttr.Tag, null, "Tag", true, false, 0, 500, 20);

		// FrmType @自由表单，@傻瓜表单，@自定义表单.
		map.AddTBInt(MapDataAttr.FrmType, 0, "表单类型", true, false);

		// 可以为空这个字段。
		map.AddTBString(MapDataAttr.FK_FrmSort, null, "表单类别", true, false, 0, 500, 20);
		map.AddTBString(MapDataAttr.FK_FormTree, null, "表单树类别", true, false, 0, 500, 20);

		// enumAppType
		map.AddTBInt(MapDataAttr.AppType, 1, "应用类型", true, false);

		map.AddTBString(MapDataAttr.Note, null, "备注", true, false, 0, 500, 20);
		map.AddTBString(MapDataAttr.Designer, null, "设计者", true, false, 0, 500, 20);
		map.AddTBString(MapDataAttr.DesignerUnit, null, "单位", true, false, 0, 500, 20);
		map.AddTBString(MapDataAttr.DesignerContact, null, "联系方式", true, false, 0, 500, 20);

		// 增加参数字段.
		map.AddTBAtParas(4000);

		map.AddTBInt(MapDataAttr.Idx, 100, "顺序号", true, true);
		map.AddTBString(MapDataAttr.GUID, null, "GUID", true, false, 0, 128, 20);
		map.AddTBString(MapDataAttr.Ver, null, "版本号", true, false, 0, 30, 20);
		this.set_enMap(map);
		return this.get_enMap();
	}

	public final void DoUp() {
		try {
			this.DoOrderUp(MapDataAttr.FK_FormTree, this.getFK_FormTree(), MapDataAttr.Idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 下移
	 * 
	 */
	public final void DoOrderDown() {
		try {
			this.DoOrderDown(MapDataAttr.FK_FormTree, this.getFK_FormTree(), MapDataAttr.Idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MapData ImpMapData(DataSet ds) {
		try {
			return ImpMapData(ds, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static MapData ImpMapData(DataSet ds, boolean isSetReadony) throws Exception {
		String errMsg = "";
		if (ds.Tables.contains("WF_Flow")) {
			errMsg += "@此模板文件为流程模板。";
		}

		if (!ds.Tables.contains("Sys_MapAttr")) {
			errMsg += "@缺少表:Sys_MapAttr";
		}

		if (!ds.Tables.contains("Sys_MapData")) {
			errMsg += "@缺少表:Sys_MapData";
		}
		if (!errMsg.equals("")) {
			throw new RuntimeException(errMsg);
		}

		DataTable dt = ds.hashTables.get("Sys_MapData");
		String fk_mapData = dt.Rows.get(0).get("No").toString();
		MapData md = new MapData();
		md.setNo(fk_mapData);
		if (md.getIsExits()) {
			throw new RuntimeException("已经存在(" + fk_mapData + ")的数据。");
		}

		return ImpMapData(fk_mapData, ds, isSetReadony);
	}

	/**
	 * 导入表单
	 * 
	 * @param fk_mapdata
	 *            表单ID
	 * @param ds
	 *            表单数据
	 * @param isSetReadonly
	 *            是否设置只读？
	 * @return
	 */
	public static MapData ImpMapData(String fk_mapdata, DataSet ds, boolean isSetReadonly) {
		String errMsg = "";
		// if (ds.Tables[0].TableName != "Sys_MapData")
		// errMsg += "@非表单模板。";

		if (ds.Tables.contains("WF_Flow")) {
			errMsg += "@此模板文件为流程模板。";
		}

		// if (!ds.Tables.contains("Sys_MapAttr"))
		// {
		// errMsg += "@缺少表:Sys_MapAttr";
		// }
		//
		// if (!ds.Tables.contains("Sys_MapData"))
		// {
		// errMsg += "@缺少表:Sys_MapData";
		// }

		DataTable dtCheck = ds.hashTables.get("Sys_MapAttr");
		boolean isHave = true;// false;
		for (DataRow dr : dtCheck.Rows) {
			if (dr.getValue("KeyOfEn").toString().equals("OID")) {
				isHave = true;
				break;
			}
		}

		if (!isHave) {
			errMsg += "@缺少列:OID";
		}

		// if (!errMsg.equals(""))
		// {
		// throw new RuntimeException("以下错误不可导入，可能的原因是非表单模板文件:" + errMsg);
		// }
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion

		// 定义在最后执行的sql.
		String endDoSQL = "";

		// 检查是否存在OID字段.
		MapData mdOld = new MapData();
		mdOld.setNo(fk_mapdata);
		mdOld.RetrieveFromDBSources();
		mdOld.Delete();

		// 求出dataset的map.
		String oldMapID = "";
		DataTable dtMap = ds.hashTables.get("Sys_MapData");
		for (DataRow dr : dtMap.Rows) {
			if (dr.getValue("No").toString().contains("Dtl")) {
				continue;
			}
			oldMapID = dr.getValue("No").toString();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("MMddhhmmss");

		String timeKey = formatter.format(new Date());
		// string timeKey = fk_mapdata;
		for (DataTable dt : ds.Tables) {
			int idx = 0;
			if (dt.TableName.equals("Sys_MapDtl")) {
				for (DataRow dr : dt.Rows) {
					MapDtl dtl = new MapDtl();
					logger.debug(dt.Columns.size() + "");
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							dtl.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					if (isSetReadonly) {
						// dtl.IsReadonly = true;

						dtl.setIsInsert(false);
						dtl.setIsUpdate(false);
						dtl.setIsDelete(false);
					}

					dtl.Insert();
				}
			} else if (dt.TableName.equals("Sys_MapData")) {
				for (DataRow dr : dt.Rows) {
					MapData md = new MapData();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							md.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
						// md.SetValByKey(dc.ColumnName, val);
					}
					if (StringHelper.isNullOrEmpty(md.getPTable().trim())) {
						md.setPTable(md.getNo());
					}

					if (!StringHelper.isNullOrEmpty(mdOld.getFK_FormTree())) {
						md.setFK_FormTree(mdOld.getFK_FormTree());
					}

					if (!StringHelper.isNullOrEmpty(mdOld.getFK_FrmSort())) {
						md.setFK_FrmSort(mdOld.getFK_FrmSort());
					}

					if (!StringHelper.isNullOrEmpty(mdOld.getPTable())) {
						md.setPTable(mdOld.getPTable());
					}

					try {
						md.DirectInsert();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// ORIGINAL LINE: case "Sys_FrmBtn":
			else if (dt.TableName.equals("Sys_FrmBtn")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmBtn en = new FrmBtn();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					if (isSetReadonly) {
						en.setIsEnable(false);
					}

					en.setMyPK("Btn_" + idx + "_" + fk_mapdata);
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmLine":
			else if (dt.TableName.equals("Sys_FrmLine")) {
				try {
					for (DataRow dr : dt.Rows) {
						idx++;
						FrmLine en = new FrmLine();
						for (DataColumn dc : dt.Columns) {
							String val = dr.getValue(dc.ColumnName) == null ? ""
									: String.valueOf(dr.getValue(dc.ColumnName));
							if (!StringHelper.isNullOrEmpty(val)) {
								en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
							}
						}
						en.setMyPK("LE_" + idx + "_" + fk_mapdata);
						en.Insert();
					}
				} catch (Exception e) {

				}
			}
			// ORIGINAL LINE: case "Sys_FrmLab":
			else if (dt.TableName.equals("Sys_FrmLab")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmLab en = new FrmLab();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					// en.FK_MapData = fk_mapdata; 删除此行解决从表lab的问题。
					en.setMyPK("LB_" + idx + "_" + fk_mapdata);
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmLink":
			else if (dt.TableName.equals("Sys_FrmLink")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmLink en = new FrmLink();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					en.setMyPK("LK_" + idx + "_" + fk_mapdata);
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmEle":
			else if (dt.TableName.equals("Sys_FrmEle")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmEle en = new FrmEle();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					if (isSetReadonly) {
						en.setIsEnable(false);
					}

					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmImg":
			else if (dt.TableName.equals("Sys_FrmImg")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmImg en = new FrmImg();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					en.setMyPK("Img_" + idx + "_" + fk_mapdata);
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmImgAth":
			else if (dt.TableName.equals("Sys_FrmImgAth")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmImgAth en = new FrmImgAth();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}

					if (StringHelper.isNullOrEmpty(en.getCtrlID())) {
						en.setCtrlID("ath" + idx);
					}

					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmRB":
			else if (dt.TableName.equals("Sys_FrmRB")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmRB en = new FrmRB();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}

					try {
						en.Save();
					} catch (java.lang.Exception e) {
					}
				}
			}
			// ORIGINAL LINE: case "Sys_FrmAttachment":
			else if (dt.TableName.equals("Sys_FrmAttachment")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmAttachment en = new FrmAttachment();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					en.setMyPK("Ath_" + idx + "_" + fk_mapdata);
					if (isSetReadonly) {
						en.setIsDelete(false);
						en.setIsUpload(false);
					}

					try {
						en.Insert();
					} catch (java.lang.Exception e2) {
					}
				}
			}
			// ORIGINAL LINE: case "Sys_MapM2M":
			else if (dt.TableName.equals("Sys_MapM2M")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					MapM2M en = new MapM2M();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					// en.NoOfObj = "M2M_" + idx + "_" + fk_mapdata;
					if (isSetReadonly) {
						en.setIsDelete(false);
						en.setIsInsert(false);
					}
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_MapFrame":
			else if (dt.TableName.equals("Sys_MapFrame")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					MapFrame en = new MapFrame();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					en.setNoOfObj("Fra_" + idx + "_" + fk_mapdata);
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_MapExt":
			else if (dt.TableName.equals("Sys_MapExt")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					MapExt en = new MapExt();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					try {
						en.Insert();
					} catch (java.lang.Exception e3) {
						en.setMyPK("Ext_" + idx + "_" + fk_mapdata);
						en.Insert();
					}
				}
			}
			// ORIGINAL LINE: case "Sys_MapAttr":
			else if (dt.TableName.equals("Sys_MapAttr")) {
				for (DataRow dr : dt.Rows) {
					MapAttr en = new MapAttr();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}

					if (isSetReadonly) {
						if (!StringHelper.isNullOrEmpty(en.getDefValReal())
								&& (en.getDefValReal().contains("@WebUser.") || en.getDefValReal().contains("@RDT"))) {
							en.setDefValReal("");
						}

						switch (en.getUIContralType()) {
						case DDL:
							en.setUIIsEnable(false);
							break;
						case TB:
							en.setUIIsEnable(false);
							break;
						case RadioBtn:
							en.setUIIsEnable(false);
							break;
						case CheckBok:
							en.setUIIsEnable(false);
							break;
						default:
							break;
						}
					}
					en.setMyPK(en.getFK_MapData() + "_" + en.getKeyOfEn());
					try {
						en.DirectInsert();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// ORIGINAL LINE: case "Sys_GroupField":
			else if (dt.TableName.equals("Sys_GroupField")) {
				for (DataRow dr : dt.Rows) {
					idx++;
					GroupField en = new GroupField();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							en.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					int beforeID = (int) en.getOID();
					en.setOID(0);
					en.Insert();
					endDoSQL += "@UPDATE Sys_MapAttr SET GroupID=" + en.getOID() + " WHERE FK_MapData='" + fk_mapdata
							+ "' AND GroupID=" + beforeID;
				}
			}
			// ORIGINAL LINE: case "Sys_Enum":
			else if (dt.TableName.equals("Sys_Enum")) {
				for (DataRow dr : dt.Rows) {
					BP.Sys.SysEnum se = new BP.Sys.SysEnum();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						se.SetValByKey(dc.ColumnName, val);
					}
					se.setMyPK(se.getEnumKey() + "_" + se.getLang() + "_" + se.getIntKey());
					if (se.getIsExits()) {
						continue;
					}
					se.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_EnumMain":
			else if (dt.TableName.equals("Sys_EnumMain")) {
				for (DataRow dr : dt.Rows) {
					BP.Sys.SysEnumMain sem = new BP.Sys.SysEnumMain();
					for (DataColumn dc : dt.Columns) {
						String val = dr.getValue(dc.ColumnName) == null ? ""
								: String.valueOf(dr.getValue(dc.ColumnName));
						if (!StringHelper.isNullOrEmpty(val)) {
							sem.SetValByKey(dc.ColumnName, val.toString().replace(oldMapID, fk_mapdata));
						}
					}
					if (sem.getIsExits()) {
						continue;
					}
					sem.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_Node":
			else if (dt.TableName.equals("WF_Node")) {
				if (dt.Rows.size() > 0) {
					endDoSQL += "@UPDATE WF_Node SET FWCSta=2" + ",FWC_X=" + dt.Rows.get(0).get("FWC_X") + ",FWC_Y="
							+ dt.Rows.get(0).get("FWC_Y") + ",FWC_H=" + dt.Rows.get(0).get("FWC_H") + ",FWC_W="
							+ dt.Rows.get(0).get("FWC_W") + ",FWCType=" + dt.Rows.get(0).get("FWCType")
							+ " WHERE NodeID=" + fk_mapdata.replace("ND", "");
				}
			} else {
			}
		}
		// 执行最后结束的sql.
		try {
			DBAccess.RunSQLs(endDoSQL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MapData mdNew = new MapData(fk_mapdata);
		mdNew.RepairMap();
		// mdNew.FK_FrmSort = fk_sort;
		mdNew.Update();
		return mdNew;
	}

	public final void RepairMap() {
		GroupFields gfs = new GroupFields(this.getNo());
		if (gfs.size() == 0) {
			GroupField gf = new GroupField();
			gf.setEnName(this.getNo());
			gf.setLab(this.getName());
			gf.Insert();
			String sqls = "";
			sqls += "@UPDATE Sys_MapDtl SET GroupID=" + gf.getOID() + " WHERE FK_MapData='" + this.getNo() + "'";
			sqls += "@UPDATE Sys_MapAttr SET GroupID=" + gf.getOID() + " WHERE FK_MapData='" + this.getNo() + "'";
			sqls += "@UPDATE Sys_MapFrame SET GroupID=" + gf.getOID() + " WHERE FK_MapData='" + this.getNo() + "'";
			sqls += "@UPDATE Sys_MapM2M SET GroupID=" + gf.getOID() + " WHERE FK_MapData='" + this.getNo() + "'";
			sqls += "@UPDATE Sys_FrmAttachment SET GroupID=" + gf.getOID() + " WHERE FK_MapData='" + this.getNo() + "'";
			try {
				DBAccess.RunSQLs(sqls);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			GroupField gfFirst = (GroupField) ((gfs.get(0) instanceof GroupField) ? gfs.get(0) : null);
			String sqls = "";
			sqls += "@UPDATE Sys_MapDtl SET GroupID=" + gfFirst.getOID()
					+ "        WHERE  No   IN (SELECT X.No FROM (SELECT No FROM Sys_MapDtl WHERE GroupID NOT IN (SELECT OID FROM Sys_GroupField WHERE EnName='"
					+ this.getNo() + "')) AS X ) AND FK_MapData='" + this.getNo() + "'";
			sqls += "@UPDATE Sys_MapAttr SET GroupID=" + gfFirst.getOID()
					+ "       WHERE  MyPK IN (SELECT X.MyPK FROM (SELECT MyPK FROM Sys_MapAttr       WHERE GroupID NOT IN (SELECT OID FROM Sys_GroupField WHERE EnName='"
					+ this.getNo() + "')) AS X) AND FK_MapData='" + this.getNo() + "' ";
			sqls += "@UPDATE Sys_MapFrame SET GroupID=" + gfFirst.getOID()
					+ "      WHERE  MyPK IN (SELECT X.MyPK FROM (SELECT MyPK FROM Sys_MapFrame      WHERE GroupID NOT IN (SELECT OID FROM Sys_GroupField WHERE EnName='"
					+ this.getNo() + "')) AS X) AND FK_MapData='" + this.getNo() + "' ";
			sqls += "@UPDATE Sys_MapM2M SET GroupID=" + gfFirst.getOID()
					+ "        WHERE  MyPK IN (SELECT X.MyPK FROM (SELECT MyPK FROM Sys_MapM2M        WHERE GroupID NOT IN (SELECT OID FROM Sys_GroupField WHERE EnName='"
					+ this.getNo() + "')) AS X) AND FK_MapData='" + this.getNo() + "' ";
			sqls += "@UPDATE Sys_FrmAttachment SET GroupID=" + gfFirst.getOID()
					+ " WHERE  MyPK IN (SELECT X.MyPK FROM (SELECT MyPK FROM Sys_FrmAttachment WHERE GroupID NOT IN (SELECT OID FROM Sys_GroupField WHERE EnName='"
					+ this.getNo() + "')) AS X) AND FK_MapData='" + this.getNo() + "' ";

			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			/// #warning 这些sql 对于Oracle 有问题，但是不影响使用.
			try {
				DBAccess.RunSQLs(sqls);
			} catch (java.lang.Exception e) {

			}
		}

		BP.Sys.Frm.MapAttr attr = new BP.Sys.Frm.MapAttr();
		if (this.getEnPK().equals("OID")) {
			if (!attr.IsExit(MapAttrAttr.KeyOfEn, "OID", MapAttrAttr.FK_MapData, this.getNo())) {
				attr.setFK_MapData(this.getNo());
				attr.setKeyOfEn("OID");
				attr.setName("OID");
				attr.setMyDataType(BP.DA.DataType.AppInt);
				attr.setUIContralType(UIContralType.TB);
				attr.setLGType(FieldTypeS.Normal);
				attr.setUIVisible(false);
				attr.setUIIsEnable(false);
				attr.setDefVal("0");
				attr.setHisEditType(BP.En.EditType.Readonly);
				attr.Insert();
			}
		}
		if (this.getEnPK().equals("No") || this.getEnPK().equals("MyPK")) {
			if (!attr.IsExit(MapAttrAttr.KeyOfEn, this.getEnPK(), MapAttrAttr.FK_MapData, this.getNo())) {
				attr.setFK_MapData(this.getNo());
				attr.setKeyOfEn(this.getEnPK());
				attr.setName(this.getEnPK());
				attr.setMyDataType(BP.DA.DataType.AppInt);
				attr.setUIContralType(UIContralType.TB);
				attr.setLGType(FieldTypeS.Normal);
				attr.setUIVisible(false);
				attr.setUIIsEnable(false);
				attr.setDefVal("0");
				attr.setHisEditType(BP.En.EditType.Readonly);
				attr.Insert();
			}
		}

		if (!attr.IsExit(MapAttrAttr.KeyOfEn, "RDT", MapAttrAttr.FK_MapData, this.getNo())) {
			attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(this.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn("RDT");
			attr.setName("更新时间");

			attr.setMyDataType(BP.DA.DataType.AppDateTime);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setDefVal("@RDT");
			attr.setTag("1");
			attr.Insert();
		}
	}

	@Override
	public boolean beforeInsert() {
		this.setPTable(PubClass.DealToFieldOrTableNames(this.getPTable()));
		return super.beforeInsert();
	}

	/**
	 * 设置Para 参数.
	 * 
	 */
	public final void ResetMaxMinXY() {
		float i1 = 0;
		try {
			i1 = DBAccess
					.RunSQLReturnValFloat("SELECT MIN(X1) FROM Sys_FrmLine WHERE FK_MapData='" + this.getNo() + "'", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (i1 == 0) {
			try {
				i1 = DBAccess.RunSQLReturnValFloat(
						"SELECT MIN(X) FROM Sys_FrmImg WHERE FK_MapData='" + this.getNo() + "'", 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		float i2 = 0;
		try {
			i2 = DBAccess
					.RunSQLReturnValFloat("SELECT MIN(X)  FROM Sys_FrmLab  WHERE FK_MapData='" + this.getNo() + "'", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i1 > i2) {
			this.setMaxLeft(i2);
		} else {
			this.setMaxLeft(i1);
		}

		// 求最右边.
		try {
			i1 = DBAccess
					.RunSQLReturnValFloat("SELECT Max(X2) FROM Sys_FrmLine WHERE FK_MapData='" + this.getNo() + "'", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (i1 == 0) {
			// 没有线的情况，按照图片来计算。
			try {
				i1 = DBAccess.RunSQLReturnValFloat(
						"SELECT Max(X+W) FROM Sys_FrmImg WHERE FK_MapData='" + this.getNo() + "'", 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setMaxRight(i1);

		// 求最top.
		try {
			i1 = DBAccess
					.RunSQLReturnValFloat("SELECT MIN(Y1) FROM Sys_FrmLine WHERE FK_MapData='" + this.getNo() + "'", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			i2 = DBAccess
					.RunSQLReturnValFloat("SELECT MIN(Y)  FROM Sys_FrmLab  WHERE FK_MapData='" + this.getNo() + "'", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (i1 > i2) {
			this.setMaxTop(i2);
		} else {
			this.setMaxTop(i1);
		}

		try {
			i1 = DBAccess
					.RunSQLReturnValFloat("SELECT Max(Y1) FROM Sys_FrmLine WHERE FK_MapData='" + this.getNo() + "'", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i1 == 0) {
			try {
				i1 = DBAccess.RunSQLReturnValFloat(
						"SELECT Max(Y+H) FROM Sys_FrmImg WHERE FK_MapData='" + this.getNo() + "'", 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 小周鹏添加2014/10/23-----------------------START
		try {
			i2 = DBAccess
					.RunSQLReturnValFloat("SELECT Max(Y)  FROM Sys_FrmLab  WHERE FK_MapData='" + this.getNo() + "'", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (i2 == 0) {
			try {
				i2 = DBAccess.RunSQLReturnValFloat(
						"SELECT Max(Y+H) FROM Sys_FrmImg WHERE FK_MapData='" + this.getNo() + "'", 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (i1 > i2) {
			this.setMaxEnd(i1);
		} else {
			this.setMaxEnd(i2);
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion

		this.DirectUpdate();
	}

	/**
	 * 求位移.
	 * 
	 * @param md
	 * @param scrWidth
	 * @return
	 */
	public static float GenerSpanWeiYi(MapData md, float scrWidth) {
		if (scrWidth == 0) {
			scrWidth = 900;
		}

		float left = md.getMaxLeft();
		if (left == 0) {
			md.ResetMaxMinXY();
			md.RetrieveFromDBSources();
			md.Retrieve();

			left = md.getMaxLeft();
		}

		float right = md.getMaxRight();
		float withFrm = right - left;
		if (withFrm >= scrWidth) {
			return -left;
		}
		float space = (scrWidth - withFrm) / 2; // �հ׵ĵط�.

		return -(left - space);
	}

	public static float GenerSpanWidth(MapData md, float scrWidth) {
		if (scrWidth == 0) {
			scrWidth = 900;
		}
		float left = md.getMaxLeft();
		if (left == 0) {
			md.ResetMaxMinXY();
			left = md.getMaxLeft();
		}

		float right = md.getMaxRight();
		float withFrm = right - left;
		if (withFrm >= scrWidth) {
			return withFrm;
		}
		return scrWidth;
	}

	public static float GenerSpanHeight(MapData md, float scrHeight) {
		if (scrHeight == 0) {
			scrHeight = 1200;
		}

		float end = md.getMaxEnd();
		if (end > scrHeight) {
			return end + 10;
		} else {
			return scrHeight;
		}
	}

	@Override
	public boolean beforeUpdateInsertAction() {
		this.setPTable(PubClass.DealToFieldOrTableNames(this.getPTable()));
		getMapAttrs().Retrieve(MapAttrAttr.FK_MapData, getPTable());

		// 更新版本号.
		this.setVer(DataType.getCurrentDataTimess());

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 检查是否有ca认证设置.
		boolean isHaveCA = false;
		for (Object item : this.getMapAttrs()) {
			if (((MapAttr) item).getSignType() == SignType.CA) {
				isHaveCA = true;
				break;
			}
		}
		this.setIsHaveCA(isHaveCA);
		if (getIsHaveCA()) {
			// MapAttr attr = new BP.Sys.MapAttr();
			// attr.MyPK = this.No + "_SealData";
			// attr.FK_MapData = this.No;
			// attr.HisEditType = BP.En.EditType.UnDel;
			// attr.KeyOfEn = "SealData";
			// attr.Name = "SealData";
			// attr.MyDataType = BP.DA.DataType.AppString;
			// attr.UIContralType = UIContralType.TB;
			// attr.LGType = FieldTypeS.Normal;
			// attr.UIVisible = false;
			// attr.UIIsEnable = false;
			// attr.MaxLen = 4000;
			// attr.MinLen = 0;
			// attr.Save();
		}

		return super.beforeUpdateInsertAction();
	}

	/**
	 * 更新版本
	 * 
	 */
	public final void UpdateVer() {
		String sql = "UPDATE Sys_MapData SET VER='" + BP.DA.DataType.getCurrentDataTimess() + "' WHERE No='"
				+ this.getNo() + "'";
		BP.DA.DBAccess.RunSQL(sql);
	}

	@Override
	public boolean beforeDelete() {
		String sql = "";
		sql = "SELECT * FROM Sys_MapDtl WHERE FK_MapData ='" + this.getNo() + "'";
		DataTable Sys_MapDtl = DBAccess.RunSQLReturnTable(sql);
		String ids = "'" + this.getNo() + "'";
		for (DataRow dr : Sys_MapDtl.Rows) {
			ids += ",'" + dr.getValue("No") + "'";
		}

		String where = " FK_MapData IN (" + ids + ")";

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 删除相关的数据。
		sql += "@DELETE FROM Sys_MapDtl WHERE FK_MapData='" + this.getNo() + "'";
		sql += "@DELETE FROM Sys_FrmLine WHERE " + where;
		sql += "@DELETE FROM Sys_FrmEle WHERE " + where;
		sql += "@DELETE FROM Sys_FrmEvent WHERE " + where;
		sql += "@DELETE FROM Sys_FrmBtn WHERE " + where;
		sql += "@DELETE FROM Sys_FrmLab WHERE " + where;
		sql += "@DELETE FROM Sys_FrmLink WHERE " + where;
		sql += "@DELETE FROM Sys_FrmImg WHERE " + where;
		sql += "@DELETE FROM Sys_FrmImgAth WHERE " + where;
		sql += "@DELETE FROM Sys_FrmRB WHERE " + where;
		sql += "@DELETE FROM Sys_FrmAttachment WHERE " + where;
		sql += "@DELETE FROM Sys_MapM2M WHERE " + where;
		sql += "@DELETE FROM Sys_MapFrame WHERE " + where;
		sql += "@DELETE FROM Sys_MapExt WHERE " + where;
		sql += "@DELETE FROM Sys_MapAttr WHERE " + where;
		sql += "@DELETE FROM Sys_GroupField WHERE EnName IN (" + ids + ")";
		sql += "@DELETE FROM Sys_MapData WHERE No IN (" + ids + ")";
		sql += "@DELETE FROM Sys_MapM2M WHERE " + where;
		sql += "@DELETE FROM Sys_M2M WHERE " + where;
		try {
			DBAccess.RunSQLs(sql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 删除相关的数据。

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 删除物理表。
		try {
			BP.DA.DBAccess.RunSQL("DROP TABLE " + this.getPTable());
		} catch (java.lang.Exception e) {
		}

		MapDtls dtls = new MapDtls(this.getNo());
		for (Object dtl : dtls) {
			try {
				DBAccess.RunSQL("DROP TABLE " + ((MapDtl) dtl).getPTable());
			} catch (java.lang.Exception e2) {
			}
			((MapDtl) dtl).Delete();
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion

		return super.beforeDelete();
	}

	public static DataSet GenerHisDataSet(String FK_MapData) {

		// Sys_MapDtl.
		String sql = "SELECT * FROM Sys_MapDtl WHERE FK_MapData ='" + FK_MapData + "'";

		DataTable dtMapDtl = DBAccess.RunSQLReturnTable_UL(sql);
		dtMapDtl.TableName = "Sys_MapDtl";

		String ids = "'" + FK_MapData + "'";
		for (DataRow dr : dtMapDtl.Rows) {
			ids += ",'" + dr.getValue("No") + "'";
		}

		List<String> listNames = new ArrayList<String>();
		// Sys_GroupField.
		listNames.add("Sys_GroupField");
		sql = "SELECT * FROM Sys_GroupField WHERE  EnName IN (" + ids + ")";
		String sqls = sql;

		// Sys_Enum
		listNames.add("Sys_Enum");
		sql = "SELECT * FROM Sys_Enum WHERE EnumKey IN ( SELECT UIBindKey FROM Sys_MapAttr WHERE FK_MapData IN (" + ids
				+ ") )";
		sqls += ";" + sql;

		// 审核组件
		String nodeIDstr = FK_MapData.replace("ND", "");
		if (DataType.IsNumStr(nodeIDstr)) {
			// 审核组件状态:0 禁用;1 启用;2 只读;
			listNames.add("WF_Node");
			sql = "SELECT * FROM WF_Node WHERE NodeID=" + nodeIDstr + " AND  FWCSta in(1,2)";
			sqls += ";" + sql;
		}

		String where = " FK_MapData IN (" + ids + ")";

		// Sys_MapData.
		listNames.add("Sys_MapData");
		sql = "SELECT * FROM Sys_MapData WHERE No='" + FK_MapData + "'";
		sqls += ";" + sql;

		// Sys_MapAttr.
		listNames.add("Sys_MapAttr");
		sql = "SELECT * FROM Sys_MapAttr WHERE " + where + " AND KeyOfEn NOT IN('WFState') ORDER BY FK_MapData,IDX ";
		sqls += ";" + sql;

		// Sys_MapM2M.
		listNames.add("Sys_MapM2M");
		sql = "SELECT * FROM Sys_MapM2M WHERE " + where;
		sqls += ";" + sql;

		// Sys_MapExt.
		listNames.add("Sys_MapExt");
		sql = "SELECT * FROM Sys_MapExt WHERE " + where;
		sqls += ";" + sql;

		// line.
		listNames.add("Sys_FrmLine");
		sql = "SELECT * FROM Sys_FrmLine WHERE " + where;
		sqls += ";" + sql;

		// ele.
		listNames.add("Sys_FrmEle");
		sql = "SELECT * FROM Sys_FrmEle WHERE " + where;
		sqls += ";" + sql;

		// link.
		listNames.add("Sys_FrmLink");
		sql = "SELECT * FROM Sys_FrmLink WHERE " + where;
		sqls += ";" + sql;

		// btn.
		listNames.add("Sys_FrmBtn");
		sql = "SELECT * FROM Sys_FrmBtn WHERE " + where;
		sqls += ";" + sql;

		// Sys_FrmImg.
		listNames.add("Sys_FrmImg");
		sql = "SELECT * FROM Sys_FrmImg WHERE " + where;
		sqls += ";" + sql;

		// Sys_FrmLab.
		listNames.add("Sys_FrmLab");
		sql = "SELECT * FROM Sys_FrmLab WHERE " + where;
		sqls += ";" + sql;

		// Sys_FrmRB.
		listNames.add("Sys_FrmRB");
		sql = "SELECT * FROM Sys_FrmRB WHERE " + where;
		sqls += ";" + sql;

		// Sys_FrmAttachment.
		listNames.add("Sys_FrmAttachment");
		sql = "SELECT * FROM Sys_FrmAttachment WHERE " + where + " AND FK_Node=0";
		sqls += ";" + sql;

		// Sys_FrmImgAth.
		listNames.add("Sys_FrmImgAth");
		sql = "SELECT * FROM Sys_FrmImgAth WHERE " + where;
		sqls += ";" + sql;

		DataSet ds = DBAccess.RunSQLReturnDataSet_UL(sqls);
		if (ds != null && ds.Tables.size() == listNames.size())
			for (int i = 0; i < listNames.size(); i++) {
				try {
					ds.Tables.get(i).TableName = listNames.get(i);
				} catch (Exception e) {
				}
			}

		// string[] strs = sqls.Split(';');
		// DataSet ds = new DataSet();
		// for (int i = 0; i < strs.Length; i++)
		// {
		// sql = strs[i];
		// if (string.IsNullOrEmpty(sql))
		// continue;

		// DataTable dt = RunSQLReturnTable(sql);
		// string tableName = "DT" + i;
		// try
		// {
		// tableName = listNames[i];
		// //int indexStart = sql.IndexOf("From ",
		// StringComparison.OrdinalIgnoreCase) + 5;
		// //int indexEnd = sql.IndexOf(" WHERE",
		// StringComparison.OrdinalIgnoreCase) - indexStart;
		// //tableName = sql.Substring(indexStart, indexEnd);
		// }
		// catch (Exception) { }
		// dt.TableName = tableName;
		// ds.Tables.Add(dt);
		// }

		for (DataTable item : ds.Tables) {
			if (item.TableName == "Sys_MapAttr" && item.Rows.size() == 0) {
				MapAttr attr = new MapAttr();
				attr.setFK_MapData(FK_MapData);
				attr.setKeyOfEn("OID");
				attr.setName("OID");
				attr.setMyDataType(BP.DA.DataType.AppInt);
				attr.setUIContralType(UIContralType.TB);
				attr.setLGType(FieldTypeS.Normal);
				attr.setUIVisible(false);
				attr.setUIIsEnable(false);
				attr.setDefVal("0");
				attr.setHisEditType(EditType.Readonly);
				attr.Insert();
			}
		}

		ds.Tables.add(dtMapDtl);
		return ds;
	}

	public final DataSet GenerHisDataSet() {
		DataSet ds = new DataSet();
		String sql = "";

		// Sys_MapDtl.
		sql = "SELECT * FROM Sys_MapDtl WHERE FK_MapData ='" + this.getNo() + "'";
		DataTable Sys_MapDtl = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_MapDtl.TableName = "Sys_MapDtl";
		// ds.Tables.add(Sys_MapDtl);
		// ds.hashTables.put(Sys_MapDtl.TableName, Sys_MapDtl);
		String ids = "'" + this.getNo() + "'";
		for (DataRow dr : Sys_MapDtl.Rows) {
			ids += ",'" + dr.getValue("No") + "'";
		}
		String where = " FK_MapData IN (" + ids + ")";

		// Sys_GroupField.
		sql = "SELECT * FROM Sys_GroupField WHERE  EnName IN (" + ids + ")";
		DataTable Sys_GroupField = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_GroupField.TableName = "Sys_GroupField";
		ds.Tables.add(Sys_GroupField);
		ds.hashTables.put(Sys_GroupField.TableName, Sys_GroupField);

		// Sys_Enum
		sql = "SELECT * FROM Sys_Enum WHERE EnumKey IN ( SELECT UIBindKey FROM Sys_MapAttr WHERE FK_MapData IN (" + ids
				+ ") )";
		DataTable Sys_Enum = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_Enum.TableName = "Sys_Enum";
		ds.Tables.add(Sys_Enum);
		ds.hashTables.put("Sys_Enum", Sys_Enum);

		String nodeIDstr = this.getNo().replace("ND", "");
		if (DataType.IsNumStr(nodeIDstr)) {
			// sql = "SELECT NodeID,FWCSta,FWC_X,FWC_Y,FWC_H,FWC_W,FWCType,*
			// FROM WF_Node WHERE NodeID=" + nodeIDstr;
			sql = "SELECT * FROM WF_Node WHERE NodeID=" + nodeIDstr;
			DataTable WF_Node = DBAccess.RunSQLReturnTable_UL(sql);
			if (WF_Node.Rows.size() == 1) {
				if (WF_Node.Rows.get(0).get("FWCSta") == null
						|| !WF_Node.Rows.get(0).get("FWCSta").toString().equals("0")) {
					WF_Node.TableName = "WF_Node";
					ds.Tables.add(WF_Node);
					ds.hashTables.put(WF_Node.TableName, WF_Node);
				}
			}
		}

		// Sys_MapData.
		// sql = "SELECT * FROM Sys_MapData WHERE No IN (" + ids + ")";
		sql = "SELECT * FROM Sys_MapData WHERE No='" + this.getNo() + "'";
		DataTable Sys_MapData = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_MapData.TableName = "Sys_MapData";
		ds.Tables.add(Sys_MapData);
		ds.hashTables.put(Sys_MapData.TableName, Sys_MapData);

		// Sys_MapAttr.
		sql = "SELECT * FROM Sys_MapAttr WHERE " + where + " AND KeyOfEn NOT IN('WFState') ORDER BY FK_MapData,IDX ";
		DataTable Sys_MapAttr = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_MapAttr.TableName = "Sys_MapAttr";
		if (Sys_MapAttr.Rows.size() == 0) {
			BP.Sys.Frm.MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(this.getNo());
			attr.setKeyOfEn("OID");
			attr.setName("OID");
			attr.setMyDataType(BP.DA.DataType.AppInt);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setDefVal("0");
			attr.setHisEditType(BP.En.EditType.Readonly);
			attr.Insert();
		}
		ds.Tables.add(Sys_MapAttr);
		ds.hashTables.put(Sys_MapAttr.TableName, Sys_MapAttr);

		// Sys_MapM2M.
		sql = "SELECT * FROM Sys_MapM2M WHERE " + where;
		DataTable Sys_MapM2M = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_MapM2M.TableName = "Sys_MapM2M";
		ds.Tables.add(Sys_MapM2M);
		ds.hashTables.put(Sys_MapM2M.TableName, Sys_MapM2M);

		// Sys_MapExt.
		sql = "SELECT * FROM Sys_MapExt WHERE " + where;
		DataTable Sys_MapExt = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_MapExt.TableName = "Sys_MapExt";
		ds.Tables.add(Sys_MapExt);
		ds.hashTables.put(Sys_MapExt.TableName, Sys_MapExt);

		// line.
		sql = "SELECT * FROM Sys_FrmLine WHERE " + where;
		DataTable dtLine = DBAccess.RunSQLReturnTable_UL(sql);
		dtLine.TableName = "Sys_FrmLine";
		ds.Tables.add(dtLine);
		ds.hashTables.put(dtLine.TableName, dtLine);

		// ele.
		sql = "SELECT * FROM Sys_FrmEle WHERE " + where;
		DataTable dtFrmEle = DBAccess.RunSQLReturnTable_UL(sql);
		dtFrmEle.TableName = "Sys_FrmEle";
		ds.Tables.add(dtFrmEle);
		ds.hashTables.put(dtFrmEle.TableName, dtFrmEle);

		// link.
		sql = "SELECT * FROM Sys_FrmLink WHERE " + where;
		DataTable dtLink = DBAccess.RunSQLReturnTable_UL(sql);
		dtLink.TableName = "Sys_FrmLink";
		ds.Tables.add(dtLink);
		ds.hashTables.put(dtLink.TableName, dtLink);

		// btn.
		sql = "SELECT * FROM Sys_FrmBtn WHERE " + where;
		DataTable dtBtn = DBAccess.RunSQLReturnTable_UL(sql);
		dtBtn.TableName = "Sys_FrmBtn";
		ds.Tables.add(dtBtn);
		ds.hashTables.put(dtBtn.TableName, dtBtn);

		// Sys_FrmImg.
		sql = "SELECT * FROM Sys_FrmImg WHERE " + where;
		DataTable dtFrmImg = DBAccess.RunSQLReturnTable_UL(sql);
		dtFrmImg.TableName = "Sys_FrmImg";
		ds.Tables.add(dtFrmImg);
		ds.hashTables.put(dtFrmImg.TableName, dtFrmImg);

		// Sys_FrmLab.
		sql = "SELECT * FROM Sys_FrmLab WHERE " + where;
		DataTable Sys_FrmLab = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_FrmLab.TableName = "Sys_FrmLab";
		ds.Tables.add(Sys_FrmLab);
		ds.hashTables.put(Sys_FrmLab.TableName, Sys_FrmLab);

		// Sys_FrmRB.
		sql = "SELECT * FROM Sys_FrmRB WHERE " + where;
		DataTable Sys_FrmRB = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_FrmRB.TableName = "Sys_FrmRB";
		ds.Tables.add(Sys_FrmRB);
		ds.hashTables.put(Sys_FrmRB.TableName, Sys_FrmRB);

		// Sys_FrmAttachment.
		sql = "SELECT * FROM Sys_FrmAttachment WHERE " + where + " AND FK_Node=0";
		DataTable Sys_FrmAttachment = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_FrmAttachment.TableName = "Sys_FrmAttachment";
		ds.Tables.add(Sys_FrmAttachment);
		ds.hashTables.put(Sys_FrmAttachment.TableName, Sys_FrmAttachment);

		// Sys_FrmImgAth.
		sql = "SELECT * FROM Sys_FrmImgAth WHERE " + where;
		DataTable Sys_FrmImgAth = DBAccess.RunSQLReturnTable_UL(sql);
		Sys_FrmImgAth.TableName = "Sys_FrmImgAth";
		ds.Tables.add(Sys_FrmImgAth);
		ds.hashTables.put(Sys_FrmImgAth.TableName, Sys_FrmImgAth);

		ds.Tables.add(Sys_MapDtl);
		ds.hashTables.put(Sys_MapDtl.TableName, Sys_MapDtl);

		// // Sys_MapAttr.
		// sql = "SELECT * FROM Sys_MapAttr WHERE " + where + " AND KeyOfEn NOT
		// IN('WFState') ORDER BY FK_MapData,IDX ";
		// DataTable Sys_MapAttr = DBAccess.RunSQLReturnTable_UL(sql);
		// Sys_MapAttr.TableName = "Sys_MapAttr";
		// if (Sys_MapAttr.Rows.size() == 0)
		// {
		// BP.Sys.Frm.MapAttr attr = new BP.Sys.Frm.MapAttr();
		// attr.setFK_MapData(this.getNo());
		// attr.setKeyOfEn("OID");
		// attr.setName("OID");
		// attr.setMyDataType(BP.DA.DataType.AppInt);
		// attr.setUIContralType(UIContralType.TB);
		// attr.setLGType(FieldTypeS.Normal);
		// attr.setUIVisible(false);
		// attr.setUIIsEnable(false);
		// attr.setDefVal("0");
		// attr.setHisEditType(BP.En.EditType.Readonly);
		// attr.Insert();
		// }
		// ds.Tables.add(Sys_MapAttr);
		// ds.hashTables.put(Sys_MapAttr.TableName, Sys_MapAttr);

		return ds;
	}

	/**
	 * 生成自动的ｊｓ程序
	 * 
	 * @param pk
	 * @param attrs
	 * @param attr
	 * @param tbPer
	 * @return
	 */
	public static String GenerAutoFull(String pk, MapAttrs attrs, MapExt me, String tbPer) {
		String left = "\n document.forms[0]." + tbPer + "_TB" + me.getAttrOfOper() + "_" + pk + ".value = ";
		String right = me.getDoc();
		for (Object mattr : attrs) {
			right = right.replace("@" + ((MapAttr) mattr).getKeyOfEn(), " parseFloat( document.forms[0]." + tbPer
					+ "_TB_" + ((MapAttr) mattr).getKeyOfEn() + "_" + pk + ".value) ");
		}
		return " alert( document.forms[0]." + tbPer + "_TB" + me.getAttrOfOper() + "_" + pk + ".value ) ; \t\n " + left
				+ right;
	}
}