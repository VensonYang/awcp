package BP.Sys;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;
import BP.En.EntityNoName;

/** 
 GENoNames
 
*/
public class GENoNames extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public static ArrayList<GENoName> convertGENoNames(Object obj) {
		return (ArrayList<GENoName>) obj;
	}
	/** 
	 物理表
	 
	*/
	public String SFTable = null;
	public String Desc = null;

	/** 
	 GENoNames
	 
	*/
	public GENoNames()
	{
	}
	public GENoNames(String sftable, String tableDesc)
	{
		this.SFTable = sftable;
		this.Desc = tableDesc;
	}
	@Override
	public Entity getGetNewEntity()
	{
		return new GENoName(this.SFTable, this.Desc);
	}
	@Override
	public int RetrieveAll()
	{
		return this.RetrieveAllFromDBSource();
	}
}