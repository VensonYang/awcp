package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;
import BP.Sys.SystemConfig;

/** 
 表单元素扩展s
 
*/
public class FrmEles extends EntitiesMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 表单元素扩展s
	 
	*/
	public FrmEles()
	{
	}
	/** 
	 表单元素扩展s
	 
	 @param fk_mapdata s
	*/
	public FrmEles(String fk_mapdata)
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
	
	public static ArrayList<FrmEle> convertFrmEles(Object obj){
		return (ArrayList<FrmEle>)obj;
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmEle();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}