package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;
import BP.Sys.SystemConfig;

/** 
 按钮s
 
*/
public class FrmBtns extends EntitiesMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 按钮s
	 
	*/
	public FrmBtns()
	{
	}
	/** 
	 按钮s
	 
	 @param fk_mapdata s
	*/
	public FrmBtns(String fk_mapdata)
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
	public static ArrayList<FrmBtn> convertFrmBtns(Object obj) {
		return (ArrayList<FrmBtn>) obj;
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmBtn();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}