package BP.WF.Entity;

import java.util.ArrayList;

import BP.En.Entities;
import BP.En.Entity;

/** 
 退回轨迹s 
 
*/
public class ReturnWorks extends Entities
{
		///#region 构造
	
	public static ArrayList<ReturnWork> convertReturnWorks(Object obj) {
		return (ArrayList<ReturnWork>) obj;
	}
	
	/** 
	 退回轨迹s
	 
	*/
	public ReturnWorks()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new ReturnWork();
	}
		///#endregion
}