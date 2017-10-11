package BP.WF.DTS;

import BP.DA.DBUrlType;
import BP.DA.DataType;
import BP.DA.Log;
import BP.DA.LogType;
import BP.DTS.DataIOEn;
import BP.DTS.DoType;
import BP.DTS.RunTimeType;
import BP.En.Map;
import BP.Port.WebUser;
import BP.Sys.PubClass;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;
import BP.WF.Template.PubLib.WFState;

public class OutputSQLs extends DataIOEn
{
	/** 
	 流程时效考核
	 
	*/
	public OutputSQLs()
	{
		this.HisDoType = DoType.UnName;
		this.Title = "OutputSQLs for produces DTSCHofNode";
		this.HisRunTimeType = RunTimeType.UnName;
		this.FromDBUrl = DBUrlType.AppCenterDSN;
		this.ToDBUrl = DBUrlType.AppCenterDSN;
	}
	@Override
	public void Do()
	{
		String sql = this.GenerSqls();
		try {
			PubClass.ResponseWriteBlueMsg(sql.replace("\n", "<BR>"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public final String GenerSqls()
	{
		Log.DefaultLogWriteLine(LogType.Info, WebUser.getName() + "开始调度考核信息:" + DataType.getCurrentDateByFormart("yyyy-MM-dd HH:mm:ss"));
		String infoMsg = "", errMsg = "";

		Nodes nds = new Nodes();
		nds.RetrieveAll();

		String fromDateTime = new java.util.Date().getYear() + "-01-01";
		fromDateTime = "2004-01-01 00:00";
		//string fromDateTime=DateTime.Now.Year+"-01-01 00:00";
		//string fromDateTime=DateTime.Now.Year+"-01-01 00:00";
		String insertSql = "";
		String delSQL = "";
		String updateSQL = "";

		String sqls = "";
		int i = 0;
		for (Node nd :Nodes.convertNodes(nds) )
		{
			if (nd.getIsPCNode()) // 如果是计算机节点.
			{
				continue;
			}
			i++;
			Map map = nd.getHisWork().getEnMap();
			delSQL = "\n DELETE FROM " + map.getPhysicsTable() + " WHERE  OID  NOT IN (SELECT WORKID FROM WF_GenerWorkFlow ) AND WFState= " + WFState.Runing.getValue();

			sqls += "\n\n\n -- NO:" + i + "、" + nd.getFK_Flow() + nd.getFlowName() + " :  " + map.getEnDesc() + " \n" + delSQL + "; \n" + insertSql + "; \n" + updateSQL + ";";
		}
		Log.DefaultLogWriteLineInfo(sqls);
		return sqls;
	}
}