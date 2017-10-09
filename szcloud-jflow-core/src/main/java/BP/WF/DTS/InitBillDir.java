package BP.WF.DTS;

import java.io.File;

import BP.DA.DBUrlType;
import BP.DA.DataType;
import BP.DTS.DataIOEn;
import BP.DTS.DoType;
import BP.DTS.RunTimeType;
import BP.En.QueryObject;
import BP.Port.Dept;
import BP.Port.Depts;
import BP.WF.Data.BillTemplate;
import BP.WF.Data.BillTemplates;

public class InitBillDir extends DataIOEn {
	/**
	 * 流程时效考核
	 * 
	 */
	public InitBillDir() {
		this.HisDoType = DoType.UnName;
		this.Title = "<font color=green><b>创建单据目录(运行在每次更改单据文号或每年一天)</b></font>";
		this.HisRunTimeType = RunTimeType.UnName;
		this.FromDBUrl = DBUrlType.AppCenterDSN;
		this.ToDBUrl = DBUrlType.AppCenterDSN;
	}

	/**
	 * 创建单据目录
	 * 
	 */
	@Override
	public void Do() {

		Depts depts = new Depts();
		QueryObject qo = new QueryObject(depts);
		// qo.AddWhere("Grade", " < ", 4);
		qo.DoQuery();

		BillTemplates funcs = new BillTemplates();
		funcs.RetrieveAll();

		String path = BP.WF.Glo.getFlowFileBill();
		String year = DataType.getCurrentYear();

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(path + File.separator + year);
		if (!file.exists()) {
			file.mkdirs();
		}

		for (Dept Dept : Depts.convertDepts(depts)) {
			file = new File(path + File.separator + year + File.separator + Dept.getNo());
			if (!file.exists()) {
				file.mkdirs();
			}

			for (BillTemplate func : BillTemplates.convertBillTemplates(funcs)) {
				file = new File(
						path + File.separator + year + File.separator + Dept.getNo() + File.separator + func.getNo());
				if (!file.exists()) {
					file.mkdirs();
				}
			}
		}
	}
}