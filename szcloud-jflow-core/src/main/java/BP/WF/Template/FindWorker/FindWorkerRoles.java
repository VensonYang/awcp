package BP.WF.Template.FindWorker;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.Web.*;
import BP.GPM.*;
import BP.Sys.*;

/** 
 找人规则集合
 
*/
public class FindWorkerRoles extends EntitiesOID
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static ArrayList<FindWorkerRole> convertFindWorkerRoles(Object obj) {
		return (ArrayList<FindWorkerRole>) obj;
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FindWorkerRole();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 找人规则集合
	 
	*/
	public FindWorkerRoles()
	{
	}
	/** 
	 找人规则集合
	 
	 @param nodeID
	*/
	public FindWorkerRoles(int nodeID)
	{
		this.Retrieve(FindWorkerRoleAttr.FK_Node, nodeID, FindWorkerRoleAttr.Idx);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}