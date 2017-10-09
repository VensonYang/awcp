package BP.WF.DTS;

import java.io.File;
import java.util.Date;

import BP.DA.DataSet;
import BP.DA.DataType;
import BP.En.Method;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDataAttr;
import BP.Sys.Frm.MapDatas;
import BP.WF.Template.Flow;
import BP.WF.Template.FlowSort;
import BP.WF.Template.FlowSorts;
import BP.WF.Template.Flows;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;
import BP.WF.Template.Form.Sys.SysFormTree;
import BP.WF.Template.Form.Sys.SysFormTrees;

/**
 * Method 的摘要说明
 * 
 */
public class GenerTemplate extends Method {
	/**
	 * 不带有参数的方法
	 * 
	 */
	public GenerTemplate() {
		this.Title = "生成流程模板与表单模板";
		this.Help = "把系统中的流程与表单转化成模板放在指定的目录下。";
		// this.Warning = "您确定要执行吗？";

		this.getHisAttrs().AddTBString("Path", "C:" + File.separator + "ccflow.Template", "生成的路径", true, false, 1, 1900,
				200);
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
		return true;
	}

	/**
	 * 执行
	 * 
	 * @return 返回执行结果
	 */
	@Override
	public Object Do() {
		String path = this.GetValStrByKey("Path") + "_" + DataType.dateToStr(new Date(), "yy年MM月dd日HH时mm分");
		File file = new File(path);
		if (file.exists()) {
			return "系统正在执行中，请稍后。";
		}
		file.mkdirs();
		file = new File(path + File.separator + "Flow.流程模板");
		file.mkdirs();
		file = new File(path + File.separator + "Frm.表单模板");
		file.mkdirs();
		/*
		 * warning if (System.IO.Directory.Exists(path)) { return
		 * "系统正在执行中，请稍后。"; }
		 * 
		 * System.IO.Directory.CreateDirectory(path);
		 * System.IO.Directory.CreateDirectory(path + "\\Flow.流程模板");
		 * System.IO.Directory.CreateDirectory(path + "\\Frm.表单模板");
		 */

		Flows fls = new Flows();
		fls.RetrieveAll();
		FlowSorts sorts = new FlowSorts();
		sorts.RetrieveAll();

		// 生成流程模板。
		for (FlowSort sort : FlowSorts.convertFlowSorts(sorts)) {
			String pathDir = path + File.separator + "Flow.流程模板" + File.separator + sort.getNo() + "." + sort.getName();
			/*
			 * warning System.IO.Directory.CreateDirectory(pathDir);
			 */
			file = new File(pathDir);
			file.mkdirs();
			for (Flow fl : Flows.convertFlows(fls)) {
				fl.DoExpFlowXmlTemplete(pathDir);
			}
		}

		// 生成表单模板。
		for (FlowSort sort : FlowSorts.convertFlowSorts(sorts)) {
			String pathDir = path + File.separator + "Frm.表单模板" + File.separator + sort.getNo() + "." + sort.getName();
			/*
			 * warning System.IO.Directory.CreateDirectory(pathDir);
			 */
			file = new File(pathDir);
			file.mkdirs();
			for (Flow fl : Flows.convertFlows(fls)) {
				String pathFlowDir = pathDir + File.separator + fl.getNo() + "." + fl.getName();
				/*
				 * warning System.IO.Directory.CreateDirectory(pathFlowDir);
				 */
				file = new File(pathFlowDir);
				file.mkdirs();
				Nodes nds = new Nodes(fl.getNo());
				for (Node nd : Nodes.convertNodes(nds)) {
					MapData md = new MapData("ND" + nd.getNodeID());
					DataSet ds = md.GenerHisDataSet();
					/*
					 * warning System.Data.DataSet ds = md.GenerHisDataSet();
					 */
					try {
						ds.WriteXml(pathFlowDir + File.separator + nd.getNodeID() + "." + nd.getName() + ".Frm.xml");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// 流程表单模板.
		SysFormTrees frmSorts = new SysFormTrees();
		frmSorts.RetrieveAll();
		for (SysFormTree sort : SysFormTrees.convertSysFormTrees(frmSorts)) {
			String pathDir = path + File.separator + "Frm.表单模板" + File.separator + sort.getNo() + "." + sort.getName();
			/*
			 * warning System.IO.Directory.CreateDirectory(pathDir);
			 */
			file = new File(pathDir);
			file.mkdirs();

			MapDatas mds = new MapDatas();
			mds.Retrieve(MapDataAttr.FK_FrmSort, sort.getNo());
			for (MapData md : MapDatas.convertMapDatas(mds)) {
				DataSet ds = md.GenerHisDataSet();
				/*
				 * warning System.Data.DataSet ds = md.GenerHisDataSet();
				 */
				try {
					ds.WriteXml(pathDir + File.separator + md.getNo() + "." + md.getName() + ".Frm.xml");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "生成成功，请打开" + path + "。<br>如果您想共享出来请压缩后发送到template＠ccflow.org";
	}
}