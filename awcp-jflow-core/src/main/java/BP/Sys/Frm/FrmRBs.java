package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;
import BP.Sys.SystemConfig;

/** 
 单选框s
 
*/
public class FrmRBs extends EntitiesNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 单选框s
	 
	*/
	public FrmRBs()
	{
	}
	public static ArrayList<FrmRB> convertFrmRBs(Object obj) {
		return (ArrayList<FrmRB>) obj;
	}
	/** 
	 单选框s
	 
	 @param fk_mapdata s
	*/
	public FrmRBs(String fk_mapdata)
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
		return new FrmRB();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}