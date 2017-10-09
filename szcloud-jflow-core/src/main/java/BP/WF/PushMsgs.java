package BP.WF;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;
import BP.Sys.*;

/** 
 消息推送
 
*/
public class PushMsgs extends EntitiesMyPK
{
	/** 
	 消息推送
	 
	*/
	public PushMsgs()
	{
	}
	/** 
	 消息推送
	 
	 @param fk_flow
	*/
	public PushMsgs(String fk_flow)
	{


		QueryObject qo = new QueryObject(this);
		qo.AddWhereInSQL(PushMsgAttr.FK_Node, "SELECT NodeID FROM WF_Node WHERE FK_Flow='" + fk_flow + "'");
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new PushMsg();
	}
}