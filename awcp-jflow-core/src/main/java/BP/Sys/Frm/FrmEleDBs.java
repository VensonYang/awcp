package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;
import BP.Sys.SystemConfig;


/** 
 表单元素扩展DBs
 
*/
public class FrmEleDBs extends EntitiesMyPK
{
	public static ArrayList<FrmEleDB> convertFrmEleDBs(Object obj) {
		return (ArrayList<FrmEleDB>) obj;
	}
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 表单元素扩展DBs
	 
	*/
	public FrmEleDBs()
	{
	}
	/** 
	 表单元素扩展DBs
	 
	 @param fk_mapdata
	 @param pkval
	*/
	public FrmEleDBs(String fk_mapdata, String pkval)
	{
		this.Retrieve(FrmAttachmentDBAttr.FK_MapData, fk_mapdata, FrmAttachmentDBAttr.RefPKVal, pkval);
	}
	/** 
	 表单元素扩展DBs
	 
	 @param fk_mapdata s
	*/
	public FrmEleDBs(String fk_mapdata)
	{
		if (SystemConfig.getIsDebug())
		{
			this.Retrieve(FrmLineAttr.FK_MapData, fk_mapdata);
		}
		else
		{
			this.RetrieveFromCash(FrmLineAttr.FK_MapData, (Object)fk_mapdata);
		}
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmEleDB();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}