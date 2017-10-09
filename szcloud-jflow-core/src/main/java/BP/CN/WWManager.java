package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.En.Entity;
import BP.En.Map;
import BP.En.UAC;

public class WWManager extends Entity{
	
	private static final long serialVersionUID = 1L;



	public final String getND124OID(){
		return this.GetValStrByKey(TXManagerAttr.ND124OID);
	}
	
	public final String getND128OID(){
		return this.GetValStrByKey(TXManagerAttr.ND128OID);
	}

	public final String getBillNo(){
		return this.GetValStrByKey(TXManagerAttr.BillNo);
	}

	public final String getFaFangJinE(){
		return this.GetValStrByKey(TXManagerAttr.FaFangJinE);
	}

	public final String getFK_JD(){
		return this.GetValStrByKey(TXManagerAttr.FK_JD);
	}

	public final String DuiXiangLeiXing(){
		return this.GetValStrByKey(TXManagerAttr.DuiXiangLeiXing);
	}

	public final String getLianXiDianHua(){
		return this.GetValStrByKey(TXManagerAttr.LianXiDianHua);
	}

	public final String getFK_QX(){
		return this.GetValStrByKey(TXManagerAttr.FK_QX);
	}

	public final String getShenFenZhengHao(){
		return this.GetValStrByKey(TXManagerAttr.ShenFenZhengHao);
	}

	public final String getFK_SQ(){
		return this.GetValStrByKey(TXManagerAttr.FK_SQ);
	}

	public final String getXingMing(){
		return this.GetValStrByKey(TXManagerAttr.XingMing);
	}

	public final String getZhuZhi(){
		return this.GetValStrByKey(TXManagerAttr.ZhuZhi);
	}
	
	public final String getFaFangFangShi(){
		return this.GetValStrByKey(TXManagerAttr.FaFangFangShi);
	}
	
	public final String getTianXinQuShenHeRen(){
		return this.GetValStrByKey(TXManagerAttr.TianXinQuShenHeRen);
	}

	public final String getZiJinLaiYuan(){
		return this.GetValStrByKey(TXManagerAttr.ZiJinLaiYuan);
	}

	public final String getXingBie(){
		return this.GetValStrByKey(TXManagerAttr.XingBie);
	}
	
	public final String getFangAnNianDu(){
		return this.GetValStrByKey(TXManagerAttr.FangAnNianDu);
	}
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.Readonly();
		return uac;
	}
	
	public Map getEnMap() {
		Map map = new Map();
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("view_tx");
		this.set_enMap(map);
		map.AddTBStringPK(TXManagerAttr.ND124OID, null, "编号", false, false, 0, 50, 50);
		map.AddTBString(TXManagerAttr.FangAnNianDu, null, "年度", true, false, 0, 50, 200);
		map.AddDDLSysEnum(TXManagerAttr.ZiJinLaiYuan, 0, "发放方式", true, true, TXManagerAttr.ZiJinLaiYuan,
				"@0=财政资金@1=自筹资金@3=部门资金");
		map.AddDDLSysEnum(TXManagerAttr.FaFangFangShi, 0, "发放方式", true, true, TXManagerAttr.FaFangFangShi,
				"@0=现金发放@1=银行转账");
		map.AddDDLSysEnum(TXManagerAttr.DuiXiangLeiXing, 0, "对象类型", true, true, TXManagerAttr.DuiXiangLeiXing,
				"@0=低保家庭@1=五保户@2=残疾人@3=因灾@4=因病@5=单亲家庭@6=优抚对象@7=企业军转待困@8=拆迁户@9=三老户@10=半边保@11=辖区内贫困大学生@12=辖区内外来务工人员贫困子女@13=九十岁以上高领老人@14=计生困难户@15=有突出贡献的社区工作人员@16=其他");
		map.AddTBString(TXManagerAttr.XingMing, null, "姓名", true, false, 0, 50, 200);
		map.AddDDLSysEnum(TXManagerAttr.XingBie, 0, "性别", true, true, TXManagerAttr.XingBie,
				"@0=男@=女");
		map.AddTBString(TXManagerAttr.NianLing, null, "年龄", true, false, 0, 50, 200);
		map.AddTBString(TXManagerAttr.LianXiDianHua, null, "联系电话", true, false, 0, 50, 200);
		map.AddTBString(TXManagerAttr.ZhuZhi, null, "住址", true, false, 0, 50, 200);
		map.AddTBString(TXManagerAttr.FaFangJinE, null, "金额", true, false, 0, 50, 200);
		map.AddTBString(TXManagerAttr.TianXinQuShenHeRen, null, "签字", true, false, 0, 50, 200);
		
		return this.get_enMap();
	}

}
