package BP.WF.Template.Form.Sys;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;
import BP.WF.Template.Flow;

/** 
 流程表单树
 
*/
public class SysFormTrees extends EntitiesTree
{
	/** 
	 流程表单树s
	 
	*/
	public static ArrayList<SysFormTree> convertSysFormTrees(Object obj) {
		return (ArrayList<SysFormTree>) obj ;}
	public SysFormTrees()
	{
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new SysFormTree();
	}

	@Override
	public int RetrieveAll()
	{
		int i = super.RetrieveAll();
		if (i == 0)
		{
			SysFormTree fs = new SysFormTree();
			fs.setName("公文类");
			fs.setNo ( "01");
			fs.Insert();

			fs = new SysFormTree();
			fs.setName("办公类");
			fs.setNo ( "02");
			fs.Insert();
			i = super.RetrieveAll();
		}
		return i;
	}
}