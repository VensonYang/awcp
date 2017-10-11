package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 纬度报表s
 
*/
public class FrmRpts extends EntitiesNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 纬度报表s
	 
	*/
	public FrmRpts()
	{
	}
	public static ArrayList<FrmRpt> convertFrmRpts(Object obj) {
		return (ArrayList<FrmRpt>) obj;
	}
	/** 
	 纬度报表s
	 
	 @param fk_mapdata s
	*/
	public FrmRpts(String fk_mapdata)
	{
		this.Retrieve(FrmRptAttr.FK_MapData, fk_mapdata, FrmRptAttr.No);
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmRpt();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}