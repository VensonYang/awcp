package BP.WF.Template.Form.Sys.Sln;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.*;

/** 
 表单字段方案s
 
*/
public class FrmFields extends EntitiesMyPK
{
	public static ArrayList<FrmField> convertFrmFields(Object obj) {
		return (ArrayList<FrmField>) obj;
	}
	public FrmFields()
	{
	}
	/** 
	 查询
	 
	*/
	public FrmFields(String fk_mapdata, int nodeID)
	{
		this.Retrieve(FrmFieldAttr.FK_MapData, fk_mapdata, FrmFieldAttr.FK_Node, nodeID,FrmFieldAttr.EleType, FrmEleType.Field);
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmField();
	}
}