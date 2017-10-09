package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesOID;
import BP.En.Entity;

/** 
 GroupFields
 
*/
public class GroupFields extends EntitiesOID
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<GroupField> convertGroupFields(Object obj) {
		return (ArrayList<GroupField>) obj;
	}
		///#region 构造
	/** 
	 GroupFields
	 
	*/
	public GroupFields()
	{
	}
	/** 
	 GroupFields
	 
	 @param EnName s
	*/
	public GroupFields(String EnName)
	{
		int i = this.Retrieve(GroupFieldAttr.EnName, EnName, GroupFieldAttr.Idx);
		if (i == 0)
		{
			GroupField gf = new GroupField();
			gf.setEnName(EnName);
			MapData md = new MapData();
			md.setNo(EnName);
			if (md.RetrieveFromDBSources() == 0)
			{
				gf.setLab("基础信息");
			}
			else
			{
				gf.setLab(md.getName());
			}
			gf.setIdx(0);
			gf.Insert();
			this.AddEntity(gf);
		}
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new GroupField();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


}