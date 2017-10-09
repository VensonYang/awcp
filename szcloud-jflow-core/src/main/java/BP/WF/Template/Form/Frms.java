package BP.WF.Template.Form;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
 表单s
 
*/
public class Frms extends EntitiesNoName
{
	public static ArrayList<Frm> convertFrms(Object obj) {
		return (ArrayList<Frm>) obj;
	}
	
	/** 
	 Frm
	 
	*/
	public Frms()
	{
	}
	/** 
	 Frm
	 
	 @param fk_flow
	*/
	public Frms(String fk_flow)
	{
		this.Retrieve(FrmAttr.FK_Flow, fk_flow);
	}
	public Frms(int fk_node)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhereInSQL(FrmAttr.No, "SELECT FK_Frm FROM WF_FrmNode WHERE FK_Node=" + fk_node);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Frm();
	}
}