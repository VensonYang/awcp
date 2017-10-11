package BP.WF.Entity;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;

/** 
 流程实例s
 
*/
public class GenerWorkFlows extends Entities
{
	public static ArrayList<GenerWorkFlow> convertGenerWorkFlows(Object obj) {
		return (ArrayList<GenerWorkFlow>) obj;
	}
	/** 
	 根据工作流程,工作人员 ID 查询出来他当前的能做的工作.
	 
	 @param flowNo 流程编号
	 @param empId 工作人员ID
	 @return 
	*/
	public static DataTable QuByFlowAndEmp(String flowNo, int empId)
	{
		String sql="SELECT a.WorkID FROM WF_GenerWorkFlow a, WF_GenerWorkerlist b WHERE a.WorkID=b.WorkID   AND b.FK_Node=a.FK_Node  AND b.FK_Emp='"+(new Integer(empId)).toString()+"' AND a.FK_Flow='"+flowNo+"'";
		return DBAccess.RunSQLReturnTable(sql);
	}
	public static String QuByFlowAndNode(String flowNo,Long workId)
	{
		String sql="select * from WF_GenerWorkerlist where fid in( "+
				"SELECT  FID FROM  WF_GenerWorkerlist   WHERE   FK_Flow='"+flowNo+"' and workID= '"+workId+"')";
		DataTable dt = DBAccess.RunSQLReturnTable(sql);
		if(dt == null || dt.Rows == null || dt.Rows.size()==0)
			return "";
		String result = "";
		for(DataRow dr : dt.Rows)
		{
			result += dr.get("workid")+",";
		}
		if(result.endsWith(","))
			result = result.substring(0,result.length()-1);
		return result;
	}
	

		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new GenerWorkFlow();
	}
	/** 
	 流程实例集合
	 
	*/
	public GenerWorkFlows()
	{
	}
		///#endregion
}