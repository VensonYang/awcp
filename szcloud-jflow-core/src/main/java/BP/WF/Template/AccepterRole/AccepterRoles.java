package BP.WF.Template.AccepterRole;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.Port.*;

/** 
 接受人规则集合
 
*/
public class AccepterRoles extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new AccepterRole();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 接受人规则集合
	 
	*/
	public AccepterRoles()
	{
	}
	/** 
	 接受人规则集合.
	 
	 @param FlowNo
	*/
	public AccepterRoles(String FK_Node)
	{
		this.Retrieve(AccepterRoleAttr.FK_Node, FK_Node);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}