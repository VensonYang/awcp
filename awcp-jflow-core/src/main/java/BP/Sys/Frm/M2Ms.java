package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.Entity;
import BP.En.SimpleNoNames;

/** 
 M2M数据存储
 
*/
public class M2Ms extends SimpleNoNames
{
	public static ArrayList<M2M> convertM2Ms(Object obj) {
		return (ArrayList<M2M>) obj;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 M2M数据存储s
	 
	*/
	public M2Ms()
	{
	}
	/** 
	 M2M数据存储s
	 
	 @param FK_MapData
	 @param EnOID
	*/
	public M2Ms(String FK_MapData, long EnOID)
	{
		this.Retrieve(M2MAttr.FK_MapData, FK_MapData, M2MAttr.EnOID, (new Long(EnOID)).toString());
	}
	/** 
	 M2M数据存储 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new M2M();
	}
}