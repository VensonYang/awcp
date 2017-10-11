package BP.WF.Template;

import BP.DA.*;
import BP.En.*;
import BP.WF.Template.*;
import BP.WF.Template.PubLib.NodeAttr;
import BP.Port.*;

/** 
 标签集合
 
*/
public class LabNotes extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new LabNote();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 标签集合
	 
	*/
	public LabNotes()
	{
	}
	/** 
	 标签集合.
	 
	 @param FlowNo
	*/
	public LabNotes(String fk_flow)
	{
		this.Retrieve(NodeAttr.FK_Flow, fk_flow);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}