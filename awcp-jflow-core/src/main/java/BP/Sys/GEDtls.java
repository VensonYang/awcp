package BP.Sys;

import java.util.ArrayList;

import BP.En.EntitiesOID;
import BP.En.Entity;

/** 
通用从表s

*/
public class GEDtls extends EntitiesOID
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<GEDtl> convertGEDtls(Object obj) {
		return (ArrayList<GEDtl>) obj;
	}
	/** 
	 节点ID
	 
	*/
	public String FK_MapDtl = null;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		if (this.FK_MapDtl == null)
		{
			return new GEDtl();
		}
		return new GEDtl(this.FK_MapDtl);
	}
	/** 
	 通用从表ID
	 
	*/
	public GEDtls()
	{
	}
	/** 
	 通用从表ID
	 
	 @param fk_mapdtl
	*/
	public GEDtls(String fk_mapdtl)
	{
		this.FK_MapDtl = fk_mapdtl;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}