package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.Demo.BanJis;
import BP.Demo.StudentAttr;
import BP.En.Entity;
import BP.En.Map;
import BP.En.QueryObject;
import BP.En.UAC;
import BP.Sys.Frm.DTSearchWay;

public class TXManager extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.Readonly();
		return uac;
	}
	
	
	
	public Map getEnMap() {
		if (this.get_enMap()!=null)
		{
			return this.get_enMap();
		}
		Map map = new Map();
		map.setEnDesc("视图查询");
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("view_tx_cs");
		map.IsCount = true;
		
		map.IsShowSearchKey = false;
		map.DTSearchWay = DTSearchWay.ByDateTime;
		map.DTSearchKey = TXManagerAttr.FangAnNianDu;
		
		map.AddTBStringPK(TXManagerAttr.ND124OID, null, "编号", false, false, 0, 50, 50);
		map.AddTBString(TXManagerAttr.XingMing, null, "姓名", true, false, 0, 50, 200);
		map.AddDDLSysEnum(TXManagerAttr.DuiXiangLeiXing, 0, "对象类型", true, true, TXManagerAttr.DuiXiangLeiXing,
				"@0=低保家庭@1=五保户@2=残疾人@3=因灾@4=因病@5=单亲家庭@6=优抚对象@7=企业军转待困@8=拆迁户@9=三老户@10=半边保@11=辖区内贫困大学生@12=辖区内外来务工人员贫困子女@13=九十岁以上高领老人@14=计生困难户@15=有突出贡献的社区工作人员@16=其他");
		map.AddTBString(TXManagerAttr.ShenFenZhengHao, null, "身份证号", true, false, 0, 50, 200);
		map.AddTBString(TXManagerAttr.BillNo, null, "申报单号", true, false, 0, 50, 200);
		map.AddTBFloat(TXManagerAttr.FaFangJinE, 0, "代发金额", true, false);
		map.AddDDLEntities(TXManagerAttr.FK_QX, null, "区县", new QuYs(), true);
		map.AddDDLEntities(TXManagerAttr.FK_JD, null, "街道", new JieDs(), true);
		map.AddDDLEntities(TXManagerAttr.FK_SQ, null, "社区", new SheQs(), true);
		map.AddTBString(TXManagerAttr.ZhuZhi, null, "地址", true, false, 0, 50, 200);
		map.AddTBString(TXManagerAttr.LianXiDianHua, null, "联系电话", true, false, 0, 50, 200);
		map.AddTBDateTime(TXManagerAttr.FangAnNianDu, null, "方案日期", true, true);
		
		map.AddSearchOfAttr(TXManagerAttr.XingMing, "like", "", 10);
		map.AddSearchOfAttr(TXManagerAttr.ShenFenZhengHao, "like", "", 15);
		
		//map.AddSearchAttr(TXManagerAttr.FK_QX);
		//map.AddSearchAttr(TXManagerAttr.FK_JD);
		//map.AddSearchAttrChild(TXManagerAttr.FK_JD, true, TXManagerAttr.FK_SQ);
		//map.AddSearchAttr(TXManagerAttr.FK_SQ);
		//map.AddSearchAttrParent(TXManagerAttr.FK_SQ, true, TXManagerAttr.FK_JD, TXManagerAttr.FK_JD);
		map.AddSearchAttrGrandChild(TXManagerAttr.FK_QX, true, TXManagerAttr.FK_JD, TXManagerAttr.FK_SQ);
		map.AddSearchAttrParentAndChild(TXManagerAttr.FK_JD, true, TXManagerAttr.FK_QX, TXManagerAttr.FK_SQ, "FK_QX");
		map.AddSearchAttrParent(TXManagerAttr.FK_SQ, true, TXManagerAttr.FK_JD, "FK_JD");
		map.AddSearchAttr(TXManagerAttr.DuiXiangLeiXing);
		
		this.set_enMap(map);
		return this.get_enMap();
	}
	
}
