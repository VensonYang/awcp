package BP.WF.Template;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;

/** 
 消息收听
 
*/
public class Listens extends EntitiesOID
{
	public static ArrayList<Listen> convertListens(Object obj) {
		return (ArrayList<Listen>) obj;
	}
	/** 
	 消息收听
	 
	*/
	public Listens()
	{
	}
	/** 
	 消息收听
	 
	 @param fk_flow
	*/
	public Listens(String fk_flow)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhereInSQL(ListenAttr.FK_Node, "SELECT NodeID FROM WF_Node WHERE FK_Flow='" + fk_flow + "'");
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Listen();
	}
}