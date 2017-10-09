package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;

/** 
 剪切图片附件数据存储s
 
*/
public class FrmImgAthDBs extends EntitiesMyPK
{
	public static ArrayList<FrmImgAthDB> convertFrmImgAthDBs(Object obj) {
		return (ArrayList<FrmImgAthDB>) obj;
	}
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 剪切图片附件数据存储s
	 
	*/
	public FrmImgAthDBs()
	{
	}
	/** 
	 剪切图片附件数据存储s
	 
	 @param fk_mapdata s
	*/
	public FrmImgAthDBs(String fk_mapdata, String pkval)
	{
		this.Retrieve(FrmImgAthDBAttr.FK_MapData, fk_mapdata, FrmImgAthDBAttr.RefPKVal, pkval);
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmImgAthDB();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}