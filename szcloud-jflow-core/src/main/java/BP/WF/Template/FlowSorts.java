package BP.WF.Template;

import java.util.ArrayList;

import BP.En.EntitiesTree;
import BP.En.Entity;

/** 
 流程类别
 
*/
public class FlowSorts extends EntitiesTree
{
	/** 
	 流程类别s
	 
	*/
	public FlowSorts()
	{
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<FlowSort> convertFlowSorts(Object obj) {
		return (ArrayList<FlowSort>) obj;
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FlowSort();
	}

	/** 
	 流程类别s
	 
	 @param no ss
	 @param name anme
	*/
	public final void AddByNoName(String no, String name)
	{
		FlowSort en = new FlowSort();
		en.setNo(no);
		en.setName(name);
		this.AddEntity(en);
	}
	@Override
	public int RetrieveAll()
	{
		int i = super.RetrieveAll();
		if (i == 0)
		{
			FlowSort fs = new FlowSort();
			fs.setName("公文类");
			fs.setNo ("01");
			fs.Insert();

			fs = new FlowSort();
			fs.setName("办公类");
			fs.setNo ("02");
			fs.Insert();
			i = super.RetrieveAll();
		}

		return i;
	}
}