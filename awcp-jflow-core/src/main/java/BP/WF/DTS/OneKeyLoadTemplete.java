package BP.WF.DTS;

import java.io.File;

import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.En.Method;
import BP.En.QueryObject;
import BP.Port.Dept;
import BP.Port.Depts;
import BP.Port.Emp;
import BP.Port.EmpDept;
import BP.Port.EmpDepts;
import BP.Port.EmpStation;
import BP.Port.EmpStations;
import BP.Port.Emps;
import BP.Port.Station;
import BP.Port.Stations;
import BP.Port.WebUser;
import BP.Sys.SFTable;
import BP.Sys.SFTables;
import BP.Sys.SysEnum;
import BP.Sys.SysEnumMain;
import BP.Sys.SysEnumMains;
import BP.Sys.SysEnums;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDatas;
import BP.WF.Template.Flow;
import BP.WF.Template.FlowSort;
import BP.WF.Template.FlowSorts;
import BP.WF.Template.Flows;
import BP.WF.Template.Form.Sys.SysFormTree;
import BP.WF.Template.Form.Sys.SysFormTrees;
import BP.WF.Template.PubLib.ImpFlowTempleteModel;

/**
 * Method 的摘要说明
 * 
 */
public class OneKeyLoadTemplete extends Method {
	/**
	 * 不带有参数的方法
	 * 
	 */
	public OneKeyLoadTemplete() {
		this.Title = "一键恢复流程模版目录";
		this.Help = "此功能是一键备份流程的逆向操作.";
		this.Help += "@执行时请注意";
		this.Help += "@1,系统所有的流程数据、模版数据、组织解构数据、将会被删除。";
		this.Help += "@2,重新装载C:\\CCFlowTemplete 的数据。";
		this.Help += "@3,此功能一般提供给ccflow的开发者用于不同的数据库之间的移植。";
	}

	/**
	 * 设置执行变量
	 * 
	 * @return
	 */
	@Override
	public void Init() {
	}

	/**
	 * 当前的操纵员是否可以执行这个方法
	 * 
	 */
	@Override
	public boolean getIsCanDo() {
		if (!WebUser.getNo().equals("admin")) {
			return false;
		}

		return true;
	}

	@Override
	public Object Do() {
		String msg = "";

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 检查数据文件是否完整.
		String path = "C:" + File.separator + "CCFlowTemplete";
		File file = new File(path);
		if (!file.exists()) {
			msg += "@错误：约定的目录不存在服务器" + path + ",请把从ccflow备份的文件放入" + path;
		}

		// PortTables.
		path = path + File.separator + "PortTables.xml";
		file = new File(path);
		if (!file.exists()) {
			msg += "@错误：约定的文件不存在，" + file;
		}

		// SysTables.
		path = path + File.separator + "SysTables.xml";
		file = new File(path);
		if (!file.exists()) {
			msg += "@错误：约定的文件不存在，" + file;
		}

		// FlowTables.
		path = path + File.separator + "FlowTables.xml";
		file = new File(path);
		if (!file.exists()) {
			msg += "@错误：约定的文件不存在，" + file;
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 检查数据文件是否完整.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 1 装载流程基础表数据.
		DataSet ds = new DataSet();
		ds.readXml(path + File.separator + "FlowTables.xml");

		// 流程类别.
		FlowSorts sorts = new FlowSorts();
		try {
			sorts.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		DataTable dt = ds.hashTables.get("WF_FlowSort");
		// sorts = QueryObject.InitEntitiesByDataTable(sorts, dt, null) as
		// FlowSorts;
		for (FlowSort item : FlowSorts.convertFlowSorts(sorts)) {
			item.DirectInsert(); // 插入数据.
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 1 装载流程基础表数据.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 2 组织结构.
		ds = new DataSet();
		ds.readXml(path + File.separator + "PortTables.xml");

		// Port_Emp.
		Emps emps = new Emps();
		try {
			emps.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dt = ds.hashTables.get("Port_Emp");
		Object tempVar = QueryObject.InitEntitiesByDataTable(emps, dt, null);
		emps = (Emps) ((tempVar instanceof Emps) ? tempVar : null);
		for (Emp item : Emps.convertEmps(emps)) {
			item.DirectInsert(); // 插入数据.
		}

		// Depts.
		Depts depts = new Depts();
		try {
			depts.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dt = ds.hashTables.get("Port_Dept");
		Object tempVar2 = QueryObject.InitEntitiesByDataTable(depts, dt, null);
		depts = (Depts) ((tempVar2 instanceof Depts) ? tempVar2 : null);
		for (Dept item : Depts.convertDepts(depts)) {
			item.DirectInsert(); // 插入数据.
		}

		// Stations.
		Stations stas = new Stations();
		try {
			stas.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dt = ds.hashTables.get("Port_Station");
		Object tempVar3 = QueryObject.InitEntitiesByDataTable(stas, dt, null);
		stas = (Stations) ((tempVar3 instanceof Stations) ? tempVar3 : null);
		for (Station item : Stations.convertStations(stas)) {
			item.DirectInsert(); // 插入数据.
		}

		// EmpDepts.
		EmpDepts eds = new EmpDepts();
		try {
			eds.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dt = ds.hashTables.get("Port_EmpDept");
		Object tempVar4 = QueryObject.InitEntitiesByDataTable(eds, dt, null);
		eds = (EmpDepts) ((tempVar4 instanceof EmpDepts) ? tempVar4 : null);
		for (EmpDept item : EmpDepts.convertEmpDepts(eds)) {
			item.DirectInsert(); // 插入数据.
		}

		// EmpStations.
		EmpStations ess = new EmpStations();
		try {
			ess.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dt = ds.hashTables.get("Port_EmpStation");
		Object tempVar5 = QueryObject.InitEntitiesByDataTable(ess, dt, null);
		ess = (EmpStations) ((tempVar5 instanceof EmpStations) ? tempVar5 : null);
		for (EmpStation item : EmpStations.convertEmpStations(ess)) {
			item.DirectInsert(); // 插入数据.
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 2 组织结构.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 3 恢复系统数据.
		ds = new DataSet();
		ds.readXml(path + File.separator + "SysTables.xml");

		// 枚举Main.
		SysEnumMains sems = new SysEnumMains();
		try {
			sems.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dt = ds.hashTables.get("Sys_EnumMain");
		Object tempVar6 = QueryObject.InitEntitiesByDataTable(sems, dt, null);
		sems = (SysEnumMains) ((tempVar6 instanceof SysEnumMains) ? tempVar6 : null);
		for (SysEnumMain item : SysEnumMains.convertSysEnumMains(sems)) {
			item.DirectInsert(); // 插入数据.
		}

		// 枚举.
		SysEnums ses = new SysEnums();
		try {
			ses.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dt = ds.hashTables.get("Sys_Enum");
		Object tempVar7 = QueryObject.InitEntitiesByDataTable(ses, dt, null);
		ses = (SysEnums) ((tempVar7 instanceof SysEnums) ? tempVar7 : null);
		for (SysEnum item : SysEnums.convertSysEnums(ses)) {
			item.DirectInsert(); // 插入数据.
		}

		//// Sys_FormTree.
		// BP.Sys.SysFormTrees sfts = new SysFormTrees();
		// sfts.ClearTable();
		// dt = ds.Tables["Sys_FormTree"];
		// sfts = QueryObject.InitEntitiesByDataTable(sfts, dt, null) as
		//// SysFormTrees;
		// foreach (SysFormTree item in sfts)
		// {
		// try
		// {
		// item.DirectInsert(); //插入数据.
		// }
		// catch
		// {
		// }
		// }
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 3 恢复系统数据.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 4.备份表单相关数据.
		if (1 == 2) {
			String pathOfTables = path + File.separator + "SFTables";
			file = new File(pathOfTables);
			if (!file.exists()) {
				file.mkdirs();
			}
			// System.IO.Directory.CreateDirectory(pathOfTables);
			SFTables tabs = new SFTables();
			tabs.RetrieveAll();
			for (SFTable item : SFTables.convertSFTables(tabs)) {
				if (item.getNo().contains(".")) {
					continue;
				}

				String sql = "SELECT * FROM " + item.getNo();
				ds = new DataSet();
				ds.Tables.add(BP.DA.DBAccess.RunSQLReturnTable(sql));
				ds.WriteXml(pathOfTables + File.separator + item.getNo() + ".xml");
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 4 备份表单相关数据.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 5.恢复表单数据.
		// 删除所有的流程数据.
		MapDatas mds = new MapDatas();
		mds.RetrieveAll();
		for (MapData fl : MapDatas.convertMapDatas(mds)) {
			// if (fl.FK_FormTree.Length > 1 || fl.FK_FrmSort.Length > 1)
			// continue;
			fl.Delete(); // 删除流程.
		}

		// 清除数据.
		SysFormTrees fss = new SysFormTrees();
		try {
			fss.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		// 调度表单文件。
		String frmPath = path + File.separator + "Form";
		File dirInfo = new File(frmPath);
		File[] dirs = dirInfo.listFiles();
		for (File item : dirs) {
			if (item.getName().contains(".svn")) {
				continue;
			}

			File[] fls = item.listFiles();
			if (fls.length == 0) {
				continue;
			}

			SysFormTree fs = new SysFormTree();
			fs.setNo(item.getName().substring(0, item.getName().indexOf('.')));
			fs.setName(item.getName().substring(item.getName().indexOf('.')));
			fs.setParentNo("0");
			fs.Insert();

			for (File f : fls) {
				try {
					msg += "@开始调度表单模板文件:" + f;
					// System.IO.FileInfo info = new System.IO.FileInfo(f);
					if (!(f.getName().indexOf(".xml") >= 0)) {
						continue;
					}
					// if ( ! info.Extension.equals(".xml"))
					// {
					// continue;
					// }

					ds = new DataSet();
					// ds.ReadXml(f);
					ds.readXml(f.getName());

					MapData md = MapData.ImpMapData(ds, false);
					md.setFK_FrmSort(fs.getNo());
					md.Update();
				} catch (RuntimeException ex) {
					msg += "@调度失败,文件:" + f + ",异常信息:" + ex.getMessage();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 5.恢复表单数据.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 6.恢复流程数据.
		// 删除所有的流程数据.
		Flows flsEns = new Flows();
		flsEns.RetrieveAll();
		for (Flow fl : Flows.convertFlows(flsEns)) {
			fl.DoDelete(); // 删除流程.
		}

		dirInfo = new File(path + File.separator + "Flow" + File.separator);
		dirs = dirInfo.listFiles();

		// 删除数据.
		FlowSorts fsRoots = new FlowSorts();
		try {
			fsRoots.ClearTable();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		// 生成流程树.
		FlowSort fsRoot = new FlowSort();
		fsRoot.setNo("99");
		fsRoot.setName("流程树");
		fsRoot.setParentNo("0");
		fsRoot.DirectInsert();

		for (File dir : dirs) {
			if (dir.getName().contains(".svn")) {
				continue;
			}
			File[] fls = dir.listFiles();
			if (fls.length == 0) {
				continue;
			}

			// String[] fls = System.IO.Directory.GetFiles(dir.FullName);
			// if (fls.length == 0)
			// {
			// continue;
			// }

			FlowSort fs = new FlowSort();
			fs.setNo(dir.getName().substring(0, dir.getName().indexOf('.')));
			fs.setName(dir.getName().substring(3));
			fs.setParentNo(fsRoot.getNo());
			fs.Insert();

			for (File filePath : fls) {
				msg += "@开始调度流程模板文件:" + filePath;
				Flow myflow = Flow.DoLoadFlowTemplate(fs.getNo(), filePath.getName(),
						ImpFlowTempleteModel.AsTempleteFlowNo);
				msg += "@流程:" + myflow.getName() + "装载成功。";

				// System.IO.FileInfo info = new System.IO.FileInfo(filePath);
				// myflow.setName(info.getName().Replace(".xml", ""));
				myflow.setName(filePath.getName().replace(".xml", ""));
				if (myflow.getName().substring(2, 3).equals(".")) {
					myflow.setName(myflow.getName().substring(3));
				}

				myflow.DirectUpdate();
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 6.恢复流程数据.

		BP.DA.Log.DefaultLogWriteLineInfo(msg);

		// 删除多余的空格.
		BP.WF.DTS.DeleteBlankGroupField dts = new DeleteBlankGroupField();
		dts.Do();

		// 执行生成签名.
		GenerSiganture gs = new GenerSiganture();
		gs.Do();

		return msg;
	}
}