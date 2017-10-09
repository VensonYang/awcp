package BP.WF.Entity;

import java.util.ArrayList;

import BP.En.Entities;
import BP.En.Entity;

/** 
 移交记录s 
 
*/
public class ShiftWorks extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	public static ArrayList<ShiftWork> convertShiftWorks(Object obj) {
		return (ArrayList<ShiftWork>) obj;
	}
		///#region 构造
	/** 
	 移交记录s
	 
	*/
	public ShiftWorks()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new ShiftWork();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}