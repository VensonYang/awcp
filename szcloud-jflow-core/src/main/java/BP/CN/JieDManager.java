package BP.CN;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import BP.DA.DBAccess;
import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.En.Entity;
import BP.En.Map;
import BP.En.UAC;
import BP.Sys.SystemConfig;

public class JieDManager extends Entity {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(JieDManager.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UAC getHisUAC() {
		UAC uac = new UAC();
		uac.Readonly();
		return uac;
	}

	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map();
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("view_jd_sum");
		this.set_enMap(map);
		map.AddDDLEntities(JieDManagerAttr.FK_QX, null, "区县", new QuYs(), false);
		map.AddDDLEntitiesPK(JieDManagerAttr.FK_JD, JieDManagerAttr.FK_JD, null, "街道", new JieDs(), true);
		map.AddDDLEntities(JieDManagerAttr.FK_SQ, null, "社区", new SheQs(), false);
		map.AddDDLSysEnum(JieDManagerAttr.DuiXiangLeiXing, 0, "对象类型", true, true, JieDManagerAttr.DuiXiangLeiXing,
				"@0=低保家庭@1=五保户@2=残疾人@3=因灾@4=因病@5=单亲家庭@6=优抚对象@7=企业军转待困@8=拆迁户@9=三老户@10=半边保@11=辖区内贫困大学生@12=辖区内外来务工人员贫困子女@13=九十岁以上高领老人@14=计生困难户@15=有突出贡献的社区工作人员@16=其他");
		map.AddDDLSysEnum(JieDManagerAttr.FaFangFangShi, 0, "发放方式", false, true, JieDManagerAttr.FaFangFangShi,
				"@0=现金发放@1=银行转账");
		map.AddDDLSysEnum(JieDManagerAttr.ZiJinLaiYuan, 0, "资金来源", false, true, JieDManagerAttr.ZiJinLaiYuan,
				"@0=现金发放@1=银行转账");
		map.AddTBString(JieDManagerAttr.HuShu, null, "户数", true, false, 0, 50, 200);
		map.AddTBString(JieDManagerAttr.JinE, null, "金额", true, false, 0, 50, 200);
		map.AddSearchAttr(JieDManagerAttr.DuiXiangLeiXing);
		map.AddSearchAttr(JieDManagerAttr.FK_QX);
		map.AddSearchAttrChild(JieDManagerAttr.FK_JD, true, JieDManagerAttr.FK_SQ);
		// map.AddSearchAttr(TXManagerAttr.FK_SQ);
		map.AddSearchAttrParent(JieDManagerAttr.FK_SQ, true, JieDManagerAttr.FK_JD, JieDManagerAttr.FK_JD);
		map.AddSearchAttr(JieDManagerAttr.ZiJinLaiYuan);

		return this.get_enMap();
	}

	public void sumExcelExport() {

	}

	public static void ditialExcelExport() {

		String sql = "select ND124Rpt.FK_QX as FK_JD,ND124Rpt.DuiXiangLeiXing,COUNT(*) as HuShu,SUM(ND128Rpt.FaFangJinE) as JinE from ND124Rpt,ND128Rpt where ND124Rpt.BillNo in(select * from f_split(ND128Rpt.YinCangYu,',')) and ND124Rpt.BillNo!='' group by ND124Rpt.FK_QX,ND124Rpt.DuiXiangLeiXing";

		DataTable dt = DBAccess.RunSQLReturnTable(sql);

		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("街道查询excel");
			HSSFCellStyle style = wb.createCellStyle(); // 样式对象

			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			HSSFRow row = sheet.createRow((short) 0);

			// sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 0));

			// TODO 20161105 venson replace
			// 合並單元格,下標從0開始
			// sheet.addMergedRegion(new Region(0,(short)0,0,(short)
			// (dt.Columns.size()-1)));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (dt.Columns.size() - 1)));

			HSSFRow rowT = sheet.createRow(0);
			HSSFCell cellT = rowT.createCell((short) 0);

			cellT.setCellValue("Excel報表標題");

			// Region cra1=null;
			CellRangeAddress cra1 = null;
			for (int i = 1; i <= dt.Rows.size(); i++) {
				HSSFRow hr = sheet.createRow(i);
				DataRow dr = dt.Rows.get(i - 1);
				for (int j = 0; j < dr.columns.size(); j++) {
					HSSFCell hc = hr.createCell(j);
					hc.setCellValue("" + dr.getValue(dr.columns.get(j)));
				}
				if (i - 1 > 0) {
					// 判断本行街道名称与上一行街道名称是否相同 相同则合并单元格
					if (dt.Rows.get(i - 1).getValue(dt.Columns.get(0))
							.equals(dt.Rows.get(i - 2).getValue(dt.Columns.get(0)))) {
						if (cra1 == null) {
							// TODO 20161105 venson replace
							// cra1 = new Region((short) (i - 1), (short) 0,
							// (short) i, (short) 0);
							cra1 = new CellRangeAddress((i - 1), 0, i, 0);
						} else {
							cra1.setLastRow(cra1.getLastRow() + 1);
							// TODO 20161105 venson replace
							// cra1.setRowTo(cra1.getRowTo() + 1);
						}
					} else {
						if (cra1 != null) {
							sheet.addMergedRegion(cra1);
							sheet.getRow(cra1.getFirstRow()).getCell(cra1.getFirstColumn())
									.setCellValue("" + dt.Rows.get(i - 3).getValue(dt.Columns.get(0)));
							// TODO 20161105 venson replace
							// sheet.getRow(cra1.getRowFrom()).getCell(cra1.getColumnFrom())
							// .setCellValue("" + dt.Rows.get(i -
							// 3).getValue(dt.Columns.get(0)));
							cra1 = null;
						}
					}
				}
			}

			FileOutputStream fileOut = new FileOutputStream("d://workbook.xls");
			wb.write(fileOut);
			fileOut.close();
			logger.debug("OK");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		SystemConfig.ReadConfigFile(new JieDManager().getClass().getResourceAsStream("/conf/web.properties"));
		JieDManager.ditialExcelExport();
	}

}
