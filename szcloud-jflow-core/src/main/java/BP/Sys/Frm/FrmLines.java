package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.Entities;
import BP.En.Entity;
import BP.Sys.SystemConfig;
import BP.WF.Template.Flow;

/** 
 线s
 
*/
public class FrmLines extends Entities
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<FrmLine> convertFrmLines(Object obj) {
		return (ArrayList<FrmLine>) obj;
	}
		///#region 构造
	/** 
	 线s
	 
	*/
	public FrmLines()
	{
	}
	/** 
	 线s
	 
	 @param fk_mapdata s
	*/
	public FrmLines(String fk_mapdata)
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
		return new FrmLine();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}