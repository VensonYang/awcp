package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;
import BP.Sys.GEDtl;

/** 
 附件数据存储s
 
*/
public class FrmAttachmentDBs extends EntitiesMyPK
{
	public static ArrayList<FrmAttachmentDB> convertFrmAttachmentDBs(Object obj) {
		return (ArrayList<FrmAttachmentDB>) obj;
	}
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 附件数据存储s
	 
	*/
	public FrmAttachmentDBs()
	{
	}
	/** 
	 附件数据存储s
	 
	 @param fk_mapdata s
	*/
	public FrmAttachmentDBs(String fk_mapdata, String pkval)
	{
		this.Retrieve(FrmAttachmentDBAttr.FK_MapData, fk_mapdata, FrmAttachmentDBAttr.RefPKVal, pkval);
	}
	public FrmAttachmentDBs(String fk_mapdata, long pkval)
	{
		this.Retrieve(FrmAttachmentDBAttr.FK_MapData, fk_mapdata, FrmAttachmentDBAttr.RefPKVal, pkval);
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmAttachmentDB();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}