package BP.WF.Data;

import BP.DA.DBAccess;
import BP.DA.DataTable;
import BP.En.Entities;
import BP.En.Entity;

/** 
我发起的流程s

*/
public class MyStartFlows extends Entities
{
	/** 
	 根据工作流程,工作人员 ID 查询出来他当前的能做的工作.
	 
	 @param flowNo 流程编号
	 @param empId 工作人员ID
	 @return 
	*/
	public static DataTable QuByFlowAndEmp(String flowNo, int empId)
	{
		String sql="SELECT a.WorkID FROM WF_MyStartFlow a, WF_GenerWorkerlist b WHERE a.WorkID=b.WorkID   AND b.FK_Node=a.FK_Node  AND b.FK_Emp='"+(new Integer(empId)).toString()+"' AND a.FK_Flow='"+flowNo+"'";
		return DBAccess.RunSQLReturnTable(sql);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MyStartFlow();
	}
	/** 
	 我发起的流程集合
	 
	*/
	public MyStartFlows()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}