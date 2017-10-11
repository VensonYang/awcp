package BP.WF.Data;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.Port.*;
import BP.Web.*;
import BP.Sys.*;

/** 
 单据s
 
*/
public class Bills extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法属性
	
	public static ArrayList<Bill> convertBills(Object obj) {
		return (ArrayList<Bill>) obj;
	}
	
	
	/** 
	 单据s
	 
	*/
	public Bills()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 单据
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Bill();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}